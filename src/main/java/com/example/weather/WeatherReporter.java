package com.example.weather;

import java.util.Arrays;

class WeatherReporter {

    private final WeatherConnector weatherConnector;
    private final WeatherReportMailProvider mailProvider;

    public WeatherReporter(WeatherConnector weatherConnector, WeatherReportMailProvider mailProvider) {
        this.weatherConnector = weatherConnector;
        this.mailProvider = mailProvider;
    }

    public void prepareAndSendReportFor(String[] locations) {
        Arrays.stream(locations)
              .parallel()
              .peek(location -> log("Preparing weather report for " + location))
              .map(weatherConnector::weather)
              .peek(weather -> log("Sending weather report for %s with temperature %.2f".formatted(weather.getLocation(), weather.getTemp())))
              .forEach(mailProvider::sendMail);
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}
