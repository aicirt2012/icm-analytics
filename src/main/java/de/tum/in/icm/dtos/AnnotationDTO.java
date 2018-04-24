package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
public class AnnotationDTO {

    @XmlElement
    private String value;
    @XmlElement
    private NERType nerType;
    @XmlElement
    private String posType;
    @XmlElement
    private TextOrigin textOrigin;
    @XmlElement
    private String formattedValue;
    @XmlElement
    private List<RangeDTO> ranges = new ArrayList<>();

    // FIXME transient values are being output by REST API anyways
    @XmlTransient
    private List<Integer> plainTextIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlTextNodeIndices = new ArrayList<>();
    @XmlTransient
    private List<Integer> htmlAnnotationOffsets = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if(this.formattedValue ==null)
            this.formattedValue = value;
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

    public List<Integer> getHtmlTextNodeIndices() {
        return htmlTextNodeIndices;
    }

    public List<Integer> getHtmlAnnotationOffsets() {
        return htmlAnnotationOffsets;
    }

    public void addHtmlSourceOccurrence(int textNodeIndex, int annotationOffset) {
        htmlTextNodeIndices.add(textNodeIndex);
        htmlAnnotationOffsets.add(annotationOffset);
    }

    public void addXPathRange(String xPathStart, int offsetStart, String xPathEnd, int offsetEnd) {
        RangeDTO rangeDTO = new RangeDTO();
        rangeDTO.setxPathStart(xPathStart);
        rangeDTO.setOffsetStart(offsetStart);
        rangeDTO.setxPathEnd(xPathEnd);
        rangeDTO.setOffsetEnd(offsetEnd);
        ranges.add(rangeDTO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationDTO that = (AnnotationDTO) o;
        return Objects.equals(value, that.value) &&
                nerType == that.nerType &&
                Objects.equals(posType, that.posType) &&
                Objects.equals(ranges, that.ranges) &&
                Objects.equals(plainTextIndices, that.plainTextIndices) &&
                Objects.equals(htmlTextNodeIndices, that.htmlTextNodeIndices) &&
                Objects.equals(htmlAnnotationOffsets, that.htmlAnnotationOffsets);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, nerType, posType, ranges, plainTextIndices, htmlTextNodeIndices, htmlAnnotationOffsets);
    }

    public TextOrigin getTextOrigin() {
        return textOrigin;
    }

    public void setTextOrigin(TextOrigin textOrigin) {
        this.textOrigin = textOrigin;
    }

    public String getFormattedValue() {
        return formattedValue ;
    }

    public void setFormattedValue(String formattedValue) {
        this.formattedValue = formattedValue;
    }
}
