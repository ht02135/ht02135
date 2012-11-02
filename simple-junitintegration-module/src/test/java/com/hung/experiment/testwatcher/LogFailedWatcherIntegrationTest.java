package com.hung.experiment.testwatcher;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.hung.utils.testwatcher.LogFailedTestWatcher;

public class LogFailedWatcherIntegrationTest {
    
    @Rule
    public TestRule testWatcher = new LogFailedTestWatcher();

    /* successful, but want to comment out
    @Test
    public void testFailed() {
        // force a test failure
        Assert.assertEquals(1, 2);
    }
    */
    
    @Test
    public void testSucceeded() {
        // force a test success
        Assert.assertEquals(1, 1);
    }
}
