package com.hung.junit.legacy.dao;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.hung.auction.dao.DocumentDAO;

public class DocumentDAOTest extends TestCase {

    private static Logger log = Logger.getLogger(DocumentDAOTest.class);

    private ApplicationContext applicationContext = null;
    private DocumentDAO documentDAO = null;

    public void testDummy() {}
    
    /*
    protected void setUp() {
        log.info("********** setUp: enter **********");
        applicationContext = new ClassPathXmlApplicationContext("applicationContext-test-dao.xml");
        documentDAO = (DocumentDAO) applicationContext.getBean("documentDAO");
        log.info("********** setUp: exit **********");
    }
    
    public void testDAO() {
        log.info("********** testDAO: enter **********");

        if (documentDAO == null) {
            log.info("testDAO: documentDAO is null");
        } else {
            log.info("testDAO: documentDAO is not null");
        }

        // List<BinaryDocument> documents = documentDAO.findAll();
        // log.info("testDAO: all documents="+documents);

        // comment out this part, because full text search via contains cant work without index
        // List<StringDocument> strDocuments = documentDAO.findBySearchTextSQL("Hung");
        // log.info("testDAO: all text strDocuments="+strDocuments);

        List<StringDocument> strDocuments = documentDAO.findBySearchText("Hung");
        log.info("testDAO: all text strDocuments="+strDocuments);

        log.info("********** testDAO: exit **********");
    }
    */
}
