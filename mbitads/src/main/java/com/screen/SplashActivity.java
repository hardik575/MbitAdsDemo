package com.ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mbitadsdk.MbitAds;
import com.mbitadsdk.intad.IntCallback;
import com.mbitadsdk.nativead.NativeAdLoadSucessCallBack;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MbitAds.getInstance().setAllAdOnOff("on");

        MbitAds.getInstance().LoadNativeLangugeSelect(SplashActivity.this, "0", "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });




        MbitAds.getInstance().LoadNativeLangugeSelectTwo(SplashActivity.this, "0", "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
            @Override
            public void nativeAdLoaded(View view) {

            }

            @Override
            public void nativeAdFailedToLoad() {

            }
        });

        MbitAds.getInstance().initAppOpenSplash(SplashActivity.this, "ca-app-pub-3940256099942544/9257395921", new IntCallback() {
            @Override
            public void adIntClose() {

                Intent intent=new Intent(SplashActivity.this,LangugeActivity.class);
                startActivity(intent);

            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {

            }

            @Override
            public void adIntClick() {

            }

            @Override
            public void adIntLoaded() {

            }
        });
    }
}