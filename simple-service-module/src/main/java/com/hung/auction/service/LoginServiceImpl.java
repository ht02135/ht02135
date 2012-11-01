package com.hung.auction.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.client.ClientSession;
import com.hung.auction.domain.DomainUser;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger log = Logger.getLogger(LoginServiceImpl.class);
	
	private ClientSession clientSession;
	private DomainUserService domainUserService;
	
	public LoginServiceImpl() {
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Boolean login(String loginId, String domainName) {
		log.info("login: loginId="+loginId);
		
		DomainUser domainUser = domainUserService.findByLoginId(loginId);
		if (domainUser != null) {
			clientSession.setDomainName(domainUser.getUserDomain().getName());
			clientSession.setLoginId(domainUser.getLoginId());
			log.info("login: successfully loggedIn, clientSession.getLoginId()="+clientSession.getLoginId());
			return Boolean.TRUE;
		} else {
			log.info("login: failed to login");
			return Boolean.FALSE;
		}
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Boolean logout() {
		clientSession.setDomainName("");
		clientSession.setLoginId("");
		return Boolean.TRUE;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public ClientSession getClientSession() {
		return clientSession.clone();
	}
	
	// injection methods
	
	public void setClientSession(ClientSession clientSession) {
		this.clientSession = clientSession;
	}
	
	public void setDomainUserService(DomainUserService domainUserService) {
		this.domainUserService = domainUserService;
	}
}
