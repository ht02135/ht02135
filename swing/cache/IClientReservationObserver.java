package com.hung.cache;

import java.util.List;

import com.hung.auction.client.ClientReservation;

public interface IClientReservationObserver {

	public void reserveUnReserve(List<ClientReservation> reservations, List<ClientReservation> unReservations);
	
}