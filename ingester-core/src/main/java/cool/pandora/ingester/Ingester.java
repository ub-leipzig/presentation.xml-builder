/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cool.pandora.ingester;

import cool.pandora.ingester.common.Config;
import cool.pandora.ingester.common.TransferProcess;
import cool.pandora.modeller.ModellerClient;
import cool.pandora.modeller.ModellerClientFailedException;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmValue;
import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

import javax.xml.bind.JAXBException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Ingester
 *
 * @author Christopher Johnson
 */
public class Ingester implements TransferProcess {
    private static final Logger logger = getLogger(Ingester.class);
    private final Config config;

    Ingester(final Config config) {
        this.config = config;

    }

    @Override
    public void run() {
        logger.info("Running importer...");

        processImport(config.getXsltResource(), config.getResource());

    }

    private void processImport(final URI xsltresource, final URI resource) {
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
        final URI serviceBaseUri = config.getServiceBaseUri();
        final XdmValue baseUriValue = new XdmAtomicValue(serviceBaseUri.toString());
        final String resultFile = config.getBaseDirectory() + "/presentation.xml";
        final SAXTransformerFactory stf = (SAXTransformerFactory) TransformerFactory.newInstance();

        try {
            final TransformerHandler th1 =
                    stf.newTransformerHandler(stf.newTemplates(new StreamSource(new File(xsltresource.toString()))));
            final StreamResult result = new StreamResult(new File(resultFile));
            th1.setResult(result);
            final Transformer t = stf.newTransformer();
            th1.getTransformer().setParameter("BASEURI", baseUriValue);
            th1.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            th1.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.transform(new StreamSource(new File(resource.toString())), new SAXResult(th1));
            logger.info("Writing transformed output to " + resultFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putResult(final String resultFile) throws IOException, JAXBException {
        final XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        final RDFData rdf = projector.io().url(resultFile).read(RDFData.class);
        final List<RDFData.Resource> graph = rdf.getGraph();
        rdf.setResource(graph);
        final String contentType = "application/rdf+xml";
        final URI destinationURI = rdf.getResourceURI();
        final ByteArrayInputStream is = new ByteArrayInputStream(rdf.toString().getBytes());
        try {
            ModellerClient.doStreamPut(destinationURI, is, contentType);
        } catch (final ModellerClientFailedException e) {
            System.out.println(getMessage(e));
        }
    }

}
