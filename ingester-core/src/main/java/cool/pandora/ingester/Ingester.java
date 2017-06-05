package cool.pandora.ingester;

import org.xmlbeam.XBProjector;

import java.io.IOException;
import java.util.List;

class Ingester {

    private Ingester() {
    }

    /**
     * @param url String
     * @return XBProjector
     * @throws IOException Exception
     */
    static RDFData getRDFProjectionFromURL(final String url) throws IOException {
        XBProjector projector = new XBProjector(XBProjector.Flags.TO_STRING_RENDERS_XML);
        return projector.io().url(url).read(RDFData.class);
    }

    /**
     * @param rdf RDFData
     * @return ResourceId
     */
    static List<String> getResourceURIList(final RDFData rdf) {
        return rdf.getResourceURIList();
    }

    /**
     * @param rdf RDFData
     * @return ResourceId
     */
    static List<RDFData.Resource> getResources(final RDFData rdf) {
        return rdf.getResources();
    }

    /**
     * @return XBProjector
     * @throws IOException Exception
     */
    static RDFData buildRDFDoc() throws IOException {
        XBProjector projector = new XBProjector();
        return projector.projectEmptyDocument(RDFData.class);
    }


}


