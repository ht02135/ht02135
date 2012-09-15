package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.cache.DomainSettingCache;
import com.hung.auction.dao.DomainSettingDAO;
import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.Domain;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;
import com.hung.auction.template.BooleanDomainSettingGetterTemplate;
import com.hung.auction.template.IntegerDomainSettingGetterTemplate;
import com.hung.auction.template.StringDomainSettingGetterTemplate;
import com.hung.setting.SettingNames;

public class DomainSettingServiceImpl implements DomainSettingService {

	private static Logger log = Logger.getLogger(DomainSettingServiceImpl.class);

	private DomainService domainService;
	private DomainSettingDAO domainSettingDAO;
	
	public DomainSettingServiceImpl(DomainService domainService, DomainSettingDAO domainSettingDAO) {
		setDomainService(domainService);
		setDomainSettingDAO(domainSettingDAO);
		populateRootDomainSetting();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(StringDomainSetting stringDomainSetting) {
		domainSettingDAO.save(stringDomainSetting);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public StringDomainSetting getStringDomainSetting(String settingName, String domainName) {
		return new StringDomainSettingGetterTemplate(domainService, domainSettingDAO).getStringDomainSettingMethod(settingName, domainName);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public BooleanDomainSetting getBooleanDomainSetting(String settingName, String domainName) {
		return (BooleanDomainSetting) new BooleanDomainSettingGetterTemplate(domainService, domainSettingDAO).getStringDomainSettingMethod(settingName, domainName);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public IntegerDomainSetting getIntegerDomainSetting(String settingName, String domainName) {
		return (IntegerDomainSetting) new IntegerDomainSettingGetterTemplate(domainService, domainSettingDAO).getStringDomainSettingMethod(settingName, domainName);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<StringDomainSetting> getAllCachedStringDomainSettings() {
		return DomainSettingCache.getInstance().getAllCachedStringDomainSettings();
	}
	
	private void populateRootDomainSetting() {
		log.info("populateRootDomainSetting: enter");
		Domain rootDomain = domainService.findByName(Domain.ROOT_NAME);
		
		BooleanDomainSetting[] booleanDomainSettings = {
			new BooleanDomainSetting(SettingNames.ENABLE_JAVAMAIL_SETTING, Boolean.FALSE, rootDomain)
		};
		
		IntegerDomainSetting[] integerDomainSettings = {
			new IntegerDomainSetting(SettingNames.PICKER_EDITOR_WIDTH_SETTING, Integer.valueOf(10), rootDomain)
		};
		
		StringDomainSetting[] stringDomainSettings = {
			new StringDomainSetting(SettingNames.PICKER_FIND_LABEL_SETTING, "find", rootDomain)
		};
		
		for (int i=0; i<booleanDomainSettings.length; i++) {
			try {
				if (domainSettingDAO.findBooleanDomainSetting(booleanDomainSettings[i].getName(), Domain.ROOT_NAME) == null) {
					domainSettingDAO.save(booleanDomainSettings[i]);
				}
			} catch (Exception e) {}
		}
		
		for (int i=0; i<integerDomainSettings.length; i++) {
			try {
				if (domainSettingDAO.findIntegerDomainSetting(integerDomainSettings[i].getName(), Domain.ROOT_NAME) == null) {
					domainSettingDAO.save(integerDomainSettings[i]);
				}
			} catch (Exception e) {}
		}
		
		for (int i=0; i<stringDomainSettings.length; i++) {
			try {
				if (domainSettingDAO.findStringDomainSetting(stringDomainSettings[i].getName(), Domain.ROOT_NAME) == null) {
					domainSettingDAO.save(stringDomainSettings[i]);
				}
			} catch (Exception e) {}
		}
		log.info("populateRootDomainSetting: exit");
	}
	
	// injection methods

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}
	
	public void setDomainSettingDAO(DomainSettingDAO domainSettingDAO) {
		this.domainSettingDAO = domainSettingDAO;
	}

}
