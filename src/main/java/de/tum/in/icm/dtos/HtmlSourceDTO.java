package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
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
    private List<String> regexPatterns = new ArrayList<String>();
    //TODO: this should be for every pattern
    @XmlElement
    private boolean autoCompleteTaskLabel;


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

    public List<String> getRegexPatterns() {
        return regexPatterns;
    }

    public void setRegexPatterns(List<String> regexPatterns) {
        this.regexPatterns = regexPatterns;
    }

    public boolean isAutoCompleteTaskLabel() {
        return autoCompleteTaskLabel;
    }

    public void setAutoCompleteTaskLabel(boolean autoCompleteTaskLabel) {
        this.autoCompleteTaskLabel = autoCompleteTaskLabel;
    }
}
