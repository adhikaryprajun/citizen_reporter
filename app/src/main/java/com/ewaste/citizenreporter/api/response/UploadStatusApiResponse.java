package com.ewaste.citizenreporter.api.response;

import com.ewaste.citizenreporter.api.models.ApiResponse;
import com.ewaste.citizenreporter.api.models.Upload;

import java.util.ArrayList;

public class UploadStatusApiResponse {

    private ApiResponse response;
    private ArrayList<Upload> uploads;

    public UploadStatusApiResponse(ApiResponse response, ArrayList<Upload> uploads) {
        this.response = response;
        this.uploads = uploads;
    }

    public ApiResponse getResponse() {
        return response;
    }

    public ArrayList<Upload> getUploads() {
        return uploads;
    }

    public ArrayList<Upload> getUserActiveUploads(String userId) {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if((!currentUpload.getStatus().equals("3")) && currentUpload.getUserId().equals(userId)) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;
    }

    public ArrayList<Upload> getUserClosedUploads(String userId) {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if((currentUpload.getStatus().equals("3")) && currentUpload.getUserId().equals(userId)) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;
    }

    public ArrayList<Upload> getAllActiveUploads() {
        int length = uploads.size();
        ArrayList<Upload> activeUploads = new ArrayList<>();
        for(int i=0;i<length;i++){
            Upload currentUpload = uploads.get(i);
            if(!currentUpload.getStatus().equals("3")) {
                activeUploads.add(currentUpload);
            }
        }
        return activeUploads;

    }
}
