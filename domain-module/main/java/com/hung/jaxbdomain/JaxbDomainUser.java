package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hung.auction.domain.DomainUser;

@XmlRootElement(name="JaxbDomainUser")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbDomainUser implements Serializable {

	@XmlAttribute(name="loginId")
	private String loginId = "";
	
	@XmlElement(name="name")
	private String name = "";
	
	@XmlElement(name="userDomainName")
	private String userDomainName = "";
	
    public JaxbDomainUser() {}
    
    public JaxbDomainUser(String loginId, String name, String userDomainName) {
    	setLoginId(loginId);
    	setName(name);
    	setUserDomainName(userDomainName);
    }
    
    public JaxbDomainUser(DomainUser domainUser) {
    	setLoginId(domainUser.getLoginId());
    	setName(domainUser.getName());
    	setUserDomainName(domainUser.getUserDomain().getName());
    }
    
    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUserDomainName() { return userDomainName; }
    public void setUserDomainName(String userDomainName) { this.userDomainName = userDomainName; }
    
    public String toString() {
    	return "[loginId="+loginId+",name="+name+",userDomainName="+userDomainName+"]";
    }
}