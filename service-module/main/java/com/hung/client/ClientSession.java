package com.hung.auction.client;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.log4j.Logger;

public class ClientSession implements Serializable {

	private static Logger log = Logger.getLogger(ClientSession.class);
	
	private String loginId = "";
	private String domainName = "";
	
	public ClientSession() {
	}
	
	public ClientSession(String domainName, String loginId) {
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
	
	@Override
    public boolean equals(Object obj) {
    	log.info("equals: called, this.toString()="+this.toString()+" obj.toString()="+obj.toString());
    	
        if (obj == null) {
        	log.info("equals: obj == null");
        	return false;	// null check
        }
        if (this == obj) { 
        	log.info("equals: this == obj");
        	return true;	// reference check (reflexive, symmetric, transitive => true)
        }

        // Some people would prefer 'if (!(o instanceof ClassName)) ...'
        if (getClass() != obj.getClass()) {
        	log.info("equals: getClass() != obj.getClass()");
        	return false;
        }
        
        // field comparison
        ClientSession otherObj = (ClientSession) obj;
    	return new EqualsBuilder()
    		.append(this.loginId, otherObj.getLoginId())
    		.append(this.domainName, otherObj.getDomainName())
    		.isEquals();
    }
    
    @Override
    public int hashCode(){
    	log.info("hashCode: called, this.toString()="+this.toString());
    	
    	int hasCode = new HashCodeBuilder()
    		.append(this.loginId)
    		.append(this.domainName)
    		.toHashCode();
    	log.info("hashCode: hasCode="+hasCode);
    	
        return hasCode;
    }
	
    public ClientSession clone() {
    	return new ClientSession(domainName, loginId);
    }
	
	public String toString() {
		return "[domainName="+domainName+", loginId="+loginId+"]";
	}
}
