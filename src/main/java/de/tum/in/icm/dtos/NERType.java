package de.tum.in.icm.dtos;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum NERType {

    //    @XmlEnumValue("CustomValue")
    LOCATION,
    PERSON,
    ORGANIZATION,
    MONEY,
    DATE,
    TIME,
    PERCENT,
    NUMBER,
    O,
}
