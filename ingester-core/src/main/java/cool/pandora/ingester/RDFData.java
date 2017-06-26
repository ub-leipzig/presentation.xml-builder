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

import org.xmlbeam.annotation.XBDocURL;
import org.xmlbeam.annotation.XBRead;
import org.xmlbeam.annotation.XBWrite;

import java.net.URI;
import java.util.List;

/**
 * RDFData
 *
 * @author Christopher Johnson
 */
@XBDocURL("resource://output-data/mets-rdf.xml")
public interface RDFData {
    interface Resource {
    }

    /**
     * @return List<String>
     */
    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    List<String> getResourceURIList();

    /**
     * @return URI
     */
    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    URI getResourceURI();

    /**
     * @return List<Resource>
     */
    @XBRead("/rdf:RDF/*")
    List<Resource> getGraph();

    /**
     * @return List<Resource>
     */
    @XBRead("/rdf:RDF/rdf:Description[@rdf:about]")
    List<Resource> getResources();

    /**
     * @return List<Resource>
     */
    @XBRead("/rdf:RDF/rdf:Description[@rdf:nodeID]")
    List<Resource> getAnonResources();

    /**
     *
     * @param resources Lists<Resource>
     */
    @XBWrite("/rdf:RDF/*")
    void setResource(List<Resource> resources);

    /**
     *
     * @param resources Resource
     */
    @XBWrite("/rdf:RDF/*")
    void setResources(Resource resources);
}

