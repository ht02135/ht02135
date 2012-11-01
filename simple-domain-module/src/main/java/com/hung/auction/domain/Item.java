package com.hung.auction.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AUCTION_ITEM")
public class Item implements Serializable {

	@Id
	@SequenceGenerator(name = "item_seq_gen", sequenceName = "item_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "item_seq_gen")
	@Column(name="ITEM_ID", nullable=false)
	private Integer id;

	@Column(name="ITEM_NAME")
	private String name;
	
	@Column(name="ITEM_PRICE")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="SELLER_ID")
	private Seller seller;
	
	@OneToMany(mappedBy="item", cascade=CascadeType.ALL)
	private List<Bid> bids;
	
	public Item() {}
	
	public Item(String name, Double price, Seller seller) {
		setName(name);
		setPrice(price);
		setSeller(seller);
	}
	
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
    
    public List<Bid> getBids() { return bids; }
    public void setBids(List<Bid> bids) { this.bids = bids; }
    
    public String toString() {
    	return "Item=[name="+getName()+"]";
    }
}