package cool.pandora.ingester;

import cool.pandora.ingester.common.Config;
import cool.pandora.ingester.common.TransferProcess;
import cool.pandora.modeller.ModellerClient;
import cool.pandora.modeller.ModellerClientFailedException;
import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.slf4j.LoggerFactory.getLogger;

public class Ingester implements TransferProcess {
    private static final Logger logger = getLogger(Ingester.class);
    private Config config;

    Ingester(final Config config) {
        this.config = config;

    }

    @Override
    public void run() {
        logger.info("Running importer...");

        processImport(config.getXsltResource(), config.getResource());

    }

    private void processImport(final URI xsltresource, final URI resource) {
        System.setProperty("javax.xml.transform.TransformerFactory",
                "net.sf.saxon.TransformerFactoryImpl");
        String resultFile = config.getBaseDirectory() + "/rdf-output.xml";
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltresource.toString())));
            transformer.transform(new StreamSource(new File(resource.toString())), new StreamResult(new File(resultFile)));
            putResult(resultFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putResult(String resultFile) throws IOException, JAXBException {
        XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        RDFData rdf = projector.io().url(resultFile).read(RDFData.class);
        List<RDFData.Resource> resources = rdf.getResources();
        final String contentType = "application/rdf+xml";
        for (RDFData.Resource res : resources) {
            rdf.setResource(res);
            URI destinationURI = rdf.getResourceURI();
            ByteArrayInputStream is = new ByteArrayInputStream( rdf.toString().getBytes() );
            try {
                ModellerClient.doStreamPut(destinationURI, is, contentType);
            } catch (final ModellerClientFailedException e) {
                System.out.println(getMessage(e));
            }
        }
    }


}
