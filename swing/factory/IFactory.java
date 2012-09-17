package com.hung.factory;

import java.util.List;

import com.hung.adaptor.IServiceAdaptor;
import com.hung.cache.ICache;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.frame.AbstractFrame;
import com.hung.picker.AbstractPicker;


public interface IFactory {
	public ICache getCache();
	public IServiceAdaptor getServiceAdaptor();
}