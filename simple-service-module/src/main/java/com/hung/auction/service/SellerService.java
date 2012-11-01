package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.Seller;

public interface SellerService {
	
	public void save(Seller seller);
	
	public Seller findById(Integer id);
	
	public List<Seller> findAll();
	
	public List<Seller> findByName(String name);
}
