package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.Bidder;

public interface BidderDAO {

    public void save(Bidder bidder);
    
    public List<Bidder> findByName(String name);

}