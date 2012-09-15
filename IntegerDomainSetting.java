package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@DiscriminatorValue("INTEGER")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class IntegerDomainSetting extends StringDomainSetting implements Serializable {

	public IntegerDomainSetting() { super(); }

	public IntegerDomainSetting(String name, Integer integerValue, Domain settingDomain) {
		super(name, integerValue.toString(), settingDomain);
	}
	
    public Integer getIntegerValue() { return Integer.valueOf(getStringValue()); }
    public void setInteger(Integer integerValue) { setStringValue(integerValue.toString()); }
    
    public IntegerDomainSetting clone() {
    	IntegerDomainSetting integerDomainSetting = new IntegerDomainSetting(name, Integer.valueOf(stringValue), settingDomain);
    	return integerDomainSetting;
    }
}