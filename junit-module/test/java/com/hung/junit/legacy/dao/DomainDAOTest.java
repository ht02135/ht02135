package com.hung.junit.legacy.dao;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.domain.Domain;

public class DomainDAOTest extends TestCase {

    private static Logger log = Logger.getLogger(DomainDAOTest.class);

    private ApplicationContext applicationContext = null;
    private DomainDAO domainDAO = null;
    
    public void testDummy() {}

    /*
    protected void setUp() {
        log.info("********** setUp: enter **********");
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-test-dao.xml");
        domainDAO = (DomainDAO) applicationContext.getBean("domainDAO");
        log.info("********** setUp: exit **********");
    }

    public void testDAO() {
        log.info("********** testDAO: enter **********");

        // --------------------------------------------------------------------
        // using Criteria

        log.info("findByUserNameCriteria: start >>>>>>>>>>");
        List<Domain> domains = domainDAO.findByUserNameCriteria("An Chih Tsai");
        log.info("testDAO: called findByUserNameCriteria, domains="+domains);
        log.info("findByUserNameCriteria: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        log.info("findByUserNameCriteriaFetch: start >>>>>>>>>>");
        domains = domainDAO.findByUserNameCriteriaFetch("An Chih Tsai");
        log.info("testDAO: called findByUserNameCriteriaFetch, domains="+domains);
        log.info("findByUserNameCriteriaFetch: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        log.info("findByUserNameHQL: start >>>>>>>>>>");
        domains = domainDAO.findByUserNameHQL("An Chih Tsai");
        log.info("testDAO: called findByUserNameHQL, domains="+domains);
        log.info("findByUserNameHQL: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        log.info("findByUserNameHQLFetch: start >>>>>>>>>>");
        domains = domainDAO.findByUserNameHQLFetch("An Chih Tsai");
        log.info("testDAO: called findByUserNameHQLFetch, domains="+domains);
        log.info("findByUserNameHQLFetch: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        // --------------------------------------------------------------------
        // using SubQuery

        log.info("findByUserNameSubQueryHQL: start >>>>>>>>>>");
        domains = domainDAO.findByUserNameSubQueryHQL("Hung Tsai");
        log.info("testDAO: called findByUserNameSubQueryHQL, domains="+domains);
        log.info("findByUserNameSubQueryHQL: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        log.info("findByUserNameSubQueryCriteria: start >>>>>>>>>>");
        domains = domainDAO.findByUserNameSubQueryCriteria("Hung Tsai");
        log.info("testDAO: called findByUserNameSubQueryCriteria, domains="+domains);
        log.info("findByUserNameSubQueryCriteria: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        // ------------------------------------------------------------------------
        // using scalar-type & entity-type result

        log.info("getMixedResult: start >>>>>>>>>>");
        List<Object[]> objects = domainDAO.getMixedResult("testDomain");
        log.info("testDAO: called getMixedResult, objects="+objects);

        for (int i=0;i<objects.size();i++) {
            Object[] object = objects.get(i);
            log.info("testDAO: called getMixedResult, object="+object);

            if (object[0] instanceof Domain) {
                Domain domain = (Domain) object[0];
                log.info("testDAO: called getMixedResult, domain="+domain);
            }
            if (object[1] instanceof String) {
                String domainName = (String) object[1];
                log.info("testDAO: called getMixedResult, domainName="+domainName);
            }
        }

        log.info("getMixedResult: end <<<<<<<<<<");
        log.info("");
        log.info("****************************************");

        log.info("********** testDAO: exit **********");
    }
    */
}
