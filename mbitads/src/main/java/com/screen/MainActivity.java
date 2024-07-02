package com.ads;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mbitadsdk.MbitAds;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyApplication.getInstance().initAppOpen();

        MbitAds.getInstance().initAppOpen();
    }
}