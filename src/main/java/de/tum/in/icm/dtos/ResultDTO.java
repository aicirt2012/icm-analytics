package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ResultDTO {

    @XmlElement
    private String emailId;
    @XmlElement
    private List<AnnotationDTO> annotations = new ArrayList<>();

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<AnnotationDTO> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationDTO> annotations) {
        this.annotations = annotations;
    }

    public void addAnnotations(List<AnnotationDTO> annotations) {
        this.annotations.addAll(annotations);
    }

}
