package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.DomainUser;

public interface DomainUserService {
	
	public void save(DomainUser domainUser);
	
	public DomainUser findByLoginId(String loginId);
	
	public List<DomainUser> findAll();
}
