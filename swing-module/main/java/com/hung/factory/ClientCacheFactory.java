package com.hung.factory;

import org.apache.log4j.Logger;

import com.hung.adaptor.ClientCacheServiceAdaptor;


public class ClientCacheFactory {

    private static Logger log = Logger.getLogger(ClientCacheFactory.class);

    private static ClientCacheFactory instance = null;

    public static synchronized ClientCacheFactory getInstance() {
        if (instance == null) { instance = new ClientCacheFactory(); }
        return instance;
    }

    private ClientCacheFactory() {}

    public ClientCacheServiceAdaptor getServiceAdaptor() {
        return ClientCacheServiceAdaptor.getInstance();
    }

}