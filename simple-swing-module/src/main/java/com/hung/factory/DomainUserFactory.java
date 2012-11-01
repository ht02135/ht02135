package com.hung.factory;

import java.util.List;

import org.apache.log4j.Logger;

import com.hung.adaptor.DomainUserServiceAdaptor;
import com.hung.adaptor.IServiceAdaptor;
import com.hung.adaptor.RestfulDomainServiceAdaptor;
import com.hung.blotter.AbstractBlotter;
import com.hung.blotter.DomainUserBlotter;
import com.hung.cache.DomainUserCache;
import com.hung.cache.ICache;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.dialog.DomainUserSearchDialog;
import com.hung.frame.AbstractFrame;
import com.hung.frame.DomainUserFrame;
import com.hung.picker.AbstractPicker;
import com.hung.picker.DomainUserPicker;


public class DomainUserFactory implements IFactory, IGUIFactory {

    private static Logger log = Logger.getLogger(DomainUserFactory.class);

    private static DomainUserFactory instance = null;

    public static synchronized DomainUserFactory getInstance() {
        if (instance == null) { instance = new DomainUserFactory(); }
        return instance;
    }

    private DomainUserFactory() {}

    @Override
    public ICache getCache() {
        return DomainUserCache.getInstance();
    }

    @Override
    public IServiceAdaptor getServiceAdaptor() {
        return DomainUserServiceAdaptor.getInstance();
    }

    @Override
    public AbstractPicker createPicker() {
        return new DomainUserPicker();
    }

    @Override
    public AbstractSearchDialog createSearchDialog(List<Object> objects) {
        log.info("createSearchDialog: objects="+objects);
        return new DomainUserSearchDialog(objects);
    }

    public AbstractBlotter createBlotter() {
        return new DomainUserBlotter();
    }

    @Override
    public AbstractFrame createFrame() {
        return new DomainUserFrame();
    }

}