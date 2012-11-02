package com.hung.experiment.params.domainUsers;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hung.utils.parameters.domainUsers.DomainUserSimpleParameters;

@RunWith(Parameterized.class)
public class DomainUserSimpleParamIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUserSimpleParamIntegrationTest.class);
    
    private String loginId;
    private String name;
    private String domainName;
    
    public DomainUserSimpleParamIntegrationTest(String loginId, String name, String domainName) {
        this.loginId = loginId;
        this.name = name;
        this.domainName = domainName;
    }
    
    @Parameters
    public static Collection data() {
        // JaxbDomainUser(String loginId, String name, String userDomainName)
        return new DomainUserSimpleParameters().data();
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
