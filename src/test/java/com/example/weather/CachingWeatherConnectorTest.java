package com.example.weather;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class CachingWeatherConnectorTest {

    @Test
    void returnsResultOfTheWrappedConnector() {
        // Given
        var wrappedConnector = mock(WeatherConnector.class);
        when(wrappedConnector.weather(any())).thenReturn(new Weather("X", 1.0));
        var cachingConnector = new CachingWeatherConnector(wrappedConnector);

        // When
        var result = cachingConnector.weather("X");

        // Then
        assertThat(result.location()).isEqualTo("X");
        assertThat(result.temp()).isEqualTo(1.0);
    }

    @Test
    void guaranteesOnlyOneMeasurementIsMadeForEachLocation() {
        // Given
        var wrappedConnector = mock(WeatherConnector.class);
        when(wrappedConnector.weather(any())).thenReturn(new Weather("X", 1.0));
        var cachingConnector = new CachingWeatherConnector(wrappedConnector);

        // When
        cachingConnector.weather("A");
        cachingConnector.weather("B");
        cachingConnector.weather("B");
        cachingConnector.weather("A");
        cachingConnector.weather("C");
        cachingConnector.weather("A");

        //Then
        verify(wrappedConnector, times(1)).weather("A");
        verify(wrappedConnector, times(1)).weather("B");
        verify(wrappedConnector, times(1)).weather("C");
        verifyNoMoreInteractions(wrappedConnector);
    }
}
