package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PatternDTO
{
    @XmlElement
    private String label;
    @XmlElement
    private boolean matchTillSentenceEnd;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isMatchTillSentenceEnd() {
        return matchTillSentenceEnd;
    }

    public void setMatchTillSentenceEnd(boolean matchTillSentenceEnd) {
        this.matchTillSentenceEnd = matchTillSentenceEnd;
    }
}
