package com.hung.auction.template;

import org.apache.log4j.Logger;

import com.hung.auction.cache.DomainSettingCache;
import com.hung.auction.dao.DomainSettingDAO;
import com.hung.auction.domain.StringDomainSetting;
import com.hung.auction.service.DomainService;


public class StringDomainSettingGetterTemplate extends AbstractDomainSettingGetterTemplate  {

	private static Logger log = Logger.getLogger(StringDomainSettingGetterTemplate.class);

	public StringDomainSettingGetterTemplate(DomainService domainService, DomainSettingDAO domainSettingDAO) {
		this.domainService = domainService;
		this.domainSettingDAO = domainSettingDAO;
	}

	public StringDomainSetting getDomainSettingFromCache(String settingName, String domainName) {
		StringDomainSetting domainSetting = DomainSettingCache.getInstance().getStringDomainSetting(settingName, domainName);
		if (domainSetting != null) log.info("getDomainSettingFromCache: domainSetting="+domainSetting);
		return domainSetting;
	}
	
	public StringDomainSetting getDomainSettingFromDAO(String settingName, String domainName) {
		StringDomainSetting domainSetting = domainSettingDAO.findStringDomainSetting(settingName, domainName);
		if (domainSetting != null)  log.info("getDomainSettingFromDAO: domainSetting="+domainSetting);
		return domainSetting;
	}
	
	public void putDomainSettingToCache(StringDomainSetting domainSetting) {
		log.info("putDomainSettingToCache: domainSetting="+domainSetting);
		DomainSettingCache.getInstance().putStringDomainSetting(domainSetting);
	}
}
