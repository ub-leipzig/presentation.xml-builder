package cool.pandora.ingester;

import cool.pandora.ingester.common.Config;
import cool.pandora.ingester.common.TransferProcess;
import org.slf4j.Logger;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.OutputStream;
import java.net.URI;

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
        String resultFile = "/tmp/out.xml";
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltresource.toString())));

            transformer.transform(new StreamSource(new File(resource.toString())), new StreamResult(new File(resultFile)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
