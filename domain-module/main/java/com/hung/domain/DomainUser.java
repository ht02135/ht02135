package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="DOMAIN_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@XmlRootElement(name="DomainUser")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class DomainUser implements Serializable {
	// picking EHCache as cache provider for 2nd level cache.  EHCache only support :
	// below is reason for choosing CacheConcurrencyStrategy.READ_WRITE, 
	// READ_ONLY = use it if data never change
	// NONSTRICT_READ_WRITE = populate the cache from loads only, use it if data hardly change and stale data not critical concern)
	// READ_WRITE = use complex strategy to maintain read committed isolation and cache up to date
	
	public static final String ADMIN_NAME = "admin"; 
	
	@Id
	@Column(name="USER_LOGIN_ID", nullable=false)
	@XmlAttribute(name="loginId")
	private String loginId;
	
	@Column(name="USER_NAME", nullable=false)
	@XmlElement(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="USER_DOMAIN_NAME")
    @XmlAnyElement(lax = true)
	private Domain userDomain;
	
    public DomainUser() {}
    
    public DomainUser(String loginId, String name, Domain userDomain) {
    	setLoginId(loginId);
    	setName(name);
    	setUserDomain(userDomain);
    }
    
    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Domain getUserDomain() { return userDomain; }
    public void setUserDomain(Domain userDomain) { this.userDomain = userDomain; }
    
    public String toString() {
    	String tmpLoginId = (loginId == null) ? "" : loginId;
    	return "[tmpLoginId="+tmpLoginId+",name="+name+",userDomain="+userDomain+"]";
    }
}