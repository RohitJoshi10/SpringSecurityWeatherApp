package com.Caching.Weather_Service.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CacheInspectionService {

    private  final CacheManager cacheManager;

    public void printCacheContents(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if(cache != null)
        {
            System.out.println("Cache Contents: ");
            System.out.println(Objects.requireNonNull(cache.getNativeCache()));
        } else{
            System.out.println("No such cache: " + cacheName);
        }
    }
}
