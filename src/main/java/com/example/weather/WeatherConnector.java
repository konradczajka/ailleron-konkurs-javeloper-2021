package com.example.weather;

@FunctionalInterface
interface WeatherConnector {

    Weather weather(String location);
}
