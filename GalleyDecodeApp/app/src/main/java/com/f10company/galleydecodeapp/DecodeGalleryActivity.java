package com.f10company.galleydecodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

public class DecodeGalleryActivity extends AppCompatActivity {

    CropImageView cropImageView;
    Button choose,exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_gallery);

        cropImageView = (CropImageView) findViewById(R.id.cropImageView1);
        choose = (Button)findViewById(R.id.choose);
        exit = (Button)findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
