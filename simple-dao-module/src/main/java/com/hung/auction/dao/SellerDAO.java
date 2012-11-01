package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.Seller;

public interface SellerDAO {

    public void save(Seller seller);
    
    public Seller findById(final Integer id);
    
    public List<Seller> findAll();
    
    public List<Seller> findByName(String name);
}