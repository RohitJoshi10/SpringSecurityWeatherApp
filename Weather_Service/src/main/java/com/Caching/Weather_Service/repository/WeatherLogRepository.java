package com.Caching.Weather_Service.repository;

import com.Caching.Weather_Service.entity.Weather;
import com.Caching.Weather_Service.entity.WeatherLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
}
