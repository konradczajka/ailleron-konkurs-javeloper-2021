package com.example.weather;

@FunctionalInterface
interface MailProvider {

    void sendMail(final String location, final String weatherDatum, final String datum);
}
