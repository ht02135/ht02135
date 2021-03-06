package com.hung.perf.webapp;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

import com.clarkware.junitperf.TimedTest;

public class DomainUserHttpUnitTimedIntegrationTest {
    private static Logger log = Logger.getLogger(DomainUserHttpUnitTimedIntegrationTest.class);
    
    public static Test suite() {
        long maxElapsedTime = 1000;  // test fail if elapsed time exceed 1 sec
        
        Test testLoginTest = new DomainUserHttpUnitIntegrationTest("testLogin");
        Test testLoginTimedTest = new TimedTest(testLoginTest, maxElapsedTime);
        
        Test testCreateUserTest = new DomainUserHttpUnitIntegrationTest("testCreateUser");
        Test testCreateUserTimedTest = new TimedTest(testCreateUserTest, maxElapsedTime);
        
        TestSuite suite = new TestSuite();
        suite.addTest(testLoginTimedTest);
        suite.addTest(testCreateUserTimedTest);
        return suite;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}
