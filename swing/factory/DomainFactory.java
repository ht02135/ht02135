package com.hung.factory;

import java.util.List;

import org.apache.log4j.Logger;

import com.hung.adaptor.DomainServiceAdaptor;
import com.hung.adaptor.IServiceAdaptor;
import com.hung.adaptor.JaxRSDomainServiceAdaptor;
import com.hung.adaptor.RestfulDomainServiceAdaptor;
import com.hung.blotter.AbstractBlotter;
import com.hung.blotter.DomainBlotter;
import com.hung.cache.DomainCache;
import com.hung.cache.ICache;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.dialog.DomainSearchDialog;
import com.hung.frame.AbstractFrame;
import com.hung.frame.DomainFrame;
import com.hung.picker.AbstractPicker;
import com.hung.picker.DomainPicker;


public class DomainFactory implements IFactory, IGUIFactory {

    private static Logger log = Logger.getLogger(DomainFactory.class);

    private static DomainFactory instance = null;

    public static synchronized DomainFactory getInstance() {
        if (instance == null) { instance = new DomainFactory(); }
        return instance;
    }

    private DomainFactory () {}

    @Override
    public ICache getCache() {
        return DomainCache.getInstance();
    }

    @Override
    public IServiceAdaptor getServiceAdaptor() {
        return DomainServiceAdaptor.getInstance();
    }

    public RestfulDomainServiceAdaptor getRestfulServiceAdaptor() {
        return RestfulDomainServiceAdaptor.getInstance();
    }

    public JaxRSDomainServiceAdaptor getJaxRSDomainServiceAdaptor() {
        return JaxRSDomainServiceAdaptor.getInstance();
    }

    @Override
    public AbstractPicker createPicker() {
        return new DomainPicker();
    }

    @Override
    public AbstractSearchDialog createSearchDialog(List<Object> objects) {
        log.info("createSearchDialog: objects="+objects);
        return new DomainSearchDialog(objects);
    }

    public AbstractBlotter createBlotter() {
        return new DomainBlotter();
    }

    @Override
    public AbstractFrame createFrame() {
        return new DomainFrame();
    }

}