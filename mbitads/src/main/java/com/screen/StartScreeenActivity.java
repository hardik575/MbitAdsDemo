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

public class StartScreeenActivity extends AppCompatActivity {

    RelativeLayout rlNativeAdsContainerForCardTheme;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_start_screeen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        MbitAds.getInstance().appOpenManagerSplash.isAppResumeEnabled = true;

        shimmerFrameLayout = findViewById(R.id.shimmer_container_banner);
        rlNativeAdsContainerForCardTheme = findViewById(R.id.rlNativeAdsContainerForCardTheme);

        MbitAds.getInstance().ShowStartScreen(this, "0", "", shimmerFrameLayout, rlNativeAdsContainerForCardTheme, new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });
    }

    public void next(View view) {

        Intent intent = new Intent(StartScreeenActivity.this, MainActivity.class);
        startActivity(intent);


    }
}