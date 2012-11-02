package com.hung.utils.parameters.domainUsers;

import java.util.Arrays;
import java.util.Collection;

import com.hung.utils.parameters.Parameters;

public class DomainUserSimpleParameters implements Parameters {

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
}
