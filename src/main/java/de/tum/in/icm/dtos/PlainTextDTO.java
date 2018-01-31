package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PlainTextDTO  {


    @XmlElement
    private String plainText;
    @XmlElement
    private String emailId;
    @XmlElement
    private List<PatternDTO> regexPatterns = new ArrayList<PatternDTO>();



    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
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
