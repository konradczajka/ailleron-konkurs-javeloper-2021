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
        var weather = new Weather("X", 1.0);
        var connector = mock(WeatherConnector.class);
        when(connector.weather(any())).thenReturn(weather);
        var emailProvider = mock(WeatherReportMailProvider.class);

        // When
        new WeatherReporter(connector, emailProvider).checkWeatherAndSendMailWithTemperature("X");

        // Then
        verify(connector).weather("X");
        verify(emailProvider).sendMail(weather);
        verifyNoMoreInteractions(connector, emailProvider);
    }
}
