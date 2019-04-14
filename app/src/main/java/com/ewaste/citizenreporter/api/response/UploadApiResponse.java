package com.ewaste.citizenreporter.api.response;

import com.ewaste.citizenreporter.api.models.ApiResponse;
import com.ewaste.citizenreporter.api.models.Session;
import com.ewaste.citizenreporter.api.models.User;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class UploadApiResponse {

    private ApiResponse response;

    public UploadApiResponse(ApiResponse response) {
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }

}
