package com.hung.adaptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

public class DomainServiceAdaptor implements IServiceAdaptor {

    private static Logger log = Logger.getLogger(DomainServiceAdaptor.class);

    private DomainService domainService = null;

    private static DomainServiceAdaptor instance = null;

    public static synchronized DomainServiceAdaptor getInstance() {
        if (instance == null) { instance = new DomainServiceAdaptor(); }
        return instance;
    }

    // Adaptor vs Proxy
    // Adaptor: it is used to adapt different interface.  it is more from client perspective.  adaptor expect
    //          interface to be of particular type and adaptor plays a role of filling that gap
    // Proxy: it is used to represent a standin object for a real object which is complex to create
    private DomainServiceAdaptor() {
        // domainService is an adaptee in Adaptor pattern
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        domainService = (DomainService) applicationContext.getBean("httpDomainServiceExporter");
    }

    public void save(Object object) {
        domainService.save((Domain) object);
    }

    public Object findById(String id) {
        return domainService.findByName(id);
    }

    public List<Object> findAll() {
        List<Object> objects = new ArrayList(domainService.findAll());
        log.info("findAll: objects="+objects);
        return objects;
    }
}