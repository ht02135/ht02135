package com.hung.auction.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AUCTION_BID")
public class Bid implements Serializable {

	@Id
	@SequenceGenerator(name = "bid_seq_gen", sequenceName = "bid_sequence")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "bid_seq_gen")
	@Column(name="BID_ID", nullable=false)
	private Integer id;
	
	@Column(name="BID_PRICE")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private Item item;
	
	@ManyToOne
	@JoinColumn(name="BIDDER_ID")
	private Bidder bidder;
	
	public Bid() {}
	
	public Bid(Item item, Bidder bidder, Double price) {
		setPrice(price);
		setItem(item);
		setBidder(bidder);
	}
	
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    
    public Bidder getBidder() { return bidder; }
    public void setBidder(Bidder bidder) { this.bidder = bidder; }
    
    public String toString() {
    	return "Bid=["+bidder+", "+item+"]";
    }
}