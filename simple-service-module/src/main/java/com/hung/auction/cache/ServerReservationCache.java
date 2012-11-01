package com.hung.auction.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservation;


public class ServerReservationCache {
    private static Logger log = Logger.getLogger(ServerReservationCache.class);

    private static ServerReservationCache instance = null;

    private Cache serverReservationCache = null;    // thread-safe

    public static synchronized ServerReservationCache getInstance() {
        if (instance == null) { instance = new ServerReservationCache(); }
        return instance;
    }

    private ServerReservationCache() {
        CacheManager.getInstance().addCache("serverReservationCache");
        serverReservationCache = CacheManager.getInstance().getCache("serverReservationCache");
        if (serverReservationCache == null) log.info("serverReservationCache is null");
    }

    // client might submit reservations request, but it doesnt mean it can get all of them
    public List<ClientReservation> synchReservations(List<ClientReservation> clientReservations) {
        log.info("synchUnReservations: clientReservations="+clientReservations);

        List<ClientReservation> serverReservations = new ArrayList();
        for (int i=0; i<clientReservations.size(); i++) {
            ClientReservation clientReservation = clientReservations.get(i);
            String key = clientReservation.getEntityType() + "/" + clientReservation.getEntityId();

            if (serverReservationCache.get(key) == null) {
                serverReservationCache.put(new Element(key, clientReservation.clone()));
                serverReservations.add(clientReservation.clone());
            }
        }
        return serverReservations;
    }

    // client should be able to unreserve without competition
    public List<ClientReservation> synchUnReservations(List<ClientReservation> clientUnReservations) {
        log.info("synchUnReservations: clientUnReservations="+clientUnReservations);

        List<ClientReservation> serverUnReservations = new ArrayList();
        for (int i=0; i<clientUnReservations.size(); i++) {
            ClientReservation clientUnReservation = clientUnReservations.get(i);
            String key = clientUnReservation.getEntityType() + "/" + clientUnReservation.getEntityId();

            // check if is in cache
            Element serverUnReservationElement = serverReservationCache.get(key);
            if (serverUnReservationElement != null) {
                ClientReservation serverUnReservation = ((ClientReservation) serverUnReservationElement.getObjectValue());
                // check if is reserved by you
                if (clientUnReservation.getLoginId().equals(serverUnReservation.getLoginId())) {
                    serverUnReservations.add(serverUnReservation.clone());
                    serverReservationCache.remove(key);
                }
            }
        }
        return serverUnReservations;
    }
}