package com.hung.experiment.jmeterjunitrequest;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

// created this class to see if can be picked up by JMeter UI : JUnit Request Sampler

public class JMeterJunitRequestIntegrationTest extends TestCase {
    
    @Test
    public void testJUnitRequest() {
    	Assert.assertTrue(true);
    }
    
    @Test
    public void testDummy() {
    	Assert.assertTrue(true);
    }
}
