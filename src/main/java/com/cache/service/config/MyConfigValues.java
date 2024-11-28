package com.cache.service.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cache.service.Dao.Employees;

@Component
public class MyConfigValues {
	
	@Value("${cache.maxCacheSize}")
	private int maxCacheSize;
	//maxCacheSize = Initial size , 0.75f = it is the load factor that when it is 75% map will resize, 
	//true: Access Order when true then map  maintain the order based on access instead of insertion.
	private Map<String, Employees> internalCache = new LinkedHashMap<>(maxCacheSize, 0.75f, true);

	public int getMaxCacheSize() {
		return maxCacheSize;
	}

	public void setMaxCacheSize(int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}

	public Map<String, Employees> getInternalCache() {
		return internalCache;
	}

	public void setInternalCache(Map<String, Employees> internalCache) {
		this.internalCache = internalCache;
	}
	
    
}
