package com.hung.selenium.pom.domainUsers;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;

public class DomainUsersPage extends LoginPage {

    private static Logger log = Logger.getLogger(DomainUsersPage.class);
    
    public DomainUsersPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        super.open();
        
        // login
        typeDomainName("root");
        typeLoginId("admin");
        clickSubmit();
        
        // click domainUsers link
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/domainUsers']");
        selenium.waitForPageToLoad("30000");
    }
    
    public boolean isViewAdminURLPresent() {
        return selenium.isElementPresent("css=table a[href='/simple-restfulwebapp-module/auction/domainUsers/admin']");
    }
    
    public boolean isEditAdminURLPresent() {
        return selenium.isElementPresent("css=table a[href='/simple-restfulwebapp-module/auction/domainUsers/admin?edit']");
    }
    
    public boolean isNewUserURLPresent() {
        return selenium.isElementPresent("css=table a[href='/simple-restfulwebapp-module/auction/domainUsers?new']");
    }
    
    public boolean isNewUserFormPresent() {
        return selenium.isElementPresent("css=form#jaxbDomainUser");
    }
    
    public void clickNewUserLink() {
        selenium.click("css=table a[href='/simple-restfulwebapp-module/auction/domainUsers?new']");
        selenium.waitForPageToLoad("30000");
    }
}
