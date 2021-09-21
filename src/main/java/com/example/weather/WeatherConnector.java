package com.example.weather;

@FunctionalInterface
interface WeatherConnector {

    String[] weather(String location);
}
