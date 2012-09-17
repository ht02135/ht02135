package com.hung.adaptor;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.ClientCache;
import com.hung.auction.service.ClientCacheService;

public class ClientCacheServiceAdaptor {

    private static Logger log = Logger.getLogger(DomainUserServiceAdaptor.class);

    private ClientCacheService clientCacheService = null;

    private static ClientCacheServiceAdaptor instance = null;

    public static synchronized ClientCacheServiceAdaptor getInstance() {
        if (instance == null) { instance = new ClientCacheServiceAdaptor(); }
        return instance;
    }

    private ClientCacheServiceAdaptor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        clientCacheService = (ClientCacheService) applicationContext.getBean("httpClientCacheServiceExporter");
    }

    public void save(ClientCache clientCache) {
        clientCacheService.save(clientCache);
    }

    public void saveClientCaches(List<ClientCache> clientCaches) {
        clientCacheService.saveClientCaches(clientCaches);
    }

    public List<ClientCache> getClientCaches(String loginId, String entityType) {
        return clientCacheService.getClientCaches(loginId, entityType);
    }
}