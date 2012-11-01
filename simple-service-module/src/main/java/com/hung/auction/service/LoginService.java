package com.hung.auction.service;

import com.hung.auction.client.ClientSession;
import com.hung.auction.domain.DomainUser;

public interface LoginService {
	
	public Boolean login(String loginId, String domainName);
	
	public Boolean logout();
	
	public ClientSession getClientSession();
}
