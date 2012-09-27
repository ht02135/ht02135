package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.Domain;

public interface DomainService {
	
	public void save(Domain domain);
	
	public Domain findByName(String name);
	
	public List<String> getNamesByPatternStoreProcedure(String pattern);
	
	public List<String> getNamesByPatternSQL(String pattern);
	
	public List<String> getParentNamesByPatternSQL(String pattern);
	
	public List<Domain> getDomainsByPatternStoreProcedure(String pattern);
	
    public List<Domain> getDomainsByPatternSQL(String pattern);
    
    public List<Domain> getDomainsByFieldsPatternSQL(String pattern);
    
    public List<Domain> getDomainsByPatternHQL(String pattern);
	
	public List<Domain> findAll();
	
	public void deleteByName(String name);
}