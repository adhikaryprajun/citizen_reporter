package com.ewaste.citizenreporter.util;

import com.ewaste.citizenreporter.api.models.Upload;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by prajunadhikary on 14/04/19.
 */

public class UploadController {

    private ArrayList<Upload> uploads;

    public UploadController(ArrayList<Upload> uploads) {
        this.uploads = uploads;
    }

    public ArrayList<Upload> getUploads() {
        return uploads;
    }

    public void setUploads(ArrayList<Upload> uploads) {
        this.uploads = uploads;
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
