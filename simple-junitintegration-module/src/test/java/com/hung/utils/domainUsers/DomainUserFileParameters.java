package com.hung.utils.domainUsers;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.hung.utils.FileParameters;

public class DomainUserFileParameters extends FileParameters {
    
    private static Logger log = Logger.getLogger(DomainUserFileParameters.class);
    
    @Override
    public Collection data() {
        return this.getDataFromFile("src/test/resources/data/domainUsers.properties");
    }
}
