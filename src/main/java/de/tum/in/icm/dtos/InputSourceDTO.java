package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class InputSourceDTO {

    @XmlElement
    private String bodySource;
    @XmlElement
    private String subjectSource;
    @XmlElement
    private String emailId;
    @XmlElement
    private List<PatternDTO> patterns = new ArrayList<PatternDTO>();



    public String getBodySource() {
        return bodySource;
    }

    public void setBodySource(String bodySource) {
        this.bodySource = bodySource;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<PatternDTO> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternDTO> patterns) {
        this.patterns = patterns;
    }


    public String getSubjectSource() {
        return subjectSource;
    }

    public void setSubjectSource(String subjectSource) {
        this.subjectSource = subjectSource;
    }
}
