package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.annotation.Annotation;
import java.util.*;


@XmlRootElement
public class ResultDTO {

    @XmlElement
    public String EmailId;
    @XmlElement
    public List<AnnotationDTO>Annotations;
}
