package com.ewaste.citizenreporter.api.response;


import com.ewaste.citizenreporter.api.models.ApiResponse;

public class RegisterApiResponse {

    private ApiResponse response;

    public RegisterApiResponse(ApiResponse response) {
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }

}
