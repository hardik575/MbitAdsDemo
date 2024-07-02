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

public class LangugeActivity extends AppCompatActivity {

    ShimmerFrameLayout shimmerFrameLayout;
    RelativeLayout rlNativeAdsContainerForCardTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_languge);

        shimmerFrameLayout = findViewById(R.id.shimmer_container_banner);
        rlNativeAdsContainerForCardTheme = findViewById(R.id.rlNativeAdsContainerForCardTheme);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MbitAds.getInstance().LoadNativeOnBordingScreenOne(LangugeActivity.this, "0", "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });


        MbitAds.getInstance().ShowLangugeOne(this, "0", shimmerFrameLayout, rlNativeAdsContainerForCardTheme);


    }

    public void loadAds(View view) {
        MbitAds.getInstance().ShowLangugeTwo(this, "0", shimmerFrameLayout, rlNativeAdsContainerForCardTheme);

    }

    public void loadNextActivity(View view) {

        Intent intent = new Intent(LangugeActivity.this, LangugeActivity.class);
        startActivity(intent);

    }
}