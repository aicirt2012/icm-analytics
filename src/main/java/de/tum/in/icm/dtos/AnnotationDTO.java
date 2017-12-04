package de.tum.in.icm.dtos;

import javax.ws.rs.POST;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnnotationDTO {


    public AnnotationDTO ()
    {}

    public AnnotationDTO (String value, String nerType,String posType,int startIndex, int endIndex)
    {
        Value = value;
        NERType = NERType.valueOf(nerType);
        // decide whether or not we need this
        POSType = posType;
        StartIndex = startIndex;
        EndIndex = endIndex;

    }

    @XmlElement
    String Value;

    @XmlElement
    NERType NERType;

    @XmlElement
    String POSType;

    @XmlElement
    int StartIndex;

    @XmlElement
    int EndIndex;
}
