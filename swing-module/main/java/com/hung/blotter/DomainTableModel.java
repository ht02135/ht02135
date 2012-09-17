package com.hung.blotter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.hung.adaptor.LoginServiceAdaptor;
import com.hung.auction.client.ClientReservation;
import com.hung.auction.domain.Domain;
import com.hung.cache.ClientReservationCache;
import com.hung.cache.IClientReservationObserver;
import com.hung.factory.FactoryMethods;

public class DomainTableModel extends AbstractTableModel implements IClientReservationObserver {

	private static Logger log = Logger.getLogger(DomainBlotter.class);
	
	public static final int RESERVATION_COL_IDX = 0; 
	public static final int DOMAIN_COL_IDX = 1; 
	
	public static final String RESERVATION_HEADER = "Reservation"; 
	public static final String DOMAIN_HEADER = "Domain"; 
	
	List<Object> domains = null;
	ClientReservationCache clientReservationCache = null;
	LoginServiceAdaptor loginService = null;
	
	public DomainTableModel() {
		loginService = FactoryMethods.getLoginFactory().getServiceAdaptor();
		domains = new ArrayList(FactoryMethods.getDomainFactory().getServiceAdaptor().findAll());
		clientReservationCache = FactoryMethods.getClientReservationFactory().getCache();
		clientReservationCache.addCacheObserver(this);
	}

	public void reserveUnReserve(List<ClientReservation> reservations, List<ClientReservation> unReservations) {
		log.info("*** reserveUnReserve: reservations="+reservations+", unReservations="+unReservations+" ***");
		
		// dont need to do much other than notify view that table data change (well client reservation cache changed)
		fireTableDataChanged();	// there are other firexx(), but using fireTableDataChanged() should be ok
	}

	public int getRowCount() {
		return domains.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		log.info("getValueAt: rowIndex="+rowIndex+", columnIndex="+columnIndex);
		
		if (columnIndex > 2) return null;
		if (rowIndex > domains.size()) return null;
		
		if (columnIndex == RESERVATION_COL_IDX) {	// deal with reservation column
			log.info("getValueAt: deal with reservation column");
			String reservedBy = "";
			Domain domain = (Domain) domains.get(rowIndex);
			ClientReservation clientReservation = clientReservationCache.getCachedClientReservation(ClientReservation.DOMAIN_ENTITY, domain.getName());
			if (clientReservation != null) {
				log.info("getValueAt: clientReservation="+clientReservation);
				
				if (clientReservation.getLoginId().equals(loginService.getClientSession().getLoginId())) {
					reservedBy = "reserved";
				} else {
					reservedBy = "reserved by other";
				}
			} else {
				log.info("getValueAt: clientReservation is null");
			}
			return reservedBy;
		} else if (columnIndex == DOMAIN_COL_IDX) {	// deal with domain column
			log.info("getValueAt: deal with domain column");
			Domain domain = (Domain) domains.get(rowIndex);
			log.info("getValueAt: domain="+domain);
			return domain;
		} else {
			// shouldnt happen
			return null;
		}
	}
} 
