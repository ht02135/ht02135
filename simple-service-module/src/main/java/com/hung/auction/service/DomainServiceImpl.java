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
import com.hung.auction.domain.Domain;

@Service("domainService")
public class DomainServiceImpl implements DomainService {

    private static Logger log = Logger.getLogger(DomainServiceImpl.class);

    @Autowired
    @Qualifier("domainDAO")
    private DomainDAO domainDAO;

    public DomainServiceImpl() {}

    // constructor called before call setXX injection
    public DomainServiceImpl(DomainDAO domainDAO) {
        setDomainDAO(domainDAO);
        populateRootDomain();
    }

    // -- called after all setXX injection are called - start ----
    @PostConstruct
    public void postConstruct() {
        populateRootDomain();
    }

    public void initMethod() {
        populateRootDomain();
    }
    // -- Suppose to be called after all setXX injection are called - end ----

    // -- destory -- start
    @PreDestroy
    public void preDestroy() {}

    public void destroyMethod() {}
    // -- destory -- end

    @Transactional(propagation=Propagation.REQUIRED)
    public void save(Domain domain) {
        if (domain.getParentDomain() == null) {
            domain.setParentDomain(domainDAO.findByName(Domain.ROOT_NAME));
        }
        domainDAO.save(domain);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Domain findByName(String name) {
        Domain domain = null;
        try {
            domain = domainDAO.findByName(name);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return domain;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Domain findByName(String name, boolean enableEagerFetch) {
        Domain domain = null;
        try {
            domain = domainDAO.findByName(name, enableEagerFetch);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return domain;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<String> getNamesByPatternStoreProcedure(String pattern) {
        log.info("getNamesByPatternStoreProcedure: pattern="+pattern);
        List<String> domainNames = domainDAO.getNamesByPatternStoreProcedure(pattern);
        log.info("getNamesByPatternStoreProcedure: domainNames="+domainNames);
        return domainNames;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<String> getNamesByPatternSQL(String pattern) {
        log.info("getNamesByPatternSQL: pattern="+pattern);
        List<String> domainNames = domainDAO.getNamesByPatternSQL(pattern);
        log.info("getNamesByPatternSQL: domainNames="+domainNames);
        return domainNames;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<String> getParentNamesByPatternSQL(String pattern) {
        log.info("getParentNamesByPatternSQL: pattern="+pattern);
        List<String> domainNames = domainDAO.getParentNamesByPatternSQL(pattern);
        log.info("getParentNamesByPatternSQL: domainNames="+domainNames);
        return domainNames;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<Domain> getDomainsByPatternStoreProcedure(String pattern) {
        log.info("getDomainsByPatternStoreProcedure: pattern="+pattern);
        List<Domain> domains = domainDAO.getDomainsByPatternStoreProcedure(pattern);
        log.info("getDomainsByPatternStoreProcedure: domains="+domains);
        return domains;
    }

    public List<Domain> getDomainsByPatternSQL(String pattern) {
        log.info("getDomainsByPatternSQL: pattern="+pattern);
        List<Domain> domains = domainDAO.getDomainsByPatternSQL(pattern);
        log.info("getDomainsByPatternSQL: domains="+domains);
        return domains;
    }

    public List<Domain> getDomainsByFieldsPatternSQL(String pattern) {
        log.info("getDomainsByFieldsPatternSQL: pattern="+pattern);
        List<Domain> domains = domainDAO.getDomainsByFieldsPatternSQL(pattern);
        log.info("getDomainsByFieldsPatternSQL: domains="+domains);
        return domains;
    }

    public List<Domain> getDomainsByPatternHQL(String pattern) {
        log.info("getDomainsByPatternHQL: pattern="+pattern);
        List<Domain> domains = domainDAO.getDomainsByPatternHQL(pattern);
        log.info("getDomainsByPatternHQL: domains="+domains);
        return domains;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<Domain> findAll() {
        return domainDAO.findAll();
    }

    public List<Domain> findAll(boolean enableEagerFetch) {
        return domainDAO.findAll(enableEagerFetch);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteByName(String name) {
        domainDAO.deleteByName(name);
    }

    private void populateRootDomain() {
        try {
            Domain rootDomain = domainDAO.findByName(Domain.ROOT_NAME);
            if (rootDomain == null) {
                rootDomain = new Domain(Domain.ROOT_NAME);
                domainDAO.save(rootDomain);
            }

            Domain subRootDomain = domainDAO.findByName(Domain.SUBROOT_NAME);
            if (subRootDomain == null) {
                subRootDomain = new Domain(Domain.SUBROOT_NAME, rootDomain);
                domainDAO.save(subRootDomain);
            }
        } catch (Exception e) {}
    }

    // injection methods

    public void setDomainDAO(DomainDAO domainDAO) {
        this.domainDAO = domainDAO;
    }

}
