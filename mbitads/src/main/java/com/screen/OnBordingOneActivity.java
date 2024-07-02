package com.ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mbitadsdk.MbitAds;
import com.mbitadsdk.nativead.NativeAdLoadSucessCallBack;

public class OnBordingOneActivity extends AppCompatActivity {


    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout rlNativeAdsContainerForCardTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_bording_one);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shimmerFrameLayout = findViewById(R.id.shimmer_container_banner);
        rlNativeAdsContainerForCardTheme = findViewById(R.id.rlNativeAdsContainerForCardTheme);

        MbitAds.getInstance().ShowOnBording1(this, "0", "", shimmerFrameLayout, rlNativeAdsContainerForCardTheme, new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });
    }

    public void next(View view) {

        MbitAds.getInstance().LoadNativeOnBordingScreenOne(this, "0", MyApplication.nativeid, new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });
        Intent intent = new Intent(OnBordingOneActivity.this, OnBordingTwoActivity.class);
        startActivity(intent);
    }
}