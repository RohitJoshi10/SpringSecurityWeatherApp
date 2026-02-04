package com.Caching.Weather_Service.service;

import com.Caching.Weather_Service.entity.Weather;
import com.Caching.Weather_Service.repository.WeatherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Cacheable(value = "weather", key = "#city")
    public String getWeatherByCity(String city) {
        System.out.println("Fetching data from DB for city: " + city);
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    // If we don't update the cache and run this API then in the DB weather is updated but our above get API will fetch  data from cache and in the cache data is not updated yet to solve this problem we need to update the cache also when weather is updated.
    //Even after doing this weather will not get updated in the cache as it use concurrent hashmap it will create a entry of updated whether but it will not pick it this create inconsistency to resolve this error we need to give them key also apart from weather.
    @CachePut(value = "weather", key = "#city")
    public String updateWeather(String city, String updatedWeather) {
        weatherRepository.findByCity(city).ifPresent(weather -> {
            weather.setForecast(updatedWeather);
            weatherRepository.save(weather);
        });

        return updatedWeather;
    }

    @Transactional
    @CacheEvict(value = "weather", key = "#city")
    public void deleteWeather(String city) {
        System.out.println("Removing weather data for city: " + city);
        weatherRepository.deleteByCity(city);
    }
}
