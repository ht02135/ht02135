package com.hung.auction.jaxbdomain;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbAbstractDomainSetting {

    @XmlElement(name="name")
    private String name = "";

    @XmlElement(name="value")
    private String value;

    public JaxbAbstractDomainSetting() {}

    public JaxbAbstractDomainSetting(String name, String value) {
        setName(name);
        setValue(value);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String toString() {
        return "[name="+name+",value="+value+"]";
    }
}