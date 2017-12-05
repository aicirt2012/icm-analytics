package de.tum.in.icm.dtos;

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

}
