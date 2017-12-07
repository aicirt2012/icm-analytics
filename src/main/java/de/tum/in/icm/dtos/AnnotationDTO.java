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
    private List<Integer> plainTextStartIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> plainTextEndIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlSourceStartIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlSourceEndIndices = new ArrayList<>();

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

    public List<Integer> getPlainTextStartIndices() {
        return plainTextStartIndices;
    }

    public List<Integer> getPlainTextEndIndices() {
        return plainTextEndIndices;
    }

    public void addPlainTextOccurence(int startIndex, int endIndex) {
        plainTextStartIndices.add(startIndex);
        plainTextEndIndices.add(endIndex);
    }

    public List<Integer> getHtmlSourceStartIndices() {
        return htmlSourceStartIndices;
    }

    public List<Integer> getHtmlSourceEndIndices() {
        return htmlSourceEndIndices;
    }

    public void addHtmlSourceOccurence(int startIndex, int endIndex) {
        htmlSourceStartIndices.add(startIndex);
        htmlSourceEndIndices.add(endIndex);
    }

}
