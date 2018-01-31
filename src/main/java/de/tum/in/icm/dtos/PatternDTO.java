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
    @XmlElement
    private boolean caseSensitive;

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

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
}
