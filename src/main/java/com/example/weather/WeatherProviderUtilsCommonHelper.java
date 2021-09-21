package com.example.weather;

import java.util.HashMap;
import java.util.Map;

class WeatherProviderUtilsCommonHelper {

    private WeatherConnector weatherConnector;
    private MailProvider mailProvider;
    private Map<String, Weather> cacheWeather = new HashMap<>();

    public WeatherProviderUtilsCommonHelper(WeatherConnector weatherConnector, MailProvider mailProvider) {
        this.weatherConnector = weatherConnector;
        this.mailProvider = mailProvider;
    }

    public Weather checkWeatherAndSendMailWithTemperature(String location) {

        try {
            String[] weatherData = weatherConnector.weather(location);

            Weather weather = new Weather(weatherData[0], Double.valueOf(weatherData[1]));

            cacheWeather.put(location, weather);

            mailProvider.sendMail(location, weatherData[0], weatherData[1]);

            return weather;
        } catch (Exception e) {
            log(e);
            return null;
        }
    }

    public void setWeatherConnector(WeatherConnector connector) {
        this.weatherConnector = connector;
    }

    private static void log(Object object) {
        System.out.println("Weather=" + object.toString());
    }

    private static void log(String text) {
        System.out.println("Weather=" + text);
    }
}
