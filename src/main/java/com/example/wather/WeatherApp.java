package com.example.wather;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.TimeUnit.SECONDS;

@FunctionalInterface
interface WeatherConnector {

    String[] weather(String location);
}

@FunctionalInterface
interface MailProvider {

    void sendMail(final String location, final String weatherDatum, final String datum);
}

public class WeatherApp {

    static final String[] locations = new String[]{"Cracow", "Warsaw", "London", "Lodz", "Kielce", "Tokyo", "NewYork", "Buenos Aires", "Rzeszow"};

    private static final int executionLimitInSeconds = 10;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        WeatherConnector dummyConnector = location -> new String[]{location, "20.0"};
        MailProvider dummyMailProvider = (location, weatherDatum, datum) -> {};

        Runnable task = () -> {
            WeatherProviderUtilsCommonHelper provider = new WeatherProviderUtilsCommonHelper(dummyConnector, dummyMailProvider);

            String location = locations[random.nextInt(locations.length)];

            log(location);

            Weather weather = provider.checkWeatherAndSendMailWithTemperature(location);

            log(weather);
        };

        var pool = new ForkJoinPool(locations.length * 20);
        try {
            for (int i = 0; i < locations.length * 20; i++) {
                pool.execute(task);
            }
            pool.awaitTermination(executionLimitInSeconds, SECONDS);
        } finally {
            pool.shutdown();
        }
    }

    private static void log(Object object) {
        System.out.println("Weather=" + object.toString());
    }

    private static void log(String text) {
        System.out.println("Weather=" + text);
    }
}

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

class Weather {

    private String location;
    private double temp;

    public Weather(String location, double temp) {
        this.location = location;
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Weather{" +
               "location='" + location + '\'' +
               ", temp=" + temp +
               '}';
    }
}