package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TaskAnnotationDTO extends AnnotationDTO {


    @XmlElement
    private String dueDate;
    @XmlElement
    private List<String> relatedPersons;

    public TaskAnnotationDTO(String dueDate, List<String> relatedPersons) {
        this.dueDate = dueDate;
        this.relatedPersons = relatedPersons;
    }


    public List<String> getRelatedPersons() {
        return relatedPersons;
    }

    public String getDueDate() {
        return dueDate;
    }

}
