package com.hung.restful.springmvc;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.hung.auction.domain.Domain;
import com.hung.auction.restful.springmvc.RestfulDomainController;
import com.hung.auction.service.DomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainControllerMockitoTest {
    
    // http://stackoverflow.com/questions/861089/testing-spring-mvc-annotation-mapppings

    private static Logger log = Logger.getLogger(DomainControllerMockitoTest.class);
    
    private RestfulDomainController restfulDomainController = null; 
    
    @MockitoAnnotations.Mock
    private DomainService domainServiceMock;
    
    private MockHttpServletRequest requestMock;
    private MockHttpServletResponse responseMock;
    
    @Autowired
    @Qualifier("annotationMethodHandlerAdapter")
    private AnnotationMethodHandlerAdapter adapter;
    
    @Before
    public void setUp() {  
        MockitoAnnotations.initMocks(this);
        requestMock = new MockHttpServletRequest();
        responseMock = new MockHttpServletResponse();
        
        // no need to setup adapter, it will be wired in

        restfulDomainController = new RestfulDomainController(); 
        restfulDomainController.setDomainService(domainServiceMock);
    }
    
    @Test
    public void testGetDomains() {
        log.info("testGetDomains -  start");

        // request specific setup
        requestMock.setRequestURI("/restfuldomaincontroller");
        requestMock.setMethod("GET");
        // requestMock.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML.toString());
        requestMock.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        requestMock.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true); 
        
        try {
            // expect
            Mockito.when(domainServiceMock.findAll(true)).thenReturn(mockDomains());
            
            // run the method
            ModelAndView mav = adapter.handle(requestMock, responseMock, restfulDomainController);
            
            // debug
            log.info("responseMock.getContentAsString()="+responseMock.getContentAsString());
            if (mav != null) {
                log.info("mav.getViewName()="+mav.getViewName());
                log.info("mav.getModel()="+mav.getModel());
            }
            
            // verify
            Mockito.verify(domainServiceMock).findAll(true);
        } catch (Exception e) {
            log.info("e.getMessage()="+e.getMessage());
            e.printStackTrace();
        }
        
        log.info("testGetDomains -  end");
    }
    
    private List<Domain> mockDomains() {
        log.info("mockDomains -  enter");
        List<Domain> domains = new ArrayList<Domain>(1);
        domains.add(new Domain("root", "root", null));
        log.info("mockDomains -  domains="+domains);
        return domains;
    }
}
