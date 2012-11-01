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
	
	/*
	effective java: equals contract
		1>reflexive : x.equals(x) true
		2>symmetric: x.equals(y) and y.equals(x) true
		3>transitive: x=y, y=z, then x=z true
		4>consistent
		5>null check: x.equal(null)  false
	*/
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
    
	/*
	effective java: hashCode contract
		1>must override hashCode when override equals
		2>consistent
		3>if 2 objects are equal according to equals(object), then hashCode are equal
	*/
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
