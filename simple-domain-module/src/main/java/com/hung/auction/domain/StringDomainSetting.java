package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="DOMAIN_SETTING")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="SETTING_TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class StringDomainSetting implements Serializable {

	@Id
	@SequenceGenerator(name = "setting_seq_gen", sequenceName = "setting_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "setting_seq_gen")
	@Column(name="SETTING_ID", nullable=false)
	private Integer id;
	
	@Column(name="SETTING_NAME", nullable=false)
	protected String name;
	
	@Column(name="SETTING_VALUE", nullable=false)
	protected String stringValue;
	
	@ManyToOne
	@JoinColumn(name="SETTING_DOMAIN_NAME")
	protected Domain settingDomain;
	
	public StringDomainSetting() {}
	
	public StringDomainSetting(String name, String stringValue, Domain settingDomain) {
		setName(name);
		setStringValue(stringValue);
		setSettingDomain(settingDomain);
	}
	
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getStringValue() { return stringValue; }
    public void setStringValue(String stringValue) { this.stringValue = stringValue; }
    
    public Domain getSettingDomain() { return settingDomain; }
    public void setSettingDomain(Domain settingDomain) { this.settingDomain = settingDomain; }
    
    public StringDomainSetting clone() {
    	StringDomainSetting stringDomainSetting = new StringDomainSetting(name, stringValue, settingDomain);
    	return stringDomainSetting;
    }
    
    public String toString() {
    	return "[name="+name+",value="+stringValue+",settingDomain="+settingDomain+"]";
    }
}