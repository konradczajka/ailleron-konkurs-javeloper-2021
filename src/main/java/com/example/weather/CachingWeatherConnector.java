package com.example.weather;


import java.util.HashMap;
import java.util.Map;

/**
 * {@link WeatherConnector} decorator which caches obtained measurements
 *
 * Warning. The cache is bounded neither in size nor time
 */
class CachingWeatherConnector implements WeatherConnector {

    private final WeatherConnector wrappedConnector;
    private final Map<String, Weather> cache = new HashMap<>();

    CachingWeatherConnector(WeatherConnector wrappedConnector) {
        this.wrappedConnector = wrappedConnector;
    }

    @Override
    public Weather weather(String location) {
        return cache.computeIfAbsent(location, wrappedConnector::weather);
    }
}
