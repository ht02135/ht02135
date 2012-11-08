package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.ClientCacheDAO;
import com.hung.auction.domain.ClientCache;

public class ClientCacheServiceImpl implements ClientCacheService {

	private static Logger log = Logger.getLogger(ClientCacheServiceImpl.class);

	private ClientCacheDAO clientCacheDAO;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(ClientCache clientCache) {
		try {
			clientCacheDAO.save(clientCache);
		} catch (Exception e) {
			log.error("saveClientCaches: exception when saving clientCache="+clientCache);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void saveClientCaches(List<ClientCache> clientCaches) {
		for (int i=0; i<clientCaches.size(); i++) {
			ClientCache clientCache = clientCaches.get(i);
			try {
				clientCacheDAO.save(clientCache);
			} catch (Exception e) {
				log.error("saveClientCaches: exception when saving clientCache="+clientCache);
			}
		}
	}

	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<ClientCache> getClientCaches(String loginId, String entityType) {
		return clientCacheDAO.getClientCaches(loginId, entityType);
	}
	
	// injection methods

	public void setClientCacheDAO(ClientCacheDAO clientCacheDAO) {
		this.clientCacheDAO = clientCacheDAO;
	}
}
