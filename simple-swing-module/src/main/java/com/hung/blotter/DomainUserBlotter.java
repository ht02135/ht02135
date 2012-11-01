package com.hung.blotter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservation;
import com.hung.cache.IClientReservationObserver;

public class DomainUserBlotter extends AbstractBlotter implements ActionListener {

	private static Logger log = Logger.getLogger(DomainUserBlotter.class);
	
	public DomainUserBlotter() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {

	}
	
	protected void setupUI() {

	}

	public void actionPerformed(ActionEvent e) {
	}
} 
