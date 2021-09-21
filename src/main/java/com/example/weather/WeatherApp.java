package com.example.weather;

public class WeatherApp {

    static final String[] locations = new String[]{"Cracow", "Warsaw", "London", "Lodz", "Kielce", "Tokyo", "NewYork", "Buenos Aires", "Rzeszow"};

    public static void main(String[] args) {

        WeatherConnector dummyConnector = location -> new Weather(location, 20.0);
        WeatherReportMailProvider dummyMailProvider = weather -> {};
        WeatherReporter provider = new WeatherReporter(
                new CachingWeatherConnector(dummyConnector),
                dummyMailProvider);

        provider.prepareAndSendReportFor(locations);
    }
}