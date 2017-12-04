package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NERInputDTO {

    @XmlElement
    public String[] lines;

}
