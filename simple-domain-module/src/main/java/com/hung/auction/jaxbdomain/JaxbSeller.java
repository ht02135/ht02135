package com.hung.auction.jaxbdomain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.hung.auction.domain.Seller;

@XmlRootElement(name="JaxbSeller")
@XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
public class JaxbSeller implements Serializable {
	
	@XmlAttribute(name="id")
	private Integer id = null;
	
	@XmlElement(name="name")
	private String name = "";
	
	@XmlElement(name="email")
	private String email = "";
	
    public JaxbSeller() {}
    
    public JaxbSeller(Integer id, String name, String email) {
    	setId(id);
    	setName(name);
    	setEmail( email);
    }
    
    public JaxbSeller(Seller seller) {
    	setId(seller.getId());
    	setName(seller.getName());
    	setEmail(seller.getEmail());
    }
    
    public JaxbSeller(String name, String email) {
    	setName(name);
    	setEmail( email);
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public Integer getId() {
    	return id;
    }

    public void setName(String name) {
    	this.name = name;
    }
	
    public String getName() {
    	return name;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
	
    public String getEmail() {
    	return email;
    }
    
    public String toString() {
    	return "[name=" + name + ",email="+email+"]";
    }
}