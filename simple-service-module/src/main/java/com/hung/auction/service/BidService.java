package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.Bid;
import com.hung.auction.domain.Bidder;

public interface BidService {
	
	public void publish(final Bid bid);
	
	public void publish(final Integer bidId);
	
	public void save(Bid bid);
	
	public List<Bid> findByBidder(Bidder bidder);
	
	public Bid findById(Integer id);
}
