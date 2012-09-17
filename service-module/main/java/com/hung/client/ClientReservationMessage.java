package com.hung.auction.client;

import java.io.Serializable;
import java.util.List;

public class ClientReservationMessage implements Serializable {

	private List<ClientReservation> unReservations;
	private List<ClientReservation> reservations;
	
	public ClientReservationMessage() {
	}

	public ClientReservationMessage(List<ClientReservation> reservations, List<ClientReservation> unReservations) {
		setUnReservations(unReservations);
		setReservations(reservations);
	}
	
	public List<ClientReservation> getUnReservations() {
		return unReservations;
	}
	public void setUnReservations(List<ClientReservation> unReservations) {
		this.unReservations = unReservations;
	}
	
	public List<ClientReservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<ClientReservation> reservations) {
		this.reservations = reservations;
	}
	
	public String toString() {
		return "[unReservations="+unReservations+", reservations="+reservations+"]";
	}
}

