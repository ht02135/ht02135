package com.hung.junit.unit.controller.restfulmvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.domain.Domain;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.auction.restful.springmvc.RestfulDomainController;
import com.hung.auction.service.DomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainRESTfulControllerMockTest {

    private static Logger log = Logger.getLogger(DomainRESTfulControllerMockTest.class);
    
    // mocks
    private DomainService domainServiceMock;
    
    private RestfulDomainController restfulDomainController; 
    
    // #######################################################################
    
    @Before
    public void setUp() {  
        // mock
        domainServiceMock = EasyMock.createNiceMock(DomainService.class);
        
        restfulDomainController = new RestfulDomainController(); 
        restfulDomainController.setDomainService(domainServiceMock);
    }
    
    // test spring-mvc controller as pojo
    @Test
    public void testGetDomains() {
        log.info("testGetDomains -  start");
        
        try {
            // expect
            EasyMock.expect(domainServiceMock.findAll(true)).andReturn(mockDomains());
            
            // replay
            EasyMock.replay(domainServiceMock);
            
            // run the method
            // ModelMap model = new ModelMap();
            List<JaxbDomain> actualDomains = restfulDomainController.getDomains();
            log.info("actualDomains="+actualDomains);
            
            // verify mock
            EasyMock.verify(domainServiceMock);
        } catch (Exception e) {
            log.info("e.getMessage()="+e.getMessage());
            e.printStackTrace();
        }
        
        log.info("testGetDomains -  end");
    }
    
    @Test
    public void testGetDomainByName() {
        log.info("testGetDomainByName -  start");
        
        try {
            // expect
            EasyMock.expect(domainServiceMock.findByName("root", true)).andReturn(mockRootDomain());
            
            // replay
            EasyMock.replay(domainServiceMock);
            
            // run the method
            // ModelMap model = new ModelMap();
            JaxbDomain actualRootDomain = restfulDomainController.getDomainByName("root");
            log.info("actualRootDomain="+actualRootDomain);
            
            // verify mock
            EasyMock.verify(domainServiceMock);
        } catch (Exception e) {
            log.info("e.getMessage()="+e.getMessage());
            e.printStackTrace();
        }
        
        log.info("testGetDomainByName -  end");
    }
    
    private Domain mockRootDomain() {
        Domain rootDomain = new Domain("root", "root", null);
        return rootDomain;
    }
    
    private List<Domain> mockDomains() {
        List<Domain> domains = new ArrayList<Domain>(1);
        domains.add(new Domain("root", "root", null));
        return domains;
    }
    
    private List<JaxbDomain> mockJaxbDomains() {
        List<JaxbDomain> jaxDomains = new ArrayList<JaxbDomain>(1);
        Iterator<Domain> iter = mockDomains().iterator();
        while (iter.hasNext()) {
            jaxDomains.add(new JaxbDomain(iter.next()));
        }
        return jaxDomains;
    }
}
