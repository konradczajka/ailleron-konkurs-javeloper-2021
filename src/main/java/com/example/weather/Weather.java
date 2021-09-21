package com.example.weather;

import java.util.Objects;

final class Weather {

    private final String location;
    private final double temp;

    public Weather(String location, double temp) {
        this.location = location;
        this.temp = temp;
    }

    public String getLocation() {
        return location;
    }

    public double getTemp() {
        return temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weather weather = (Weather) o;
        return Double.compare(weather.temp, temp) == 0 && Objects.equals(location, weather.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, temp);
    }

    @Override
    public String toString() {
        return "Weather{" +
               "location='" + location + '\'' +
               ", temp=" + temp +
               '}';
    }
}
