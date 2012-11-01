package com.hung.auction.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("BIDDER")
public class Bidder extends User implements Serializable {

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="street", column=@Column(name="SHIPPING_STREET")),
			@AttributeOverride(name="zipcode", column=@Column(name="SHIPPING_ZIPCODE")),
			@AttributeOverride(name="city", column=@Column(name="SHIPPING_CITY"))
	})
	private Address shippingAddress;
	
	@OneToMany(mappedBy="bidder", cascade=CascadeType.ALL)
	private List<Bid> bids;
	
    public Bidder() { super(); }
    
    public Bidder(String name, Address homeAddress, Address shippingAddress, String email) {
    	super(name, homeAddress, email);
    	setShippingAddress(shippingAddress);
    }

    public Address getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }
    
    public List<Bid> getBids() { return bids; }
    public void setBids(List<Bid> bids) { this.bids = bids; }
    
    public String toString() {
    	return "Bidder=[name="+getName()+"]";
    }
}