package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.SellerDAO;
import com.hung.auction.domain.Seller;

public class SellerServiceImpl implements SellerService {
	
	private SellerDAO sellerDAO;
	
	public SellerServiceImpl() {
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Seller seller) {
		sellerDAO.save(seller);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Seller findById(Integer id) {
		return sellerDAO.findById(id);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Seller> findAll() {
		return sellerDAO.findAll();
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Seller> findByName(String name) {
		return sellerDAO.findByName(name);
	}
	
	// injection methods

	public void setSellerDAO(SellerDAO sellerDAO) {
		this.sellerDAO = sellerDAO;
	}
	
}