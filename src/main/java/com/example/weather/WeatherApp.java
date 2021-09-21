package com.example.weather;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WeatherApp {

    static final String[] locations = new String[]{"Cracow", "Warsaw", "London", "Lodz", "Kielce", "Tokyo", "NewYork", "Buenos Aires", "Rzeszow"};

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        WeatherConnector dummyConnector = location -> new Weather(location, 20.0);
        WeatherReportMailProvider dummyMailProvider = weather -> {};
        WeatherReporter provider = new WeatherReporter(
                new CachingWeatherConnector(dummyConnector),
                dummyMailProvider);

        provider.prepareAndSendReportFor(locations);
    }
}