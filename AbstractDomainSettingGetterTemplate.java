package com.hung.auction.template;

import com.hung.auction.dao.DomainSettingDAO;
import com.hung.auction.domain.Domain;
import com.hung.auction.domain.StringDomainSetting;
import com.hung.auction.service.DomainService;

public abstract class AbstractDomainSettingGetterTemplate {

	protected DomainService domainService;
	protected DomainSettingDAO domainSettingDAO;
	
	public abstract StringDomainSetting getDomainSettingFromCache(String settingName, String domainName);
	
	public abstract StringDomainSetting getDomainSettingFromDAO(String settingName, String domainName);
	
	public abstract void putDomainSettingToCache(StringDomainSetting domainSetting);
	
	public StringDomainSetting getStringDomainSettingMethod(String settingName, String domainName) {

		// check cache
		StringDomainSetting stringDomainSetting = getDomainSettingFromCache(settingName, domainName);
		if (stringDomainSetting != null) {
			return stringDomainSetting;
		}
		
		// dao lookup
		stringDomainSetting = getDomainSettingFromDAO(settingName, domainName);
		if (stringDomainSetting != null) {
			putDomainSettingToCache(stringDomainSetting.clone());	// cache domain setting
			return stringDomainSetting;
		}
		
		// recursive lookup	
		Domain domain = domainService.findByName(domainName);
		stringDomainSetting = getStringDomainSettingMethod(settingName, domain.getParentDomain().getName());
		if (stringDomainSetting != null) {
			stringDomainSetting = stringDomainSetting.clone();
			stringDomainSetting.setSettingDomain(domain);
			putDomainSettingToCache(stringDomainSetting);	// cache parent-domain setting
			return stringDomainSetting;
		}
		
		return null;
	}
}
