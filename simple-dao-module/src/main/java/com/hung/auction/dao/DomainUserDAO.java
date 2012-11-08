package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.DomainUser;

public interface DomainUserDAO {

    public void save(DomainUser domainUser);
    
    public DomainUser findByLoginId(String loginId);
    
    public List<DomainUser> findAll();
    
    public void deleteByLoginId(String loginId);
}