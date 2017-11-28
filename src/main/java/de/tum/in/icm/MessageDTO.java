package de.tum.in.icm;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageDTO {
    @XmlElement
    public String[] lines;
}
