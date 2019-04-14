package com.ewaste.citizenreporter.api;

public interface ApiProtocol {

    void makeRequest();
    void parseResponse(String response);
    void handleResponse();
}
