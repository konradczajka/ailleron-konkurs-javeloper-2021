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
        WeatherReportMailProvider dummyMailProvider = weather -> {};

        Runnable task = () -> {
            WeatherReporter provider = new WeatherReporter(
                    new CachingWeatherConnector(dummyConnector),
                    dummyMailProvider);

            String location = locations[random.nextInt(locations.length)];
            provider.checkWeatherAndSendMailWithTemperature(location);
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
}