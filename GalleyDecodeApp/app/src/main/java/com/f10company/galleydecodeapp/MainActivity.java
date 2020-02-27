package com.f10company.galleydecodeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private static final double BUTTON_HEIGHT_PERCENT = 0.4135;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (794(디자이너 그림 상 버튼높이픽셀)/1920(디자이너의그림 화면픽셀1920))
    private static final double BUTTON_WIDTH_PERCENT = 0.7351;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (794(디자이너 그림 상 버튼가로픽셀)/1080(디자이너의그림 화면픽셀1080))
    private static final double BUTTON_BOTTOM_MARGIN_PERCENT = 0.0844;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (162(디자이너 그림 상 버튼밑마진)/1920(디자이너의그림 화면픽셀1920))

    private static final double TEXT_HEIGHT_PERCENT = 0.2541;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (488(디자이너 그림 상 버튼높이픽셀)/1920(디자이너의그림 화면픽셀1920))
    private static final double TEXT_WIDTH_PERCENT = 0.9074;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (980(디자이너 그림 상 버튼가로픽셀)/1080(디자이너의그림 화면픽셀1080))
    private static final double TEXT_TOP_MARGIN_PERCENT = 0.1755;
    // 이 상수는 버튼이 화면 높이의 비율을 나타냄. (337(디자이너 그림 상 버튼밑마진)/1920(디자이너의그림 화면픽셀1920))

    protected final static String INTENT_CODE_STRING = "codeString";
    //intent 코드 주고받는 key

    TextView textView;
    Button button;
    private AdView mAdView;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textview);
        button = (Button)findViewById(R.id.button);
        //mAdView = (AdView)findViewById(R.id.adView);
        relativeLayout = (RelativeLayout)findViewById(R.id.mainRelativeLayout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        //mAdView.setAdSize(new AdSize());
        //AdRequest adRequest = new AdRequest.Builder().build();

        //AdSize adSize = getAdSize();
        /*mAdView.setAdSize(adSize);
        mAdView.loadAd(adRequest);*/
        mAdView = new AdView(this);
        mAdView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        rp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mAdView.setLayoutParams(rp);
        relativeLayout.addView(mAdView);
        loadBanner();

        setSize();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DecodeGalleryActivity.class);

                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        mAdView.setAdSize(adSize);

        // Step 5 - Start loading the ad in the background.
        mAdView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String str = data.getExtras().getString(INTENT_CODE_STRING);
                textView.setText(str);
            }
        }
    }

    void setSize()
    {
        double deviceWidth = getResources().getDisplayMetrics().widthPixels; //휴대폰 디스플레이 가로픽셀
        double deviceHeight = getResources().getDisplayMetrics().heightPixels; //휴대폰 디스플레이 세로픽셀


        ///////////////////////////////버튼 동적 레이아웃 적용///////////////////////////////////
        double buttonBottomMargin = deviceHeight*BUTTON_BOTTOM_MARGIN_PERCENT;//휴대폰 디스플레이에 기반한 버튼 bottom 마진

        RelativeLayout.LayoutParams rp_buttonLayout = new RelativeLayout.LayoutParams(
                (int)(deviceWidth*BUTTON_WIDTH_PERCENT),(int)(deviceHeight*BUTTON_HEIGHT_PERCENT));
        rp_buttonLayout.setMargins(0,0,0,(int)buttonBottomMargin);
        rp_buttonLayout.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM );
        rp_buttonLayout.addRule( RelativeLayout.CENTER_HORIZONTAL );
        button.setLayoutParams(rp_buttonLayout);
        //////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////버튼 동적 레이아웃 적용///////////////////////////////////
        double textTopMargin = deviceHeight*TEXT_TOP_MARGIN_PERCENT;//휴대폰 디스플레이에 기반한 버튼 bottom 마진

        RelativeLayout.LayoutParams rp_textLayout = new RelativeLayout.LayoutParams(
                (int)(deviceWidth*TEXT_WIDTH_PERCENT),(int)(deviceHeight*TEXT_HEIGHT_PERCENT));
        rp_textLayout.setMargins(0,(int)textTopMargin,0,0);
        rp_textLayout.addRule( RelativeLayout.ALIGN_PARENT_TOP );
        rp_textLayout.addRule( RelativeLayout.CENTER_HORIZONTAL );
        textView.setLayoutParams(rp_textLayout);
        //////////////////////////////////////////////////////////////////////////////////


    }
}
