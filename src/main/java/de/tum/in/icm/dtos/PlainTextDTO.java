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
    private List<String> regexPatterns = new ArrayList<String>();
    //TODO: this should be for every pattern
    @XmlElement
    private boolean matchTillSentenceEnd;


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

    public List<String> getRegexPatterns() {
        return regexPatterns;
    }

    public void setRegexPatterns(List<String> regexPatterns) {
        this.regexPatterns = regexPatterns;
    }


    public boolean isMatchTillSentenceEnd() {
        return matchTillSentenceEnd;
    }

    public void setMatchTillSentenceEnd(boolean matchTillSentenceEnd) {
        this.matchTillSentenceEnd = matchTillSentenceEnd;
    }
}
