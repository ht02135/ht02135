package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.ClientCache;
import com.hung.auction.domain.Domain;

public interface ClientCacheDAO {

	public void save(ClientCache clientCache);
	
	public List<ClientCache> getClientCaches(String loginId, String entityType);
}