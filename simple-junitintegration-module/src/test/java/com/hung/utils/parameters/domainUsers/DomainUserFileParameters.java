package com.hung.utils.parameters.domainUsers;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.hung.utils.parameters.FileParameters;

public class DomainUserFileParameters extends FileParameters {
    
    private static Logger log = Logger.getLogger(DomainUserFileParameters.class);
    
    @Override
    public Collection data() {
        return this.getDataFromFile("src/test/resources/data/domainUsers.properties");
    }
}