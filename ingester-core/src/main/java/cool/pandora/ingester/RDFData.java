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

    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    List<String> getResourceURIList();

    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    URI getResourceURI();

    @XBRead("/rdf:RDF/*")
    List<Resource> getGraph();

    @XBRead("/rdf:RDF/rdf:Description[@rdf:about]")
    List<Resource> getResources();

    @XBRead("/rdf:RDF/rdf:Description[@rdf:nodeID]")
    List<Resource> getAnonResources();

    @XBWrite("/rdf:RDF/*")
    void setResource(List<Resource> resources);

    @XBWrite("/rdf:RDF/*")
    void setResources(Resource resources);
}

