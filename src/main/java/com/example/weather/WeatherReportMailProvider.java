package com.example.weather;

@FunctionalInterface
interface WeatherReportMailProvider {

    void sendMail(Weather weather);
}
