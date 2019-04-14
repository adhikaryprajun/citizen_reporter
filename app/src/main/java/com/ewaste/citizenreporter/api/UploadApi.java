package com.ewaste.citizenreporter.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ewaste.citizenreporter.UploadActivity;
import com.ewaste.citizenreporter.api.models.ApiResponse;
import com.ewaste.citizenreporter.api.models.Session;
import com.ewaste.citizenreporter.api.models.User;
import com.ewaste.citizenreporter.api.response.LoginApiResponse;
import com.ewaste.citizenreporter.api.response.UploadApiResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prajunadhikary on 13/04/19.
 */

public class UploadApi {

    private final String URL = "http://goodrichpestcontrol.com/ewaste/api/upload_api.php";

    private final int METHOD = Request.Method.POST;

    // API Parameters
    private final String KEY_SESSION_HASH = "session_hash";
    private final String KEY_DESCRIPTION = "description";
//    private final String KEY_ADDRESS = "addresss";
    private final String KEY_TYPE = "type";

    private String session_hash;
    private byte[] picture;
    private String description;
    private String address;
    private String type;

    private Context context;

    private UploadApiResponse loginApiResponse;
    public ProgressDialog d;

    public UploadApi(Context context, String session_hash, byte[] picture, String description, String type, ProgressDialog d) {
        this.context = context;
        this.session_hash = session_hash;
        this.picture = picture;
        this.description = description;
        this.type = type;
        this.d=d;
    }

    public void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(METHOD, URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    parseResponse(resultResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                d.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_SESSION_HASH,session_hash);
                params.put("description", description);
//                params.put("address", address);
                params.put("type", type);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                params.put("picture", new DataPart(imagename+".jpg", picture, "image/jpg"));
                return params;
            }
        };

        d.setMessage("Loading...");
        d.show();
        queue.add(multipartRequest);
    }

    void parseResponse(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            String code = obj.getString(ApiResponse.KEY_CODE);
            String message = obj.getString(ApiResponse.KEY_MESSAGE);
            ApiResponse r = new ApiResponse(code, message);
            if (r.isValid()) {
                loginApiResponse = new UploadApiResponse(r);
            } else {
                loginApiResponse = new UploadApiResponse(r);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Log.d("UPLOAD",  "Parsing error");
        }
        d.dismiss();
        handleLoginApiResponse();
    }

    void handleLoginApiResponse() {
        Toast.makeText(context, "Uploaded Be Happy!!!", Toast.LENGTH_SHORT).show();
        if(loginApiResponse.getResponse().isValid()) {

        } else {
            Log.d("UPLOAD", "Not able to login");
        }

    }
}
