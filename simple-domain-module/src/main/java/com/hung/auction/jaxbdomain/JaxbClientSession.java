package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement(name="JaxbClientSession")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbClientSession implements Serializable {
	
    @NotEmpty
	@XmlAttribute(name="loginId")
	private String loginId = "";
	
    @NotEmpty
	@XmlAttribute(name="domainName")
	private String domainName = "";
	
	public JaxbClientSession() {
	}
	
	public JaxbClientSession(String domainName, String loginId) {
		setDomainName(domainName);
		setLoginId(loginId);
	}
	
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getDomainName() {
		return domainName;
	}
	
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
    public JaxbClientSession clone() {
    	return new JaxbClientSession(domainName, loginId);
    }
	
	public String toString() {
		return "[domainName="+domainName+", loginId="+loginId+"]";
	}
}
