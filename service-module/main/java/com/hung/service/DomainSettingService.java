package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;

public interface DomainSettingService {
	
	public void save(StringDomainSetting stringDomainSetting);
	
	public StringDomainSetting getStringDomainSetting(String settingName, String domainName);
	
	public BooleanDomainSetting getBooleanDomainSetting(String settingName, String domainName);
	
	public IntegerDomainSetting getIntegerDomainSetting(String settingName, String domainName);
	
	public List<StringDomainSetting> getAllCachedStringDomainSettings();
}
