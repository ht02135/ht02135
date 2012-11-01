package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.hung.auction.domain.IntegerDomainSetting;

@XmlRootElement(name="JaxbIntegerDomainSetting")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbIntegerDomainSetting extends JaxbAbstractDomainSetting implements Serializable {

    public JaxbIntegerDomainSetting() {}

    public JaxbIntegerDomainSetting(IntegerDomainSetting integerDomainSetting) {
        setName(integerDomainSetting.getName());
        setValue(integerDomainSetting.getStringValue());
    }
}