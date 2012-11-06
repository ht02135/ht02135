package com.hung.utils.parameters.domainUsers;

import java.util.Arrays;
import java.util.Collection;

import com.hung.utils.parameters.FileParameters;
import com.hung.utils.parameters.SimpleParameters;

public class DomainUserParameters {
	
    // File, generic data
    public static final Collection NEW_DOMAIN_USERS_FILE_DATA;
    static
    {
        NEW_DOMAIN_USERS_FILE_DATA = (new FileParameters("src/test/resources/data/domainUsers.properties")).data();
    }
    
    public static final Collection DOMAIN_USER_LOGINIDS_FILE_DATA;
    static
    {
        DOMAIN_USER_LOGINIDS_FILE_DATA = (new FileParameters("src/test/resources/data/domainUserLoginIds.properties")).data();
    }
    
    // File, selenium2webdriverbacked
    public static final Collection NEW_DOMAIN_USERS_WEBDRIVERBACKED_FILE_DATA;
    static
    {
    	NEW_DOMAIN_USERS_WEBDRIVERBACKED_FILE_DATA = (new FileParameters("src/test/resources/data/selenium2webdriverbacked/domainUsers.properties")).data();
    }
    
    // Simple Default data
    public static final Collection NEW_DOMAIN_USERS_SIMPLE_DATA;
    static
    {
        NEW_DOMAIN_USERS_SIMPLE_DATA = (new SimpleParameters() {
            @Override
            public Collection data() {
                // JaxbDomainUser(String loginId, String name, String userDomainName)
                return Arrays.asList(
                    new Object[][] { 
                        { "domain_user_param1", "domain_user_param1", "subroot" }, 
                        { "domain_user_param2", "domain_user_param2", "subroot" },
                        { "domain_user_param3", "domain_user_param3", "subroot" },
                        { "domain_user_param4", "domain_user_param4", "subroot" },
                    }
                );
            }
        }).data();
    }
}
