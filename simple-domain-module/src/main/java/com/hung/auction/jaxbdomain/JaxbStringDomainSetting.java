package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.hung.auction.domain.StringDomainSetting;

@XmlRootElement(name="JaxbStringDomainSetting")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbStringDomainSetting extends JaxbAbstractDomainSetting implements Serializable {

    public JaxbStringDomainSetting() {}

    public JaxbStringDomainSetting(StringDomainSetting stringDomainSetting) {
        setName(stringDomainSetting.getName());
        setValue(stringDomainSetting.getStringValue());
    }
}