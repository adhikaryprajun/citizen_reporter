package com.ewaste.citizenreporter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.ewaste.citizenreporter.api.UploadApi;
import com.ewaste.citizenreporter.api.models.Session;

import java.io.ByteArrayOutputStream;

public class UploadActivity extends AppCompatActivity {

    private ImageView ivUploadImage;
    private EditText etUploadDescription;
    private RadioButton rbtnUploadGCC, rbtnUploadPot;
    private Button btnUpload;

    private final int CAMERA_PIC_REQUEST = 2;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        session = (Session) getIntent().getSerializableExtra(UserActivity.KEY_BUNDLE_SESSION);

        ivUploadImage = findViewById(R.id.ivUploadImage);
        etUploadDescription = findViewById(R.id.etUploadDescription);
//        etUploadAddress = findViewById(R.id.etUploadAddress);
        rbtnUploadGCC = findViewById(R.id.rbtnUploadGCC);
        rbtnUploadPot = findViewById(R.id.rbtnUploadPot);
        btnUpload = findViewById(R.id.btnUpload);

        ivUploadImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Bitmap bitmap = ((BitmapDrawable) ivUploadImage.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);

                String uploadType = "0";
                if (rbtnUploadGCC.isSelected()) {
                    uploadType="0";
                } else {
                    uploadType="1";
                }

                UploadApi uploadApi = new UploadApi(getApplicationContext(), session.getHashValue(), byteArrayOutputStream.toByteArray(), etUploadDescription.getText().toString(), uploadType, new ProgressDialog(UploadActivity.this));
                uploadApi.makeRequest();
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ivUploadImage.setImageBitmap(image);
        }
    }
}
