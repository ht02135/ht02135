package com.hung.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservation;
import com.hung.auction.domain.Domain;
import com.hung.cache.IClientReservationObserver;
import com.hung.factory.FactoryMethods;

public class TestDomainReservationMain extends AbstractMain implements IClientReservationObserver {

	private static Logger log = Logger.getLogger(TestDomainReservationMain.class);
	
	public TestDomainReservationMain() {
		FactoryMethods.getClientReservationFactory().getCache().addCacheObserver(this);
	}
    
	public void loggedIn() {
		String loginId = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession().getLoginId();
		String entityType = ClientReservation.DOMAIN_ENTITY;
		
		// reserve root
		List<ClientReservation> clientReservations = new ArrayList(1);
		clientReservations.add(new ClientReservation(loginId, entityType, Domain.ROOT_NAME));
		FactoryMethods.getClientReservationFactory().getCache().reserveUnReserve(clientReservations, Collections.EMPTY_LIST);
		
		// reserve root + testDomain
		clientReservations = new ArrayList(1);
		clientReservations.add(new ClientReservation(loginId, entityType, Domain.ROOT_NAME));
		clientReservations.add(new ClientReservation(loginId, entityType, "testDomain"));
		FactoryMethods.getClientReservationFactory().getCache().reserveUnReserve(clientReservations, Collections.EMPTY_LIST);
		
		// unreserve testDomain + testDomain3
		List<ClientReservation> clientUnReservations = new ArrayList(2);
		clientUnReservations.add(new ClientReservation(loginId, entityType, "testDomain"));
		clientUnReservations.add(new ClientReservation(loginId, entityType, "testDomain3"));
		FactoryMethods.getClientReservationFactory().getCache().reserveUnReserve(Collections.EMPTY_LIST, clientUnReservations);
	}

	public void reserveUnReserve(List<ClientReservation> reservations, List<ClientReservation> unReservations) {
		log.info("reserveUnReserve: ********** Update UI - enter **********");
		
		List<ClientReservation> allCachedClientReservations = FactoryMethods.getClientReservationFactory().getCache().getAllCachedClientReservations();
		log.info("reserveUnReserve: reservations="+reservations+", unReservations="+unReservations+", allCachedClientReservations="+allCachedClientReservations);
		
		log.info("reserveUnReserve: ********** Update UI - exit **********");
	}

    public static void main( String[] args )
    {
    	TestDomainReservationMain testDomainReservationMain = new TestDomainReservationMain();
    	testDomainReservationMain.login();
    }
}