package com.example.weather;

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
