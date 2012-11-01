package com.hung.auction.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.dao.DomainUserDAO;
import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;

@Service("domainUserService")
public class DomainUserServiceImpl implements DomainUserService {

	private static Logger log = Logger.getLogger(DomainUserServiceImpl.class);
	
	@Autowired
	@Qualifier("domainUserDAO")	
	private DomainUserDAO domainUserDAO;
	
	@Autowired
	@Qualifier("domainDAO")	
	private DomainDAO domainDAO;
	
	public DomainUserServiceImpl() {
		log.info("DomainUserServiceImpl(): calling constructor");
	}
	
	// constructor called before call setXX injection
	public DomainUserServiceImpl(DomainDAO domainDAO, DomainUserDAO domainUserDAO) {
		log.info("DomainUserServiceImpl(domainDAO, domainUserDAO): calling constructor");
		setDomainDAO(domainDAO);
		setDomainUserDAO(domainUserDAO);
		populateAdminDomainUser();
	}
	
	// -- Suppose to be called after all setXX injection are called - start ----
	@PostConstruct
	public void postConstruct() {
		log.info("postConstruct: calling populateRootDomain()");
		populateAdminDomainUser();
	}
	
	public void initMethod() {
		log.info("initMethod: calling populateRootDomain()");
		populateAdminDomainUser();
	}
	// -- Suppose to be called after all setXX injection are called - end ----
	
	// -- destory -- start
	@PreDestroy
	public void preDestroy() {
		log.info("preDestroy: called");
	}
	
	public void destroyMethod() {
		log.info("destroyMethod: called");
	}
	// -- destory -- end
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(DomainUser domainUser) {
		log.info("save: domainUser="+domainUser);
		
		String domainName = domainUser.getUserDomain().getName();
		Domain domain = domainDAO.findByName(domainName);
		if (domain != null) {
			log.info("save: found domain="+domain+" by domainName="+domainName+" in domainUser="+domainUser);
		} else {
			log.info("save: not found domain="+domain+" by domainName="+domainName+" in domainUser="+domainUser);
		}
		domainUserDAO.save(domainUser);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public DomainUser findByLoginId(String loginId) {
		DomainUser domainUser = null;
        try {
            domainUser =  domainUserDAO.findByLoginId(loginId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return domainUser;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<DomainUser> findAll() {
		return domainUserDAO.findAll();
	}
	
	private void populateAdminDomainUser() {
		log.info("populateAdminDomainUser: enter");
		try {
			if (domainUserDAO.findByLoginId(DomainUser.ADMIN_NAME) == null) {
				Domain rootDomain = domainDAO.findByName(Domain.ROOT_NAME);
				if (rootDomain == null) {
					rootDomain = new Domain(Domain.ROOT_NAME);
					domainDAO.save(rootDomain);
				}
				domainUserDAO.save(new DomainUser(DomainUser.ADMIN_NAME, DomainUser.ADMIN_NAME, rootDomain));
			}
		} catch (Exception e) {}
		log.info("populateAdminDomainUser: exit");
	}
	
	// injection methods

	public void setDomainUserDAO(DomainUserDAO domainUserDAO) {
		log.info("setDomainUserDAO(domainUserDAO): called");
		this.domainUserDAO = domainUserDAO;
	}
	
	public void setDomainDAO(DomainDAO domainDAO) {
		log.info("setDomainDAO(domainDAO): called");
		this.domainDAO = domainDAO;
	}
	
}
