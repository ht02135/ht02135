package com.hung.adaptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.DomainUser;
import com.hung.auction.service.DomainUserService;

public class DomainUserServiceAdaptor implements IServiceAdaptor {

    private static Logger log = Logger.getLogger(DomainUserServiceAdaptor.class);

    private DomainUserService domainUserService = null;

    private static DomainUserServiceAdaptor instance = null;

    public static synchronized DomainUserServiceAdaptor getInstance() {
        if (instance == null) { instance = new DomainUserServiceAdaptor(); }
        return instance;
    }

    private DomainUserServiceAdaptor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        domainUserService = (DomainUserService) applicationContext.getBean("httpDomainUserServiceExporter");
    }

    public void save(Object object) {
        domainUserService.save((DomainUser) object);
    }

    public Object findById(String id) {
        return domainUserService.findByLoginId(id);
    }

    public List<Object> findAll() {
        List<Object> objects = new ArrayList(domainUserService.findAll());
        log.info("findAll: objects="+objects);
        return objects;
    }
}