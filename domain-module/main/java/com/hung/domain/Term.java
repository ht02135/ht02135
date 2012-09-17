package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TERM")
public class Term implements Serializable {
 
	@Id
    @Column(name="TERM_ID", nullable=false)
    protected String id;
	
	public Term() {
	}
	
    public Term(String id) {
    	setId(id);
    }
     
    // ----------------------------------
    //Getter and Setter methods
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String toString() {
    	return "[id="+id+"]";
    }
}