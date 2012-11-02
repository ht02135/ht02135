package com.hung.utils.testwatcher;

import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class LogFailedTestWatcher extends TestWatcher {
    
    private static Logger log = Logger.getLogger(LogFailedTestWatcher.class);
    
    public LogFailedTestWatcher() {
        super();
    }

    @Override
    public void failed(Throwable e, Description description) {
        log.info("########## failed : start ##########");
        
        log.info("e="+e);
        log.info("description="+description);
        
        log.info("########## failed : end ##########");
    }
    
    @Override
    public void starting(Description description)  {
        // do nothing
    }
    
    @Override
    public void succeeded(Description description) {
        log.info("########## succeeded : start ##########");
        
        log.info("description="+description);
        
        log.info("########## succeeded : end ##########");
    }
    
}
