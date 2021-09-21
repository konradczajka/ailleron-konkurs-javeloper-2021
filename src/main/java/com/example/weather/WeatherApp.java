package com.example.weather;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WeatherApp {

    static final String[] locations = new String[]{"Cracow", "Warsaw", "London", "Lodz", "Kielce", "Tokyo", "NewYork", "Buenos Aires", "Rzeszow"};

    private static final int executionLimitInSeconds = 10;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();

        WeatherConnector dummyConnector = location -> new Weather(location, 20.0);
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