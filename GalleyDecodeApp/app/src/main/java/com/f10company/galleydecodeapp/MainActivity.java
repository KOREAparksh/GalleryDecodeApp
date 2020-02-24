package com.f10company.galleydecodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //initial commit for branch named parksh
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textview);
        button = (Button)findViewById(R.id.button);



        /*
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        startActivityForResult(intent, 1);
        */
    }

    void setSize()
    {
        float deviceWidth = getResources().getDisplayMetrics().widthPixels; //휴대폰 디스플레이 가로픽셀
        float deviceHeight = getResources().getDisplayMetrics().heightPixels; //휴대폰 디스플레이 세로픽셀

        RelativeLayout.LayoutParams buttonLayout = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        

    }

    int buttonMargin = (int) ((float) 36 / (float) 1080 * getResources().getDisplayMetrics().widthPixels); // 휴대폰 가로길이의 1080분의 36 크기
    int buttonSize = (int) ((float) 88 / (float) 1080 * getResources().getDisplayMetrics().widthPixels); // 휴대폰 가로길이의 1080분의 88 크기

    RelativeLayout.LayoutParams lp_exclamation = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
}
