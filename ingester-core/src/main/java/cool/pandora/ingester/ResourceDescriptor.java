package cool.pandora.ingester;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ResourceDescriptor
 *
 * @author Christopher Johnson
 */
@XmlRootElement
public class ResourceDescriptor {

    private String body;

    /**
     *
     * @param body String
     */
    @XmlElement
    void setBody(final String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

}