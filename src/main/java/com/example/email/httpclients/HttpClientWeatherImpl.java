package com.example.email.httpclients;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.example.email.model.LocalWeather;

@Component
public class HttpClientWeatherImpl implements HttpClient{
    
    @Autowired
    LocalWeather localWeather;

    public HttpClientWeatherImpl setLocation(String location) {
        localWeather.setLocation(location);
        return this;
    }

    @Bean(name="weatherData")
    public String mockHttpCall() {
        String weatherFromAPI = "Sunny day today.";
        return localWeather.setWeather(weatherFromAPI)
        .getFormattedWeather();
    }

    @Override
    public String getMethodValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public URI getURI() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HttpHeaders getHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OutputStream getBody() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientHttpResponse execute() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    

}
