package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ClientCacheId implements Serializable {

	private String loginId;
	private String entityType;
	private String entityId;
	
    public ClientCacheId() {}
    
    public ClientCacheId(String loginId, String entityType, String entityId) {
    	setLoginId(loginId);
    	setEntityType(entityType);
    	setEntityId(entityId);
    }
    
    public String getLoginId() {
    	return loginId;
    }
    
    public void setLoginId(String loginId) {
    	this.loginId = loginId;
    }
    
    public String getEntityType() {
    	return entityType;
    }
    
    public void setEntityType(String entityType) {
    	this.entityType = entityType;
    }
    
    public String getEntityId() {
    	return entityId;
    }
    
    public void setEntityId(String entityId) {
    	this.entityId = entityId;
    }
    
    public String toString() {
    	return "[loginId="+loginId+",entityType="+entityType+",entityId="+entityId+"]";
    }
}