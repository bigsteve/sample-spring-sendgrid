package com.example.email.httpclients;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;

public interface HttpClient  extends HttpRequest, HttpOutputMessage {
    
    /**
     * 
     * @param uri
     * @param httpMethod
     * @return
     * @throws IOException
     */
    ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException;
    
    /**
     * Execute the {@link HttpClient} request, resulting in a {@link ClientHttpResponse}.
     * @return the {@link ClientHttpResponse} response of the execution
     * @throws IOException in case of I/O errors
     */
    ClientHttpResponse execute() throws IOException;
}