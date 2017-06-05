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
@XBDocURL("resource://data/ListRecords-oai_dc.xml")
public interface RDFData {
    interface Resource {
    }

    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    List<String> getResourceURIList();

    @XBRead("/rdf:RDF/rdf:Description/@rdf:about")
    URI getResourceURI();

    @XBRead("/rdf:RDF/rdf:Description")
    List<Resource> getResources();

    @XBWrite("/rdf:RDF/*")
    void setResource(Resource resource);
}

