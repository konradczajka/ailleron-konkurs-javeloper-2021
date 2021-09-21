package com.example.weather;

import java.util.HashMap;
import java.util.Map;

class WeatherProviderUtilsCommonHelper {

    private WeatherConnector weatherConnector;
    private WeatherReportMailProvider mailProvider;
    private Map<String, Weather> cacheWeather = new HashMap<>();

    public WeatherProviderUtilsCommonHelper(WeatherConnector weatherConnector, WeatherReportMailProvider mailProvider) {
        this.weatherConnector = weatherConnector;
        this.mailProvider = mailProvider;
    }

    public Weather checkWeatherAndSendMailWithTemperature(String location) {

        try {
            Weather weather = weatherConnector.weather(location);

            cacheWeather.put(location, weather);

            mailProvider.sendMail(weather);

            return weather;
        } catch (Exception e) {
            log(e);
            return null;
        }
    }

    private static void log(Object object) {
        System.out.println("Weather=" + object.toString());
    }

    private static void log(String text) {
        System.out.println("Weather=" + text);
    }
}
