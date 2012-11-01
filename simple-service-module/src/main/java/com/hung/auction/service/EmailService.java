package com.hung.auction.service;

import com.hung.auction.domain.Bid;

public interface EmailService {
	
	public void notifySeller(Bid bid);
}
