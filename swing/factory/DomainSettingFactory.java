package com.hung.factory;

import org.apache.log4j.Logger;

import com.hung.adaptor.DomainSettingServiceAdaptor;


public class DomainSettingFactory {

    private static Logger log = Logger.getLogger(DomainSettingFactory.class);

    private static DomainSettingFactory instance = null;

    public static synchronized DomainSettingFactory getInstance() {
        if (instance == null) { instance = new DomainSettingFactory(); }
        return instance;
    }

    private DomainSettingFactory () {}

    public DomainSettingServiceAdaptor getServiceAdaptor() {
        return DomainSettingServiceAdaptor.getInstance();
    }

}