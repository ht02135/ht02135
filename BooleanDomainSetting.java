package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@DiscriminatorValue("BOOLEAN")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BooleanDomainSetting extends StringDomainSetting implements Serializable {

	public BooleanDomainSetting() { super(); }

	public BooleanDomainSetting(String name, Boolean booleanValue, Domain settingDomain) {
		super(name, (booleanValue == Boolean.TRUE) ? "TRUE" : "FALSE", settingDomain);
	}

    public Boolean getBooleanValue() { 
    	if (getStringValue().equalsIgnoreCase("true")) {
    		return Boolean.TRUE;
    	} else {
    		return Boolean.FALSE;
    	}
    }
    
    public void setBoolean(Boolean booleanValue) { 
    	if (booleanValue == Boolean.TRUE) {
    		setStringValue("TRUE");
    	} else {
    		setStringValue("FALSE");
    	}
    }
    
    public BooleanDomainSetting clone() {
    	BooleanDomainSetting booleanDomainSetting = new BooleanDomainSetting(name, (stringValue.equalsIgnoreCase("TRUE")) ? Boolean.TRUE : Boolean.FALSE, settingDomain);
    	return booleanDomainSetting;
    }
}