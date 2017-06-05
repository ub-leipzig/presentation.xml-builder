package cool.pandora.ingester;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;

/**
 * XmlFileWriter
 *
 * @author Christopher Johnson
 */
public class XmlFileWriter extends ByteArrayOutputStream {
    /**
     *
     * @return XMLWriterBuilder
     */
    public static XMLWriterBuilder write() {
        return new XMLWriterBuilder();
    }

    protected XmlFileWriter() {
    }

    /**
     *
     * @param resDescriptor ResourceDescriptor
     * @return marshalled output
     * @throws JAXBException Exception
     */
    private static ByteArrayOutputStream marshal(final ResourceDescriptor resDescriptor) throws JAXBException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final JAXBContext jaxbContext = JAXBContext.newInstance(ResourceDescriptor.class);
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(resDescriptor, out);
        return out;
    }

    /**
     *
     */
    public static class XMLWriterBuilder {
        private String rdfBody;

        /**
         *
         * @param rdfBody String
         * @return this
         */
        public XmlFileWriter.XMLWriterBuilder rdfBody(final String rdfBody) {
            this.rdfBody = rdfBody;
            return this;
        }

        /**
         *
         * @return marshalled output
         * @throws JAXBException Exception
         */
        public ByteArrayOutputStream build() throws JAXBException {
            final String descriptor = this.rdfBody;
            final ResourceDescriptor resDescriptor = new ResourceDescriptor();
            resDescriptor.setBody(descriptor);
            return XmlFileWriter.marshal(resDescriptor);
        }
    }
}
