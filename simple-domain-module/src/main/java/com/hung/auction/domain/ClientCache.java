package com.hung.auction.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="CLIENT_CACHE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ClientCache implements Serializable {

	public static final String DOMAIN_ENTITY = "Domain"; 
	public static final String DOMAIN_USER_ENTITY = "DomainUser"; 

	// use ClientCacheId composite keys
	@Id
	@AttributeOverrides({
		@AttributeOverride(name="loginId", column=@Column(name="CLIENT_LOGIN_ID", nullable=false)),
		@AttributeOverride(name="entityType", column=@Column(name="CLIENT_ENTITY_TYPE", nullable=false)),
		@AttributeOverride(name="entityId", column=@Column(name="CLIENT_ENTITY_ID", nullable=false))
	})
	private ClientCacheId id;
	
	// http://www.java2s.com/Code/Java/Hibernate/JavaTypeVSHibernateType.htm
	// mapping: Java Type        Hibernate Type          SQL Type
	// -----------------------------------------------------------------------
	//          java.util.Date   date, time, timestamp   DATE, TIME, TIMESTAMP
	//
	// @Type(type="timestamp") holds date and time
	// @Type(type="date") holds only date
	@Column(name="CLIENT_INSERTED_TIME")
	@Type(type="timestamp")
	private Date insertedTime;
	
	@Column(name="CLIENT_INSERTED_DATE")
	@Type(type="date")
	private Date insertedDate;
	
    public ClientCache() {}
    
    public ClientCache(ClientCacheId id, Date insertedTime, Date insertedDate) {
    	setId(id);
    	setInsertedTime(insertedTime);
    	setInsertedDate(insertedDate);
    }
    
    public ClientCacheId getId() {
    	return id;
    }
    
    public void setId(ClientCacheId id) {
    	this.id = id;
    }
    
    public Date getInsertedTime() {
    	return insertedTime;
    }
    
    public void setInsertedTime(Date insertedTime) {
    	this.insertedTime = insertedTime;
    }
    
    public Date getInsertedDate() {
    	return insertedDate;
    }
    
    public void setInsertedDate(Date insertedDate) {
    	this.insertedDate = insertedDate;
    }
    
    public String toString() {
    	return "[id="+id+",insertedTime="+insertedTime+",insertedDate="+insertedDate+"]";
    }
}