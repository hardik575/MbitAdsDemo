package com.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.ads.mbitadsdk.Log;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.mbitsdk.R;

public class LangugeActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayout;
    FrameLayout rlNativeAdsContainerForCardTheme;
    Button loadSecondAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languge);

        shimmerFrameLayout = findViewById(R.id.shimmer_container_banner);
        rlNativeAdsContainerForCardTheme = findViewById(R.id.rlNativeAdsContainerForCardTheme);

        runOnUiThread(new Runnable() {
            public void run() {
                if (MyApplication.getApplication().getStorageCommon().nativeAdsLanguage.getValue() != null) {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                    rlNativeAdsContainerForCardTheme.removeAllViews();
                    rlNativeAdsContainerForCardTheme.addView(MyApplication.getApplication().getStorageCommon().nativeAdsLanguage.getValue());
                } else {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                }
            }
        });

        loadSecondAds = findViewById(R.id.loadSecondAds);

        loadSecondAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            if (MyApplication.getApplication().getStorageCommon().nativeAdsLanguageSecond.getValue() != null) {
                                Log.e("LanguegSecond", "==>" + MyApplication.getApplication().getStorageCommon().nativeAdsLanguageSecond.getValue());
                                shimmerFrameLayout.setVisibility(View.GONE);
                                rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                                rlNativeAdsContainerForCardTheme.removeAllViews();
                                rlNativeAdsContainerForCardTheme.addView(MyApplication.getApplication().getStorageCommon().nativeAdsLanguageSecond.getValue());
                            } else {
                                shimmerFrameLayout.setVisibility(View.GONE);
                                rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }
                    }
                });


            }
        });


    }


    public void loadNextActivity(View view) {

        Intent intent = new Intent(LangugeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}