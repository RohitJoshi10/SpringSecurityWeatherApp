package com.Caching.Weather_Service.controller;

import com.Caching.Weather_Service.entity.Weather;
import com.Caching.Weather_Service.repository.WeatherRepository;
import com.Caching.Weather_Service.service.CacheInspectionService;
import com.Caching.Weather_Service.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherRepository weatherRepository;
    private final CacheInspectionService cacheInspectionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER, ADMIN')")
    public String getWeatherByCity(@RequestParam String city){
        String weatherCity = weatherService.getWeatherByCity(city);
        return weatherCity;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Weather addWeather(@RequestBody Weather weather){
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getALlWeather(){
        return weatherRepository.findAll();
    }

    @GetMapping("/cacheData")
    public void getCacheData(){
        cacheInspectionService.printCacheContents("weather");
    }

    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate){
        return weatherService.updateWeather(city, weatherUpdate);
    }

    @DeleteMapping("/{city}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteWeather(@PathVariable String city){
        weatherService.deleteWeather(city);
        return "Weather data for " + city + " has been deleted and cache envicted.";
    }

    @GetMapping("/health")
    public String getHealth(){
        return "Healthy";
    }



}
