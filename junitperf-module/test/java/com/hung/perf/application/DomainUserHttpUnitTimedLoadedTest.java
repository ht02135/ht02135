package com.hung.perf.application;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

import com.clarkware.junitperf.ConstantTimer;
import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TimedTest;

public class DomainUserHttpUnitTimedLoadedTest {
    private static Logger log = Logger.getLogger(DomainUserHttpUnitTimedLoadedTest.class);
    
    public static Test suite() {
        // 2 testcase, testLogin and testCreateUser
        // 50 users, 5sec=5000 millisec, 1user/100millisec, so is 1 testcase/50milli-sec
        
        long maxElapsedTime = 5000;  // test fail if elapsed time exceed 1 sec
        int users = 50;
        
        Test testLoginTest = new DomainUserHttpUnitTestCase("testLogin");
        Test testLoginLoadTest = new LoadTest(testLoginTest, users, new ConstantTimer(10));  // with delay timer
        Test testLoginTimedTest = new TimedTest(testLoginLoadTest, maxElapsedTime);
        
        Test testCreateUserTest = new DomainUserHttpUnitTestCase("testCreateUser");
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
