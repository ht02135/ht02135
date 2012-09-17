package com.hung.factory;

import org.apache.log4j.Logger;

import com.hung.cache.ClientReservationCache;


public class ClientReservationFactory {

    private static Logger log = Logger.getLogger(ClientReservationFactory.class);

    private static ClientReservationFactory instance = null;

    public static synchronized ClientReservationFactory getInstance() {
        if (instance == null) { instance = new ClientReservationFactory(); }
        return instance;
    }

    private ClientReservationFactory() {}

    public ClientReservationCache getCache() {
        return ClientReservationCache.getInstance();
    }

}