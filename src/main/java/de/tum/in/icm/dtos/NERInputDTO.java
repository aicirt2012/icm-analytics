package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NERInputDTO {

    //TODO make fields private and add getters/setters
    @XmlElement
    public String emailId;
    @XmlElement
    public String htmlSource;

}
