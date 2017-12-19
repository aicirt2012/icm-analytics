package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum NERType {

    LOCATION,
    PERSON,
    ORGANIZATION,
    DURATION,
    MONEY,
    DATE,
    TIME,
    PERCENT,
    NUMBER,
    ORDINAL,
    MISC,
    O,
    NOT_IMPLEMENTED;

    public static NERType fromString(String nerType) {
        for (NERType type : NERType.values()) {
            if (type.toString().equalsIgnoreCase(nerType)) {
                return type;
            }
        }
        return NERType.NOT_IMPLEMENTED;
    }

}
