package com.ewaste.citizenreporter.api;

import com.ewaste.citizenreporter.api.response.UploadStatusApiResponse;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public interface ApiCallback {

    void apiCompleted(UploadStatusApiResponse response);
}
