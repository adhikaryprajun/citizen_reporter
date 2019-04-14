package com.ewaste.citizenreporter.api.response;

import com.ewaste.citizenreporter.api.models.ApiResponse;

public class LogoutApiResponse {

    private ApiResponse response;
    public LogoutApiResponse(ApiResponse response) {
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }
}
