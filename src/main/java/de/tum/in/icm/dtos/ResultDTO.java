package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class ResultDTO {

    @XmlElement
    public String emailId;
    @XmlElement
    public List<AnnotationDTO> annotations;

}
