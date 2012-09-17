package com.hung.auction.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;


public class DomainSettingCache {
    private static Logger log = Logger.getLogger(DomainSettingCache.class);

    private static DomainSettingCache instance = null;

    private Cache domainSettingCache = null;    // thread-safe

    public static synchronized DomainSettingCache getInstance() {
        if (instance == null) { instance = new DomainSettingCache(); }
        return instance;
    }

    private DomainSettingCache() {
        CacheManager.getInstance().addCache("domainSettingCache");
        domainSettingCache = CacheManager.getInstance().getCache("domainSettingCache");
        if (domainSettingCache == null) log.info("domainSettingCache is null");
    }

    public BooleanDomainSetting getBooleanDomainSetting(String settingName, String domainName) {
        return (BooleanDomainSetting) getStringDomainSetting(settingName, domainName);
    }

    public IntegerDomainSetting getIntegerDomainSetting(String settingName, String domainName) {
        return (IntegerDomainSetting) getStringDomainSetting(settingName, domainName);
    }

    public StringDomainSetting getStringDomainSetting(String settingName, String domainName) {
        StringDomainSetting stringDomainSetting = null;
        String key = domainName + "/" + settingName;

        log.info("getStringDomainSetting: key="+key);
        Element domainSettingElement = domainSettingCache.get(key);
        if (domainSettingElement != null) {
            stringDomainSetting = (StringDomainSetting) domainSettingElement.getObjectValue();
        }

        return stringDomainSetting;
    }

    public void putStringDomainSetting(StringDomainSetting stringDomainSetting) {
        if (stringDomainSetting != null) {
            String key = stringDomainSetting.getSettingDomain().getName() + "/" + stringDomainSetting.getName();

            log.info("putStringDomainSetting: put <key="+key+", stringDomainSetting="+stringDomainSetting+"> into cache");
            domainSettingCache.put(new Element(key, stringDomainSetting));
        } else {
            log.info("putStringDomainSetting: stringDomainSetting is null");
        }
    }

    public List<StringDomainSetting> getAllCachedStringDomainSettings() {
        List<String> keys = domainSettingCache.getKeys();
        List<StringDomainSetting> stringDomainSettings = new ArrayList(keys.size());

        Element domainSettingElement = null;
        for (int i=0; i<keys.size(); i++) {
            domainSettingElement = domainSettingCache.get(keys.get(i));
            stringDomainSettings.add((StringDomainSetting) domainSettingElement.getObjectValue());
        }
        return stringDomainSettings;
    }
}