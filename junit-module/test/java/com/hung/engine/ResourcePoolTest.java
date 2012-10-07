package com.hung.engine;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hung.auction.engine.IResource;
import com.hung.auction.engine.IResourcePool;
import com.hung.auction.engine.ResourcePool;

// http://java.dzone.com/articles/concurrent-junit-tests

//@RunWith(ConcurrentJunitRunner.class)  <-- useless for this test
public class ResourcePoolTest {

    private static Logger log = Logger.getLogger(ResourcePoolTest.class);
    
    public static IResourcePool resourcePool = null;
    
    @Before
    public void setUp() {  
        resourcePool = new ResourcePool();
        resourcePool.open();
        resourcePool.add(new ResourcePool.Resource());
    }
    
    @After
    public void tearDown() {
        resourcePool.close();
    }
    
    @Test 
    public void testAdd() throws Throwable {
        log.info("########## ResourcePoolMultiThreadTest : testAdd - start ##########");
        
        // add one resource
        IResource resource = new ResourcePool.Resource();
        resourcePool.add(resource);
        log.info("added resource");
        
        IResource refResource = resourcePool.acquire();
        log.info("acquired resource");
        Assert.assertNotNull(refResource);
        
        resourcePool.release(refResource);
        log.info("released resource");
        
        log.info("########## ResourcePoolMultiThreadTest : testAdd - end ##########");
    }
    
    @Test 
    public void testAddRemove() throws Throwable {
        // this test will not increase total resource
        log.info("########## ResourcePoolMultiThreadTest : testAddRemove - start ##########");
        
        IResource resource = new ResourcePool.Resource();
        resourcePool.add(resource);
        log.info("added resource");
        
        IResource refResource = resourcePool.acquire();
        log.info("acquired resource");
        Assert.assertNotNull(refResource);
        
        resourcePool.release(refResource);
        log.info("release resource");
        
        resourcePool.remove(refResource);
        log.info("removed resource");
        
        log.info("########## ResourcePoolMultiThreadTest : testAddRemove - end ##########");
    }
    
    @Test 
    public void testRemoveNow() throws Throwable {
        // this test will not increase total resource
        log.info("########## ResourcePoolMultiThreadTest : testRemoveNow - start ##########");
        
        IResource resource = resourcePool.acquire();
        log.info("acquired resource");
        Assert.assertNotNull(resource);
    
        resourcePool.removeNow(resource);
        log.info("removedNow resource");

        IResource resource2 = new ResourcePool.Resource();
        resourcePool.add(resource2);
        log.info("added resource");
        
        log.info("########## ResourcePoolMultiThreadTest : testRemoveNow - end ##########");
    }
    
    @Test 
    public void testAcquireWithTimeOut() throws Throwable {
        // this test will not increase total resource
        log.info("########## ResourcePoolMultiThreadTest : testAcquireWithTimeOut - start ##########");
        
        IResource resource = resourcePool.acquire(100, TimeUnit.MILLISECONDS);
        if (resource != null) {
            log.info("acquired resource");
            Assert.assertNotNull(resource);
            
            resourcePool.release(resource);
            log.info("rlease resource");
            
            resourcePool.remove(resource);
            log.info("removed resource");
            
            IResource resource2 = new ResourcePool.Resource();
            resourcePool.add(resource2);
            log.info("added resource");
        }
        
        log.info("########## ResourcePoolMultiThreadTest : testAcquireWithTimeOut - end ##########");
    }
}
