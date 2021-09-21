package com.example.weather;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class WeatherReporterTest {

    @Test
    void fetchesWeatherDataAndSendsAMailReportWithTheResult() {
        // Given
        var xWeather = new Weather("X", 1.0);
        var yWeather = new Weather("Y", 1.0);
        var connector = mock(WeatherConnector.class);
        when(connector.weather("X")).thenReturn(xWeather);
        when(connector.weather("Y")).thenReturn(yWeather);
        var emailProvider = mock(WeatherReportMailProvider.class);

        // When
        new WeatherReporter(connector, emailProvider).prepareAndSendReportFor(new String[]{"X", "Y"});

        // Then
        verify(connector).weather("X");
        verify(connector).weather("Y");
        verify(emailProvider).sendMail(xWeather);
        verify(emailProvider).sendMail(yWeather);
        verifyNoMoreInteractions(connector, emailProvider);
    }
}
