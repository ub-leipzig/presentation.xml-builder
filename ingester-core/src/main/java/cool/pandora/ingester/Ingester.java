package cool.pandora.ingester;

import cool.pandora.ingester.common.Config;
import cool.pandora.ingester.common.TransferProcess;
import cool.pandora.modeller.ModellerClient;
import cool.pandora.modeller.ModellerClientFailedException;
import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalTime;
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
      //  System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        LocalTime now = LocalTime.now();
        String resultFile = config.getBaseDirectory() + "/rdf-output_" + now + ".xml";
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream xsltfile2 = classloader.getResourceAsStream("cool.pandora.ingester/assign_bnodes.xsl");
        SAXTransformerFactory stf = (SAXTransformerFactory)TransformerFactory.newInstance();

        try {
            TransformerHandler th1 = stf.newTransformerHandler(stf.newTemplates(new StreamSource(new File(xsltresource.toString()))));
            TransformerHandler th2 = stf.newTransformerHandler(stf.newTemplates(new StreamSource(xsltfile2)));
            th1.setResult(new SAXResult(th2));
            StreamResult result = new StreamResult(new File(resultFile));
            th2.setResult(result);
            Transformer t = stf.newTransformer();
            th2.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new StreamSource(new File(resource.toString())), new SAXResult(th1));

            putResult(resultFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putResult(String resultFile) throws IOException, JAXBException {
        XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        RDFData rdf = projector.io().url(resultFile).read(RDFData.class);
        List<RDFData.Resource> graph = rdf.getGraph();
        rdf.setResource(graph);
        final String contentType = "application/rdf+xml";
        URI destinationURI = rdf.getResourceURI();
        ByteArrayInputStream is = new ByteArrayInputStream(rdf.toString().getBytes());
        try {
            ModellerClient.doStreamPut(destinationURI, is, contentType);
        } catch (final ModellerClientFailedException e) {
            System.out.println(getMessage(e));
        }
    }


}
