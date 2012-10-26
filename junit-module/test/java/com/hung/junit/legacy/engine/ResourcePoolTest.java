package com.hung.junit.legacy.engine;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.hung.auction.engine.IResource;
import com.hung.auction.engine.ResourcePool;
import com.hung.auction.engine.ResourcePool.Resource;

public class ResourcePoolTest extends TestCase {

    private static Logger log = Logger.getLogger(ResourcePoolTest.class);

    public static ResourcePool resourcePool = null;
    public static boolean hasEnded1 = false;
    public static boolean hasEnded2 = false;
    public static boolean hasEnded3 = false;
    public static boolean hasEnded4 = false;
    public static boolean hasEnded5 = false;
    public static boolean hasEnded6 = false;
    public static boolean hasEnded7 = false;
    
    public void testDummy() {}

    /*
    protected void setUp() {
        log.info("//////////////////////////////////////////////");
        log.info("setUp: enter");
        resourcePool = new ResourcePool();
        resourcePool.open();
        log.info("setUp: exit");
        log.info("//////////////////////////////////////////////");
    }

    public void testOpen() {
        log.info("testOpen: enter");

        Runnable runnable1 = new Runnable() {
            public void run() {
                boolean isOpen = resourcePool.isOpen();
                log.info("testOpen.runnable1: isOpen="+isOpen);
                Assert.assertEquals(true, isOpen);
            }
        };
        Runnable runnable2 = new Runnable() {
            public void run() {
                boolean isOpen = resourcePool.isOpen();
                log.info("testOpen.runnable2: isOpen="+isOpen);
                Assert.assertEquals(true, isOpen);
            }
        };


        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        log.info("testOpen: exit");
        log.info("//////////////////////////////////////////////");
    }

    public void testThreads() {
        log.info("testThreads: enter");

        Runnable runnable1 = new Runnable() {
            public void run() {
                IResource resource1 = new Resource();
                log.info("testThreads.runnable1: before add resource1="+resource1);
                resourcePool.add(resource1);
                log.info("testThreads.runnable1: added resource1="+resource1);

                hasEnded1 = true;
            }
        };
        Runnable runnable2 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable2: before acquire");
                IResource resource2 = resourcePool.acquire();
                log.info("testThreads.runnable2: acquired resource2="+resource2);
                Assert.assertNotNull(resource2);

                log.info("testThreads.runnable2: before release resource2="+resource2);
                resourcePool.release(resource2);
                log.info("testThreads.runnable2: released resource2="+resource2);

                hasEnded2 = true;
            }
        };
        Runnable runnable3 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable3: before acquire");
                IResource resource3 = resourcePool.acquire();
                log.info("testThreads.runnable3: acquired resource3="+resource3);
                Assert.assertNotNull(resource3);

                log.info("testThreads.runnable3: before release resource3="+resource3);
                resourcePool.release(resource3);
                log.info("testThreads.runnable3: released resource3="+resource3);

                hasEnded3 = true;
            }
        };
        Runnable runnable4 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable4: before acquire");
                IResource resource4 = resourcePool.acquire();
                log.info("testThreads.runnable4: acquired resource4="+resource4);
                Assert.assertNotNull(resource4);

                log.info("testThreads.runnable4: before release");
                resourcePool.release(resource4);
                log.info("testThreads.runnable4: released resource4="+resource4);

                log.info("testThreads.runnable4: before remove resource4="+resource4);
                resourcePool.remove(resource4);
                log.info("testThreads.runnable4: removed resource4="+resource4);

                resource4 = new Resource();
                log.info("testThreads.runnable4: before add new resource4="+resource4);
                resourcePool.add(resource4);
                log.info("testThreads.runnable4: after add new resource4="+resource4);

                hasEnded4 = true;
            }
        };
        Runnable runnable5 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable5: before acquire");
                IResource resource5 = resourcePool.acquire();
                log.info("testThreads.runnable5: acquired resource5="+resource5);
                Assert.assertNotNull(resource5);

                log.info("testThreads.runnable5: before removeNow resource5="+resource5);
                resourcePool.removeNow(resource5);
                log.info("testThreads.runnable5: after removeNow resource5="+resource5);

                resource5 = new Resource();
                log.info("testThreads.runnable5: before add new resource5="+resource5);
                resourcePool.add(resource5);
                log.info("testThreads.runnable5: after add new resource5="+resource5);

                hasEnded5 = true;
            }
        };
        Runnable runnable6 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable6: before acquire");
                IResource resource6 = resourcePool.acquire();
                log.info("testThreads.runnable6: acquired resource6="+resource6);
                Assert.assertNotNull(resource6);

                log.info("testThreads.runnable6: before removeNow resource6="+resource6);
                resourcePool.removeNow(resource6);
                log.info("testThreads.runnable6: after removeNow resource6="+resource6);

                resource6 = new Resource();
                log.info("testThreads.runnable6: before add new resource6="+resource6);
                resourcePool.add(resource6);
                log.info("testThreads.runnable6: after add new resource6="+resource6);

                hasEnded6 = true;
            }
        };
        Runnable runnable7 = new Runnable() {
            public void run() {
                log.info("testThreads.runnable7: before remove null");
                resourcePool.remove(null);
                log.info("testThreads.runnable7: after remove null");

                IResource resource7 = new Resource();
                log.info("testThreads.runnable7: before remove resource7="+resource7);
                resourcePool.remove(null);
                log.info("testThreads.runnable7: after remove resource7="+resource7);

                log.info("testThreads.runnable7: before removeNow null");
                resourcePool.removeNow(null);
                log.info("testThreads.runnable7: after removeNow null");

                resource7 = new Resource();
                log.info("testThreads.runnable7: before removeNow resource7="+resource7);
                resourcePool.removeNow(null);
                log.info("testThreads.runnable7: after removeNow resource7="+resource7);

                hasEnded7 = true;
            }
        };

        hasEnded1 = false;
        hasEnded2 = false;
        hasEnded3 = false;
        hasEnded4 = false;
        hasEnded5 = false;
        hasEnded6 = false;
        hasEnded7 = false;

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        Thread t4 = new Thread(runnable4);
        Thread t5 = new Thread(runnable5);
        Thread t6 = new Thread(runnable6);
        Thread t7 = new Thread(runnable7);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();

        // need to make test thread last longer or it garbage collect itself and testing fixture
        try {
            Thread.sleep(7000);
        } catch (Exception e) {}

        if (hasEnded1) log.info("*** testThreads.runnable1: done ***");
        if (hasEnded2) log.info("*** testThreads.runnable2: done ***");
        if (hasEnded3) log.info("*** testThreads.runnable3: done ***");
        if (hasEnded4) log.info("*** testThreads.runnable4: done ***");
        if (hasEnded5) log.info("*** testThreads.runnable5: done ***");
        if (hasEnded6) log.info("*** testThreads.runnable6: done ***");
        if (hasEnded7) log.info("*** testThreads.runnable7: done ***");

        log.info("testThreads: exit");
    }

    public void testRemove() {
        log.info("testRemove: enter");

        Runnable runnable1 = new Runnable() {
            public void run() {
                IResource resource1 = new Resource();
                log.info("testRemove.runnable1: before add resource1="+resource1);
                resourcePool.add(resource1);
                log.info("testRemove.runnable1: added resource1="+resource1);

                hasEnded1 = true;
            }
        };
        Runnable runnable2 = new Runnable() {
            public void run() {
                log.info("testRemove.runnable2: before acquire");
                IResource resource2 = resourcePool.acquire();
                log.info("testRemove.runnable2: acquired resource2="+resource2);
                Assert.assertNotNull(resource2);

                log.info("testRemove.runnable2: before release");
                resourcePool.release(resource2);
                log.info("testRemove.runnable2: released resource2="+resource2);

                try {
                    Thread.sleep(500);
                } catch (Exception e) {}

                log.info("testRemove.runnable2: before remove resource2="+resource2);
                resourcePool.remove(resource2);
                log.info("testRemove.runnable2: remove resource2="+resource2);

                hasEnded2 = true;
            }
        };
        Runnable runnable3 = new Runnable() {
            public void run() {
                log.info("testRemove.runnable3: before acquire");
                IResource resource3 = resourcePool.acquire();
                log.info("testRemove.runnable3: acquired resource3="+resource3);
                Assert.assertNotNull(resource3);

                log.info("testRemove.runnable3: before release");
                resourcePool.release(resource3);
                log.info("testRemove.runnable3: released resource3="+resource3);

                try {
                    Thread.sleep(500);
                } catch (Exception e) {}

                log.info("testRemove.runnable3: before remove resource3="+resource3);
                resourcePool.remove(resource3);
                log.info("testRemove.runnable3: after remove resource3="+resource3);

                hasEnded3 = true;
            }
        };
        Runnable runnable4 = new Runnable() {
            public void run() {
                log.info("testRemove.runnable4: before acquire");
                IResource resource4 = resourcePool.acquire();
                log.info("testRemove.runnable4: acquired resource4="+resource4);
                Assert.assertNotNull(resource4);

                log.info("testRemove.runnable4: before release");
                resourcePool.release(resource4);
                log.info("testRemove.runnable4: released resource4="+resource4);

                try {
                    Thread.sleep(500);
                } catch (Exception e) {}

                log.info("testRemove.runnable4: before remove resource4="+resource4);
                resourcePool.remove(resource4);
                log.info("testRemove.runnable4: after remove resource4="+resource4);

                hasEnded4 = true;
            }
        };

        hasEnded1 = false;
        hasEnded2 = false;
        hasEnded3 = false;
        hasEnded4 = false;

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        Thread t4 = new Thread(runnable4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // need to make test thread last longer or it garbage collect itself and testing fixture
        try {
            Thread.sleep(4000);
        } catch (Exception e) {}

        if (hasEnded1) log.info("*** testRemove.runnable1: done ***");
        if (hasEnded2) log.info("*** testRemove.runnable2: done ***");
        if (hasEnded3) log.info("*** testRemove.runnable3: done ***");
        if (hasEnded4) log.info("*** testRemove.runnable4: done ***");

        log.info("testRemove: exit");
    }
    */
}
