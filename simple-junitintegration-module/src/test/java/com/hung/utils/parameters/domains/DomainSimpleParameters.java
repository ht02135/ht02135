package com.hung.utils.parameters.domains;

import java.util.Arrays;
import java.util.Collection;

import com.hung.utils.parameters.Parameters;

public class DomainSimpleParameters implements Parameters {

    @Override
    public Collection data() {
        // JaxbDomain(String name, String parentDomainName)
        return Arrays.asList(
            new Object[][] { 
                { "domain_param1", "subroot" }, 
                { "domain_param2", "subroot" },
                { "domain_param3", "subroot" },
                { "domain_param4", "subroot" },
            }
        );
    }
    
}
