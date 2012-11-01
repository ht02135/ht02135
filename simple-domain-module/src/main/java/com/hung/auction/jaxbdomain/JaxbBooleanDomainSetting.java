package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.hung.auction.domain.BooleanDomainSetting;

@XmlRootElement(name="JaxbBooleanDomainSetting")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbBooleanDomainSetting extends JaxbAbstractDomainSetting implements Serializable {

    public JaxbBooleanDomainSetting() {}

    public JaxbBooleanDomainSetting(BooleanDomainSetting booleanDomainSetting) {
        setName(booleanDomainSetting.getName());
        setValue(booleanDomainSetting.getStringValue());
    }
}