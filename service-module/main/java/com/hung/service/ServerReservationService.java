package com.hung.auction.service;

import java.util.List;

import com.hung.auction.client.ClientReservation;
import com.hung.auction.domain.Bid;

public interface ServerReservationService {

	public void reserveUnReserve(List<ClientReservation> clientReservations, List<ClientReservation> clientUnReservations);
}
