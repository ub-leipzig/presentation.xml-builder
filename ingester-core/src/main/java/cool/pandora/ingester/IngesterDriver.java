package cool.pandora.ingester;

import org.slf4j.Logger;
import org.xmlbeam.XBProjector;

import java.io.IOException;
import java.util.List;
import cool.pandora.ingester.common.TransferProcess;
import static org.slf4j.LoggerFactory.getLogger;

class IngesterDriver {
    private static final Logger logger = getLogger(IngesterDriver.class);

    private IngesterDriver() {
    }

    /**
     * The main entry point
     *
     * @param args from the command line
     */
    public static void main(final String[] args) {
        final IngesterDriver driver = new IngesterDriver();

        try {
            driver.run(args);

        } catch (final Exception e) {
            logger.error("Error performing ingest: {}", e.getMessage());
            logger.debug("Stacktrace: ", e);
        }
    }

    private void run(final String[] args) {
        final ArgParser parser = new ArgParser();
        final TransferProcess processor = parser.parse(args);
        processor.run();
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


