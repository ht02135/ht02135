package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

	public static final Address EMPTY = new Address("empty", "empty", "empty"); 

	@Column(name="ADDR_STREET",nullable=false)
	private String street;
	
	@Column(name="ADDR_ZIPCODE",nullable=false)
	private String zipcode;
	
	@Column(name="ADDR_CITY",nullable=false)
	private String city;
	
	public Address() {}
	
	public Address(String street, String zipcode, String city) {
		setStreet(street);
		setZipcode(zipcode);
		setCity(city);
	}

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String toString() {
    	return "[street="+street+",zipcode="+zipcode+",city="+city+"]";
    }
}