package com.hung.cache;

import java.util.Date;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientSession;
import com.hung.auction.domain.ClientCache;
import com.hung.auction.domain.ClientCacheId;
import com.hung.auction.domain.Domain;
import com.hung.factory.FactoryMethods;

public class DomainCache extends AbstractCache {

    private static Logger log = Logger.getLogger(DomainCache.class);

    private Cache domainCache = null;   // thread-safe

    private static DomainCache instance = null;

    public static synchronized DomainCache getInstance() {
        if (instance == null) { instance = new DomainCache(); }
        return instance;
    }

    private DomainCache() {
        super();
        CacheManager.getInstance().addCache("domainCache");
        domainCache = CacheManager.getInstance().getCache("domainCache");
    }

    protected Cache getCache() {
        return domainCache;
    }

    public void put(Object object) {
        log.info("put: enter");

        Domain domain = (Domain) object;
        if (object != null) {
            getCache().put(new Element(domain.getName(), domain));

            Date toDate = new Date();
            ClientSession clientSession = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession();
            ClientCacheId clientCacheId = new ClientCacheId(clientSession.getLoginId(), ClientCache.DOMAIN_ENTITY, domain.getName());
            ClientCache clientCache = new ClientCache(clientCacheId, toDate, toDate);
            log.info("put: clientSession="+clientSession+", clientCacheId="+clientCacheId+", clientCache="+clientCache);
            FactoryMethods.getClientCacheFactory().getServiceAdaptor().save(clientCache);

            fireCacheChanged();
        }

        log.info("put: exit");
    }

    public void loggedIn() {
        log.info("loggedIn: enter");

        ClientSession clientSession = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession();
        List<ClientCache> clientCaches = FactoryMethods.getClientCacheFactory().getServiceAdaptor().getClientCaches(clientSession.getLoginId(), ClientCache.DOMAIN_ENTITY);
        for (int i=0; i<clientCaches.size(); i++) {
            ClientCache clientCache = clientCaches.get(i);
            Domain domain = (Domain) FactoryMethods.getDomainFactory().getServiceAdaptor().findById(clientCache.getId().getEntityId());
            getCache().put(new Element(domain.getName(), domain));

            fireCacheChanged();
        }

        log.info("loggedIn: exit");
    }

}