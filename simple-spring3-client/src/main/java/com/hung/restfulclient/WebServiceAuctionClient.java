package com.hung.restfulclient;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.Address;
import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.Seller;
import com.hung.auction.domain.StringDomainSetting;
import com.hung.auction.service.DomainService;
import com.hung.auction.service.DomainSettingService;
import com.hung.auction.service.DomainUserService;
import com.hung.auction.service.LoginService;
import com.hung.auction.service.SellerService;
import com.hung.setting.SettingNames;

public class WebServiceAuctionClient 
{
	private static Logger log = Logger.getLogger(WebServiceAuctionClient.class);
	
    public static void main( String[] args )
    {
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-spring3-client.xml");
    	
    	// running inside servlet container
    	// testSeller(applicationContext);
		
		// test login service
    	testLogin(applicationContext, "testDomain", "root", "atsai", "An Chih Tsai");
    	testLogin(applicationContext, "testDomain2", "root", "wtsai", "Wan Tsai");
    	testLogin(applicationContext, "testDomain3", "root", "htsai", "Hung Tsai");
    	testLogin(applicationContext, "testDomain4", "testDomain3", "htsai2", "Hung Tsai2");
    	testLogin(applicationContext, "testDomain5", "testDomain3", "htsai3", "Hung Tsai3");
    	testLogin(applicationContext, "testDomain6", "testDomain5", "htsai4", "Hung Tsai4");

    	testMerge(applicationContext);

    	// populate some settings
    	populateSettings(applicationContext);
    	
    	testSettings(applicationContext);
    }
    
    public static void testSeller(ApplicationContext applicationContext) {
    	SellerService httpSellerService = (SellerService) applicationContext.getBean("httpSellerServiceExporter");
    	try {
    		Address homeAddress = new Address("14 Lake Shore Court Unit 4C", "02135", "Brighton");
    		Seller seller = new Seller("An Chih Tsai", homeAddress, "ht02135@yahoo.com");
    		httpSellerService.save(seller);
    		httpSellerService.findById(seller.getId());
		} catch (Exception e) {
			log.info("testSeller: e="+e.toString());
		}
    }
    
    public static void populateSettings(ApplicationContext applicationContext) {
    	DomainService httpDomainService = (DomainService) applicationContext.getBean("httpDomainServiceExporter");
    	DomainSettingService httpDomainSettingService = (DomainSettingService) applicationContext.getBean("httpDomainSettingServiceExporter");
    	
    	try {
    		httpDomainSettingService.save(new BooleanDomainSetting(SettingNames.ENABLE_JAVAMAIL_SETTING, Boolean.FALSE, httpDomainService.findByName("testDomain4")));
    	} catch (Exception e) {
			log.info("testSeller: e="+e.toString());
		}
		
    	try {
    		httpDomainSettingService.save(new IntegerDomainSetting(SettingNames.PICKER_EDITOR_WIDTH_SETTING, Integer.valueOf(15), httpDomainService.findByName("testDomain2")));
    	} catch (Exception e) {
			log.info("testSeller: e="+e.toString());
		}
		
    	try {
    		httpDomainSettingService.save(new StringDomainSetting(SettingNames.PICKER_FIND_LABEL_SETTING, "modified find", httpDomainService.findByName("testDomain5")));
    	} catch (Exception e) {
			log.info("testSeller: e="+e.toString());
		}
    }
    
    public static void testSettings(ApplicationContext applicationContext) {
    	LoginService httpLoginService = (LoginService) applicationContext.getBean("httpLoginServiceExporter");
    	DomainService httpDomainService = (DomainService) applicationContext.getBean("httpDomainServiceExporter");
    	DomainSettingService httpDomainSettingService = (DomainSettingService) applicationContext.getBean("httpDomainSettingServiceExporter");
    	
    	try {
        	BooleanDomainSetting booleanDomainSetting = httpDomainSettingService.getBooleanDomainSetting(SettingNames.ENABLE_JAVAMAIL_SETTING, "testDomain6");
        	IntegerDomainSetting integerDomainSetting = httpDomainSettingService.getIntegerDomainSetting(SettingNames.PICKER_EDITOR_WIDTH_SETTING, "testDomain6");
        	StringDomainSetting stringDomainSetting = httpDomainSettingService.getStringDomainSetting(SettingNames.PICKER_FIND_LABEL_SETTING, "testDomain6");
        		
        	log.info("booleanDomainSetting="+booleanDomainSetting);
        	log.info("integerDomainSetting="+integerDomainSetting);
        	log.info("stringDomainSetting="+stringDomainSetting);
    	} catch (Exception e) {
    		log.info("testLogin: e="+e.toString());
    	}
    	
    	try {
        	BooleanDomainSetting booleanDomainSetting = httpDomainSettingService.getBooleanDomainSetting(SettingNames.ENABLE_JAVAMAIL_SETTING, "testDomain4");
        	IntegerDomainSetting integerDomainSetting = httpDomainSettingService.getIntegerDomainSetting(SettingNames.PICKER_EDITOR_WIDTH_SETTING, "testDomain4");
        	StringDomainSetting stringDomainSetting = httpDomainSettingService.getStringDomainSetting(SettingNames.PICKER_FIND_LABEL_SETTING, "testDomain4");
        		
        	log.info("booleanDomainSetting="+booleanDomainSetting);
        	log.info("integerDomainSetting="+integerDomainSetting);
        	log.info("stringDomainSetting="+stringDomainSetting);
    	} catch (Exception e) {
    		log.info("testLogin: e="+e.toString());
    	}
    	
    	try {
    		List<StringDomainSetting> stringDomainSettings = httpDomainSettingService.getAllCachedStringDomainSettings();
    		
    		log.info("--- display all cached settings - start --------------");
    		for (int i=0; i<stringDomainSettings.size(); i++) {
    			log.info("stringDomainSetting("+i+")="+stringDomainSettings.get(i));
    		}
    		log.info("--- display all cached settings - end ----------------");
    	} catch (Exception e) {
    	}
    }
    
    public static void testMerge(ApplicationContext applicationContext) {
    	DomainService httpDomainService = (DomainService) applicationContext.getBean("httpDomainServiceExporter");
    	DomainUserService httpDomainUserService = (DomainUserService) applicationContext.getBean("httpDomainUserServiceExporter");
    	
    	// testLogin(applicationContext, "testDomain6", "testDomain5", "htsai4", "Hung Tsai4");
    	
    	// create/persist new user
    	Domain domain = httpDomainService.findByName("testDomain3");
		DomainUser domainUser = new DomainUser("htsai5", "Hung Tsai5", domain);
		log.info("testMerge: test1 start -------------------------------------------");
		log.info("testMerge: persist domainUser="+domainUser);
		httpDomainUserService.save(domainUser);
		domainUser = httpDomainUserService.findByLoginId(domainUser.getLoginId());
		log.info("testMerge: get domainUser="+domainUser);
		log.info("testMerge: test1 end ---------------------------------------------");
		
		log.info("testMerge: test2 start -------------------------------------------");
		// modify/persist new user
		domainUser.setName("Hung Tsai5 MODIFIED");
		httpDomainUserService.save(domainUser);
		log.info("testMerge: persist domainUser="+domainUser);
		domainUser = httpDomainUserService.findByLoginId(domainUser.getLoginId());
		log.info("testMerge: get domainUser="+domainUser);
		log.info("testMerge: test2 end ---------------------------------------------");
		
		log.info("testMerge: test3 start -------------------------------------------");
    	Domain domain2 = httpDomainService.findByName("testDomain5");
		DomainUser domainUser2 = new DomainUser("htsai5", "Hung Tsai6", domain2);
		httpDomainUserService.save(domainUser2);
		log.info("testMerge: persist domainUse2r="+domainUser2);
		domainUser2 = httpDomainUserService.findByLoginId(domainUser2.getLoginId());
		log.info("testMerge: get domainUse2r="+domainUser2);
		log.info("testMerge: test3 end ---------------------------------------------");
    }
    
    public static void testLogin(ApplicationContext applicationContext, String domainName, String parentDomainName, String userLoginName, String UserName) {
    	DomainService httpDomainService = (DomainService) applicationContext.getBean("httpDomainServiceExporter");
    	DomainUserService httpDomainUserService = (DomainUserService) applicationContext.getBean("httpDomainUserServiceExporter");
    	LoginService httpLoginService = (LoginService) applicationContext.getBean("httpLoginServiceExporter");
    	
    	Domain domain = null;
    	Domain parentDomain = null;
    	DomainUser domainUser = null;
    	
    	// create domain
    	try {
    		domain = httpDomainService.findByName(domainName);
    		parentDomain = httpDomainService.findByName(parentDomainName);
    		if (domain == null) {
    			domain = new Domain(domainName);
    			domain.setParentDomain(parentDomain);
    			httpDomainService.save(domain);
    		}
    	} catch (Exception e) {
			log.info("testLogin: e="+e.toString());
		}
		
		// find domain
    	try {
    		domain = httpDomainService.findByName(domainName);
    	} catch (Exception e) {
			log.info("testLogin: e="+e.toString());
		}
		
		// create domain user
    	try {
    		domainUser = httpDomainUserService.findByLoginId(userLoginName);
    		if (domainUser == null) {
    			domainUser = new DomainUser(userLoginName, UserName, domain);
    			httpDomainUserService.save(domainUser);
    		}
    	} catch (Exception e) {
			log.info("testLogin: e="+e.toString());
		}
		
		// find domain user
    	try {
    		domainUser = httpDomainUserService.findByLoginId(userLoginName);
    	} catch (Exception e) {
			log.info("testLogin: e="+e.toString());
		}
		
		// login
    	try {
        	if (httpLoginService.login(userLoginName, domainName).booleanValue()) {
        		log.info("test httpLoginService proxy: loggedIn successful");
        		httpLoginService.logout();
        	} else {
        		log.info("test httpLoginService proxy: loggedIn failed");
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
