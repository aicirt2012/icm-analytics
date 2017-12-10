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
    MISC,
    O

}
