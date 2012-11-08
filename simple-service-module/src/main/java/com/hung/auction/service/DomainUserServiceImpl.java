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
	
	public DomainUserServiceImpl() {}
	
	// constructor called before call setXX injection
	public DomainUserServiceImpl(DomainDAO domainDAO, DomainUserDAO domainUserDAO) {
		setDomainDAO(domainDAO);
		setDomainUserDAO(domainUserDAO);
		populateAdminDomainUser();
	}
	
	// -- Suppose to be called after all setXX injection are called - start ----
	@PostConstruct
	public void postConstruct() {
		populateAdminDomainUser();
	}
	
	public void initMethod() {
		populateAdminDomainUser();
	}
	// -- Suppose to be called after all setXX injection are called - end ----
	
	// -- destory -- start
	@PreDestroy
	public void preDestroy() {}
	
	public void destroyMethod() {}
	// -- destory -- end
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(DomainUser domainUser) {
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
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteByLoginId(String loginId) {
		domainUserDAO.deleteByLoginId(loginId);
	}
	
	private void populateAdminDomainUser() {
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
	}
	
	// injection methods

	public void setDomainUserDAO(DomainUserDAO domainUserDAO) {
		this.domainUserDAO = domainUserDAO;
	}
	
	public void setDomainDAO(DomainDAO domainDAO) {
		this.domainDAO = domainDAO;
	}
	
}
