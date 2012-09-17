package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.ClientCache;

public interface ClientCacheService {

	public void save(ClientCache clientCache);

	public void saveClientCaches(List<ClientCache> clientCaches);
	
	public List<ClientCache> getClientCaches(String loginId, String entityType);
}
