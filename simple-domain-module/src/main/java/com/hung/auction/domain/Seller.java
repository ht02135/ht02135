package com.hung.auction.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("SELLER")
public class Seller extends User implements Serializable {

	@OneToMany(mappedBy="seller", cascade=CascadeType.ALL)
	private List<Item> items;
	
    public Seller() { super(); }
    
    public Seller(String name, Address homeAddress, String email) {
    	super(name, homeAddress, email);
    }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    
    public String toString() {
    	return super.toString();
    }
}