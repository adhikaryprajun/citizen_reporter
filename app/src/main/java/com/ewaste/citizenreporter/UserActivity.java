package com.ewaste.citizenreporter;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.ewaste.citizenreporter.adapter.UploadListAdapter;
import com.ewaste.citizenreporter.api.ApiCallback;
import com.ewaste.citizenreporter.api.LogoutApi;
import com.ewaste.citizenreporter.api.UploadStatusApi;
import com.ewaste.citizenreporter.api.models.Session;
import com.ewaste.citizenreporter.api.models.Upload;
import com.ewaste.citizenreporter.api.response.UploadStatusApiResponse;
import com.ewaste.citizenreporter.util.UploadController;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    public static int i;

    public static String KEY_BUNDLE_SESSION = "session";

    private ListView lvUploads;
    public static UploadListAdapter uploadListAdapter;

    private Session session;
    private String uploadType;

    private ArrayList<Upload> uploads;
    private UploadStatusApiResponse controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        session = (Session) getIntent().getSerializableExtra(KEY_BUNDLE_SESSION);

        Log.d("SESSION", session.getUserId());

        lvUploads = (ListView) findViewById(R.id.lvUploads);

        uploads = new ArrayList<>();
        uploadListAdapter = new UploadListAdapter(getApplicationContext(), session, uploads);
        lvUploads.setAdapter(uploadListAdapter);

        Log.d("HASH", session.getHashValue());
        // GET all uploads
        UploadStatusApi uploadStatusApi = new UploadStatusApi(getApplicationContext(), session.getHashValue(), "0", "0", new ApiCallback() {
            @Override
            public void apiCompleted(UploadStatusApiResponse response) {
//                controller = new UploadController(response.getUploads());
                controller = response;
                uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getUserActiveUploads(session.getUserId()));
                lvUploads.setAdapter(uploadListAdapter);
            }
        });
        uploadStatusApi.makeRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        i=item.getItemId();
        if(item.getItemId()==R.id.user_logout) {
            LogoutApi logoutApi = new LogoutApi(getApplicationContext(), session.getHashValue());
            logoutApi.makeRequest();
        } else if (item.getItemId()==R.id.user_history) {
            uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getUserClosedUploads(session.getUserId()));
            lvUploads.setAdapter(uploadListAdapter);
        } else if(item.getItemId()==R.id.user_report) {
            uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getUserActiveUploads(session.getUserId()));
            lvUploads.setAdapter(uploadListAdapter);
        } else if(item.getItemId()==R.id.citizen_report) {
            uploadListAdapter = new UploadListAdapter(getApplicationContext(),session, controller.getAllActiveUploads());
            lvUploads.setAdapter(uploadListAdapter);
        } else if(item.getItemId()==R.id.new_upload) {
            Intent i1 = new Intent(getApplicationContext(), UploadActivity.class);
            i1.putExtra(KEY_BUNDLE_SESSION, session);
            startActivity(i1);
        }
        return super.onOptionsItemSelected(item);
    }
}
