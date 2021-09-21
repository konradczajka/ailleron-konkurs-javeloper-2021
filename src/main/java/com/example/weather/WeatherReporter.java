package com.example.weather;

class WeatherReporter {

    private final WeatherConnector weatherConnector;
    private final WeatherReportMailProvider mailProvider;

    public WeatherReporter(WeatherConnector weatherConnector, WeatherReportMailProvider mailProvider) {
        this.weatherConnector = weatherConnector;
        this.mailProvider = mailProvider;
    }

    public void checkWeatherAndSendMailWithTemperature(String location) {

        log("Fetching weather for " + location);
        Weather weather = weatherConnector.weather(location);
        log("Received weather for %s: %s".formatted(location, weather));

        log("Sending email with weather report for " + location);
        mailProvider.sendMail(weather);
        log("Sent email with weather report for " + location);
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}
