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

import cool.pandora.modeller.ModellerClient;
import cool.pandora.modeller.ModellerClientFailedException;
import org.xmlbeam.XBProjector;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

/**
 * IngesterTest
 *
 * @author Christopher Johnson
 */
public class IngesterTest {

    private IngesterTest() {
    }

    public static void main(final String[] args) throws IOException, JAXBException {
        final XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        final RDFData rdf = projector.io().fromURLAnnotation(RDFData.class);
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
