package de.tum.in.icm.dtos;

import java.util.Objects;

public class RangeDTO {

    private String xPathStart = "";
    private String xPathEnd = "";
    private int offsetStart = 0;
    private int offsetEnd = 0;

    public String getxPathStart() {
        return xPathStart;
    }

    public void setxPathStart(String xPathStart) {
        this.xPathStart = xPathStart;
    }

    public String getxPathEnd() {
        return xPathEnd;
    }

    public void setxPathEnd(String xPathEnd) {
        this.xPathEnd = xPathEnd;
    }

    public int getOffsetStart() {
        return offsetStart;
    }

    public void setOffsetStart(int offsetStart) {
        this.offsetStart = offsetStart;
    }

    public int getOffsetEnd() {
        return offsetEnd;
    }

    public void setOffsetEnd(int offsetEnd) {
        this.offsetEnd = offsetEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeDTO rangeDTO = (RangeDTO) o;
        return offsetStart == rangeDTO.offsetStart &&
                offsetEnd == rangeDTO.offsetEnd &&
                Objects.equals(xPathStart, rangeDTO.xPathStart) &&
                Objects.equals(xPathEnd, rangeDTO.xPathEnd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(xPathStart, xPathEnd, offsetStart, offsetEnd);
    }
}
