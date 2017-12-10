package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class AnnotationDTO {

    @XmlElement
    private String value;
    @XmlElement
    private NERType nerType;
    @XmlElement
    private String posType;
    @XmlElement
    private List<RangeDTO> ranges;

    // FIXME transient values are being output by REST API anyways
    @XmlTransient
    private List<Integer> plainTextIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlParentTagIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlParentTagOffsets = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NERType getNerType() {
        return nerType;
    }

    public void setNerType(NERType nerType) {
        this.nerType = nerType;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public List<RangeDTO> getRanges() {
        return ranges;
    }

    public void setRanges(List<RangeDTO> ranges) {
        this.ranges = ranges;
    }

    public List<Integer> getPlainTextIndices() {
        return plainTextIndices;
    }

    public void addPlainTextIndex(int startIndex) {
        plainTextIndices.add(startIndex);
    }

    public List<Integer> getHtmlParentTagIndices() {
        return htmlParentTagIndices;
    }

    public List<Integer> getHtmlParentTagOffsets() {
        return htmlParentTagOffsets;
    }

    public void addHtmlSourceOccurrence(int htmlParentTagIndex, int htmlParentTagOffset) {
        htmlParentTagIndices.add(htmlParentTagIndex);
        htmlParentTagOffsets.add(htmlParentTagOffset);
    }

}
