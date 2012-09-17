package com.hung.adaptor;

import java.util.List;

public interface IServiceAdaptor {
	public void save(Object object);
	public Object findById(String id);
	public List<Object> findAll();
}