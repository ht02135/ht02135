package com.hung.junit.legacy.dao2;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.dao2.QueryDomainDAO;
import com.hung.auction.domain.Domain;

public class QueryDomainDAOTest extends TestCase {

    private static Logger log = Logger.getLogger(QueryDomainDAOTest.class);

    private ApplicationContext applicationContext = null;
    private QueryDomainDAO sqlQueryDomainDAO = null;
    private QueryDomainDAO hqlQueryDomainDAO = null;
    private QueryDomainDAO criteriaQueryDomainDAO = null;
    
    public void testDummy() {}

    /*
    protected void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-test-dao.xml");
        sqlQueryDomainDAO = (QueryDomainDAO) applicationContext.getBean("sqlQueryDomainDAO");
        hqlQueryDomainDAO = (QueryDomainDAO) applicationContext.getBean("hqlQueryDomainDAO");
        criteriaQueryDomainDAO = (QueryDomainDAO) applicationContext.getBean("criteriaQueryDomainDAO");
    }

    public void testSQLQueryDomainDAO() {
        log.info("testSQLQueryDomainDAO: enter ");

        log.info("############ SQL find Domain by user name ###########################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Domain> domains = sqlQueryDomainDAO.findDomainsByUserName("admin", true);
        log.info("testSQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ SQL find Domain by user name (SUB-QUERY) ###############");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = sqlQueryDomainDAO.findDomainsByUserName("admin");
        log.info("testSQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ SQL find domains #######################################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = sqlQueryDomainDAO.findDomains(true);
        log.info("testSQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### SQL [domainName, userCount] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object[]> objectList = sqlQueryDomainDAO.findDomainNameUserCount();
        Iterator iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            BigDecimal userCount = (BigDecimal) objectArray[1];
            log.info("[domainName="+domainName+", userCount="+userCount+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### SQL [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = sqlQueryDomainDAO.findDomainUserNamePair(false);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### SQL FULL [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = sqlQueryDomainDAO.findDomainUserNamePair(true);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### SQL findDomainsWithParentContainUserName (self join via inner join) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = sqlQueryDomainDAO.findDomainsWithParentContainUserName("admin", false);
        log.info("testSQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### SQL findDomainsWithParentContainUserName (just subquery) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = sqlQueryDomainDAO.findDomainsWithParentContainUserName("admin", true);
        log.info("testSQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("testSQLQueryDomainDAO: exit ");
    }

    public void testHQLQueryDomainDAO() {
        log.info("testHQLQueryDomainDAO: enter ");

        log.info("############ HQL find Domain by user name ###########################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Domain> domains = hqlQueryDomainDAO.findDomainsByUserName("admin", true);
        log.info("testHQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ HQL find Domain by user name (SUB-QUERY) ###############");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = hqlQueryDomainDAO.findDomainsByUserName("admin");
        log.info("testHQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ HQL find domains #######################################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        hqlQueryDomainDAO.findDomains(true);
        log.info("testHQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### HQL [domainName, userCount] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object[]> objectList = hqlQueryDomainDAO.findDomainNameUserCount();
        Iterator iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            Integer userCount = (Integer) objectArray[1];
            log.info("[domainName="+domainName+", userCount="+userCount+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### HQL [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = hqlQueryDomainDAO.findDomainUserNamePair(false);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### HQL FULL [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = hqlQueryDomainDAO.findDomainUserNamePair(true);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### HQL findDomainsWithParentContainUserName (self join via inner join) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = hqlQueryDomainDAO.findDomainsWithParentContainUserName("admin", false);
        log.info("testHQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### HQL findDomainsWithParentContainUserName (just subquery) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = hqlQueryDomainDAO.findDomainsWithParentContainUserName("admin", true);
        log.info("testHQLQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("testHQLQueryDomainDAO: exit ");
    }

    public void testCriteriaQueryDomainDAO() {
        log.info("testCriteriaQueryDomainDAO: enter ");

        log.info("############ Criteria find Domain by user name ###########################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Domain> domains = criteriaQueryDomainDAO.findDomainsByUserName("admin", true);
        log.info("testCriteriaQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ Criteria find Domain by user name (SUB-QUERY) ##########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = criteriaQueryDomainDAO.findDomainsByUserName("admin");
        log.info("testCriteriaQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("############ Criteria find domains #######################################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        criteriaQueryDomainDAO.findDomains(true);
        log.info("testCriteriaQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### Criteria [domainName, userCount] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        List<Object[]> objectList = criteriaQueryDomainDAO.findDomainNameUserCount();
        Iterator iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            Integer userCount = (Integer) objectArray[1];
            log.info("[domainName="+domainName+", userCount="+userCount+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### Criteria [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = criteriaQueryDomainDAO.findDomainUserNamePair(false);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("########### Criteria FULL [domainName, userName] #############################");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        objectList = criteriaQueryDomainDAO.findDomainUserNamePair(true);
        iter = objectList.iterator();
        while (iter.hasNext()) {
            Object[] objectArray = (Object[]) iter.next();
            String domainName = (String) objectArray[0];
            String userName = (String) objectArray[1];
            log.info("[domainName="+domainName+", userName="+userName+"]");
        }
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### Criteria findDomainsWithParentContainUserName (self join via inner join) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = criteriaQueryDomainDAO.findDomainsWithParentContainUserName("admin", false);
        log.info("testCriteriaQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("######### Criteria findDomainsWithParentContainUserName (just subquery) ########");

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        domains = criteriaQueryDomainDAO.findDomainsWithParentContainUserName("admin", true);
        log.info("testCriteriaQueryDomainDAO: domains="+domains+", size="+domains.size());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        log.info("testCriteriaQueryDomainDAO: exit ");
    }
    */
}
