package com.hung.cache;

import java.util.List;

public interface ICache {
	public void put(Object object);
	public List<Object> getAll();
	public void addCacheObserver(ICacheObserver observer);
	public void fireCacheChanged();
}