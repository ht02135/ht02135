package com.hung.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.hung.adaptor.ILoginObserver;
import com.hung.factory.FactoryMethods;

public abstract class AbstractCache implements ICache, ILoginObserver {

    private List<ICacheObserver> observers = new ArrayList();

    public AbstractCache() {
        FactoryMethods.getLoginFactory().getServiceAdaptor().addLoginObserver(this);
    }

    protected Cache getCache() {
        return null;
    }

    public List<Object> getAll() {
        List<String> keys = getCache().getKeys();
        List<Object> domains = new ArrayList(keys.size());
        for (int i=0; i<keys.size(); i++) {
            Element domainElement = getCache().get(keys.get(i));
            domains.add(domainElement.getObjectValue());
        }
        return domains;
    }

    public synchronized void addCacheObserver(ICacheObserver observer) {
        observers.add(observer);
    }

    public synchronized void fireCacheChanged() {
        for (int i=0; i<observers.size(); i++) {
            observers.get(i).cacheChanged();
        }
    }
}