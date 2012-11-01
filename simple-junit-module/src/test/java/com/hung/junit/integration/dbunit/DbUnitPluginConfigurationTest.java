package com.hung.junit.integration.dbunit;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.domain.Domain;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test-dao.xml"})
public class DbUnitPluginConfigurationTest  {

    private static Logger log = Logger.getLogger(DbUnitPluginConfigurationTest.class);
    
    // this instance will be dependency injected by name
    @Autowired
    @Qualifier("domainDAO")
    private DomainDAO domainDAO;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testDbUnitPluginConfiguration() {
        log.info("////////// testDbUnitPluginConfiguration - start //////////");
        
        // fresh db populated by dbunit-plugin via flat xml file should not have dbUnitPluginConfigDomain
        Domain dbUnitPluginConfigDomain = domainDAO.findByName("dbUnitPluginConfigDomain");
        if (dbUnitPluginConfigDomain != null) {
            log.error("dbunit-plugin should clean this, dbUnitPluginConfigDomain should not exist");
            Assert.fail();
        } else {
            dbUnitPluginConfigDomain = new Domain("dbUnitPluginConfigDomain");
            domainDAO.save(dbUnitPluginConfigDomain); 
            Domain fetchDbUnitPluginConfigDomain = domainDAO.findByName("dbUnitPluginConfigDomain");
            String expectedDomainName = "dbUnitPluginConfigDomain";
            String actualDomainName = fetchDbUnitPluginConfigDomain.getName();
            Assert.assertEquals(expectedDomainName, actualDomainName);
        }
        
        log.info("////////// testDbUnitPluginConfiguration - end //////////");
    }
}
