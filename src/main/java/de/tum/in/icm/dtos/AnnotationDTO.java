package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnnotationDTO {

    @XmlElement
    public String value;
    @XmlElement
    public NERType nerType;
    @XmlElement
    public String posType;
    @XmlElement
    public int startIndex;
    @XmlElement
    public int endIndex;

    public AnnotationDTO(String value, String nerType, String posType, int startIndex, int endIndex) {
        this.value = value;
        this.nerType = NERType.valueOf(nerType);
        // decide whether or not we need this
        this.posType = posType;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

}
