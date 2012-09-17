package com.hung.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservation;
import com.hung.factory.FactoryMethods;


public class ClientReservationCache {
    private static Logger log = Logger.getLogger(ClientReservationCache.class);

    private Cache clientReservationCache = null;    // thread-safe
    private List<IClientReservationObserver> observers = new ArrayList();

    private static ClientReservationCache instance = null;

    public static synchronized ClientReservationCache getInstance() {
        if (instance == null) { instance = new ClientReservationCache(); }
        return instance;
    }

    private ClientReservationCache() {
        CacheManager.getInstance().addCache("domainSettingCache");
        clientReservationCache = CacheManager.getInstance().getCache("domainSettingCache");
        if (clientReservationCache == null) log.info("domainSettingCache is null");
    }

    // synch reservations from server
    public void synchReservations(List<ClientReservation> serverReservations) {
        log.info("synchReservations: serverReservations="+serverReservations);

        if (serverReservations.size() > 0) {
            List<ClientReservation> reservations = new ArrayList();
            for (int i=0; i<serverReservations.size(); i++) {
                ClientReservation serverReservation = serverReservations.get(i);
                String key = serverReservation.getEntityType() + "/" + serverReservation.getEntityId();

                if (clientReservationCache.get(key) == null) {
                    clientReservationCache.put(new Element(key, serverReservation.clone()));
                    reservations.add(serverReservation.clone());
                }
            }
            fireReserveUnReserve(reservations, Collections.EMPTY_LIST);
        }
    }

    // synch unReservations from server
    public void synchUnReservations(List<ClientReservation> serverUnReservations) {
        log.info("synchUnReservations: serverUnReservations="+serverUnReservations);

        if (serverUnReservations.size() > 0) {
            List<ClientReservation> unReservations = new ArrayList();
            for (int i=0; i<serverUnReservations.size(); i++) {
                ClientReservation serverUnReservation = serverUnReservations.get(i);
                String key = serverUnReservation.getEntityType() + "/" + serverUnReservation.getEntityId();

                if (clientReservationCache.get(key) != null) {
                    clientReservationCache.remove(key);
                    unReservations.add(serverUnReservation.clone());
                }
            }

            fireReserveUnReserve(Collections.EMPTY_LIST, unReservations);
        }
    }

    // handle reserve/unReserve request from client
    public void reserveUnReserve(List<ClientReservation> clientReservations, List<ClientReservation> clientUnReservations) {
        log.info("reserveUnReserve: clientReservations="+clientReservations+", clientUnReservations="+clientUnReservations);

        if ((clientReservations.size() > 0) || (clientUnReservations.size() > 0)) {
            // FactoryMethods.getServerReservationFactory().getServiceAdaptor().reserveUnReserve(clientReservations, clientUnReservations);
            FactoryMethods.getServerReservationFactory().getServiceAdaptor().publishReserveUnReserve(clientReservations, clientUnReservations);
        }
    }

    // return all cached client-reservation in cache
    public List<ClientReservation> getAllCachedClientReservations() {
        List<String> keys = clientReservationCache.getKeys();
        List<ClientReservation> clientReservations = new ArrayList(keys.size());

        Element clientReservationElement = null;
        for (int i=0; i<keys.size(); i++) {
            clientReservationElement = clientReservationCache.get(keys.get(i));
            clientReservations.add((ClientReservation) clientReservationElement.getObjectValue());
        }
        return clientReservations;
    }

    public ClientReservation getCachedClientReservation(String entityType, String entityId) {
        log.info("getCachedClientReservation: entityType="+entityType+", entityId="+entityId);

        ClientReservation clientReservation = null;
        String key = entityType + "/" + entityId;
        if (clientReservationCache.get(key) != null) {
            clientReservation = (ClientReservation) clientReservationCache.get(key).getObjectValue();
        }
        log.info("getCachedClientReservation: key="+key+", clientReservation="+clientReservation);
        return clientReservation;
    }

    public synchronized void addCacheObserver(IClientReservationObserver observer) {
        observers.add(observer);
    }

    // notify observers about reservations/unReservations
    public synchronized void fireReserveUnReserve(List<ClientReservation> reservations, List<ClientReservation> unReservations) {
        log.info("fireReserveUnReserve: reservations="+reservations+", unReservations="+unReservations);
        for (int i=0; i<observers.size(); i++) {
            observers.get(i).reserveUnReserve(reservations, unReservations);
        }
    }
}