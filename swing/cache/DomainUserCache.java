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
import com.hung.auction.domain.DomainUser;
import com.hung.factory.FactoryMethods;


public class DomainUserCache extends AbstractCache {

    private static Logger log = Logger.getLogger(DomainUserCache.class);

    private Cache domainUserCache = null;   // thread-safe

    private static DomainUserCache instance = null;

    public static synchronized DomainUserCache getInstance() {
        if (instance == null) { instance = new DomainUserCache(); }
        return instance;
    }

    private DomainUserCache() {
        super();
        CacheManager.getInstance().addCache("domainUserCache");
        domainUserCache = CacheManager.getInstance().getCache("domainUserCache");
    }

    protected Cache getCache() {
        return domainUserCache;
    }

    public void put(Object object) {
        log.info("put: enter");

        DomainUser domainUser = (DomainUser) object;
        if (object != null) {
            getCache().put(new Element(domainUser.getLoginId(), domainUser));

            Date toDate = new Date();
            ClientSession clientSession = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession();
            ClientCacheId clientCacheId = new ClientCacheId(clientSession.getLoginId(), ClientCache.DOMAIN_USER_ENTITY, domainUser.getLoginId());
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
        List<ClientCache> clientCaches = FactoryMethods.getClientCacheFactory().getServiceAdaptor().getClientCaches(clientSession.getLoginId(), ClientCache.DOMAIN_USER_ENTITY);
        for (int i=0; i<clientCaches.size(); i++) {
            ClientCache clientCache = clientCaches.get(i);
            DomainUser domainUser = (DomainUser) FactoryMethods.getDomainUserFactory().getServiceAdaptor().findById(clientCache.getId().getEntityId());
            getCache().put(new Element(domainUser.getName(), domainUser));

            fireCacheChanged();
        }

        log.info("loggedIn: exit");
    }
}