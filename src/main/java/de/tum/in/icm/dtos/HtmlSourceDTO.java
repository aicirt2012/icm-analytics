package de.tum.in.icm.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class HtmlSourceDTO  {

    @XmlElement
    private String htmlSource;
    @XmlElement
    private String emailId;
    @XmlElement
    private List<PatternDTO> regexPatterns = new ArrayList<PatternDTO>();



    public String getHtmlSource() {
        return htmlSource;
    }

    public void setHtmlSource(String htmlSource) {
        this.htmlSource = htmlSource;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<PatternDTO> getRegexPatterns() {
        return regexPatterns;
    }

    public void setRegexPatterns(List<PatternDTO> regexPatterns) {
        this.regexPatterns = regexPatterns;
    }


}
