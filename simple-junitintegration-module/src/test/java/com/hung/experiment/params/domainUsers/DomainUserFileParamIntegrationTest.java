package com.hung.experiment.params.domainUsers;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hung.utils.parameters.domainUsers.DomainUserParameters;

@RunWith(Parameterized.class)
public class DomainUserFileParamIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUserFileParamIntegrationTest.class);
    
    private String loginId;
    private String name;
    private String domainName;
    
    public DomainUserFileParamIntegrationTest(String loginId, String name, String domainName) {
        this.loginId = loginId;
        this.name = name;
        this.domainName = domainName;
    }
    
    @Parameters
    public static Collection data() {
        return DomainUserParameters.NEW_DOMAIN_USERS_FILE_DATA;
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void testData() {
        log.info("########## testData : start ##########");
        log.info("loginId="+loginId);
        log.info("name="+name);
        log.info("domainName="+domainName);
        log.info("########## testData : end ##########");
    }
}
