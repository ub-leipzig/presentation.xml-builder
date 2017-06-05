package cool.pandora.ingester;

import cool.pandora.modeller.ModellerClient;
import cool.pandora.modeller.ModellerClientFailedException;
import org.xmlbeam.XBProjector;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

public class IngesterTest {

    public static void main(String[] args) throws IOException, JAXBException {
        XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        RDFData rdf = projector.io().fromURLAnnotation(RDFData.class);
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
