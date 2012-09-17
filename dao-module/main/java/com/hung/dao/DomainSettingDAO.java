package com.hung.auction.dao;

import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;

public interface DomainSettingDAO {

    public void save(StringDomainSetting stringDomainSetting);
    
    public StringDomainSetting findStringDomainSetting(String settingName, String domainName);
    
    public BooleanDomainSetting findBooleanDomainSetting(String settingName, String domainName);
    
    public IntegerDomainSetting findIntegerDomainSetting(String settingName, String domainName);
}