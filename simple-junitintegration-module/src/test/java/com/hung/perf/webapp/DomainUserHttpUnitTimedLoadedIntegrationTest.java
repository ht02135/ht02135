package com.hung.perf.webapp;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

import com.clarkware.junitperf.ConstantTimer;
import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TimedTest;
import com.hung.perf.webapp.DomainUserHttpUnitIntegrationTest;

public class DomainUserHttpUnitTimedLoadedIntegrationTest {
    private static Logger log = Logger.getLogger(DomainUserHttpUnitTimedLoadedIntegrationTest.class);
    
    public static Test suite() {
        // 2 testcase, testLogin and testCreateUser
        // 10 users, 5sec=1000 millisec, 1user/100millisec, so is 1 testcase/50milli-sec
        
        long maxElapsedTime = 1000;  // test fail if elapsed time exceed 1 sec
        int users = 10;
        
        Test testLoginTest = new DomainUserHttpUnitIntegrationTest("testLogin");
        Test testLoginLoadTest = new LoadTest(testLoginTest, users, new ConstantTimer(10));  // with delay timer
        Test testLoginTimedTest = new TimedTest(testLoginLoadTest, maxElapsedTime);
        
        Test testCreateUserTest = new DomainUserHttpUnitIntegrationTest("testCreateUser");
        Test testCreateUserLoadTest = new LoadTest(testCreateUserTest, users, new ConstantTimer(10));  // with delay timer
        Test testCreateUserTimedTest = new TimedTest(testCreateUserLoadTest, maxElapsedTime);
        
        TestSuite suite = new TestSuite();
        suite.addTest(testLoginTimedTest);
        suite.addTest(testCreateUserTimedTest);
        return suite;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}
