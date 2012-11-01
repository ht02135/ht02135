package com.hung.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Seller;


public class SellerCache {
    private static Logger log = Logger.getLogger(SellerCache.class);

    private static SellerCache instance = null;

    private Cache sellerCache = null;   // thread-safe
    private List<SellerCacheEventListener> listeners = new ArrayList();

    public static synchronized SellerCache getInstance() {
        if (instance == null) { instance = new SellerCache(); }
        return instance;
    }

    private SellerCache() {
        CacheManager.getInstance().addCache("sellerCache");
        sellerCache = CacheManager.getInstance().getCache("sellerCache");
    }

    public void putSeller(Seller seller) {
        log.info("putSeller: seller="+seller);

        if (seller != null) sellerCache.put(new Element(seller.getId(), seller));
    }

    public List<Seller> getSellers() {
        List<Integer> keys = sellerCache.getKeys();
        List<Seller> sellers = new ArrayList(keys.size());
        for (int i=0; i<keys.size(); i++) {
            Element sellerElement = sellerCache.get(keys.get(i));
            sellers.add((Seller) sellerElement.getObjectValue());
        }
        return sellers;
    }

    public void addSellerCacheEventListener(SellerCacheEventListener listener) {
        log.info("addSellerCacheEventListener: listener="+listener);
        listeners.add(listener);
    }

    public void fireCacheChanged() {
        log.info("fireCacheChanged: enter");
        for (int i=0; i<listeners.size(); i++) {
            listeners.get(i).cacheChanged();
        }
    }
}