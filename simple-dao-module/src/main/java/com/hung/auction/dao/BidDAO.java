package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.Bid;
import com.hung.auction.domain.Bidder;

public interface BidDAO {

    public void save(Bid bid);
    
    public List<Bid> findByBidder(Bidder bidder);
    
    public Bid findById(Integer id);

}