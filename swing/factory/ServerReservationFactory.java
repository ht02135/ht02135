package com.hung.factory;

import org.apache.log4j.Logger;

import com.hung.adaptor.ServerReservationServiceAdaptor;


public class ServerReservationFactory {

    private static Logger log = Logger.getLogger(ServerReservationFactory.class);

    private static ServerReservationFactory instance = null;

    public static synchronized ServerReservationFactory getInstance() {
        if (instance == null) { instance = new ServerReservationFactory(); }
        return instance;
    }

    private ServerReservationFactory() {}

    public ServerReservationServiceAdaptor getServiceAdaptor() {
        return ServerReservationServiceAdaptor.getInstance();
    }

}