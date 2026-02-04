package com.Caching.Weather_Service.service;

import com.Caching.Weather_Service.entity.WeatherLog;
import com.Caching.Weather_Service.repository.WeatherLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class WeatherLogService {

    @Autowired
    private WeatherLogRepository weatherLogRepository;


    @PostAuthorize("returnObject.performedBy == authentication.name")
    public WeatherLog getLogById(Long id) {
        return weatherLogRepository.findById(id).orElseThrow(()-> new RuntimeException("Log not found !!!"));
    }

    public WeatherLog createLog(WeatherLog log) {
        return weatherLogRepository.save(log);
    }
}
