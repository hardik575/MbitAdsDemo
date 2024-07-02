package com.screen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.ads.mbitadsdk.MbitAds;
import com.ads.mbitadsdk.intad.IntCallback;
import com.ads.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.mbitsdk.R;

public class SplashActivity extends AppCompatActivity {

    private long TIMEOUT_SPLASH = 30000;
    private long TIME_DELAY_SPLASH = 5000;
    private boolean isFirstRunApp = true;
    private final String typeAdsSplash = "intera";

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

        MbitAds.getInstance().intisialAdMob(SplashActivity.this);
        MbitAds.getInstance().SetAllAdOnOff(false);
        MbitAds.getInstance().setUniversalClickCount(3);




        MbitAds.getInstance().PreLoadingNativeCustom(SplashActivity.this, R.layout.native_ad_view_large_card, this, true, "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
            @Override
            public void onNativeAdLoadedSucessFully(View view) {
                MyApplication.getApplication().getStorageCommon().nativeAdsLanguage.setValue(view);
            }

            @Override
            public void onNativeAdFailedToLoad() {

            }
        });

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MbitAds.getInstance().PreLoadingNativeCustom(SplashActivity.this, R.layout.native_ad_view_large_card, SplashActivity.this, true, "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
                    @Override
                    public void onNativeAdLoadedSucessFully(View view) {
                        MyApplication.getApplication().getStorageCommon().nativeAdsLanguageSecond.setValue(view);
                    }

                    @Override
                    public void onNativeAdFailedToLoad() {

                    }
                });

            }
        }, 2000);


        MbitAds.getInstance().initAppOpenSplash(SplashActivity.this, "ca-app-pub-3940256099942544/9257395921", new IntCallback() {
            @Override
            public void adIntClose() {


                navigateToNextScreen();

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


    private void navigateToNextScreen() {
        if (isDestroyed() || isFinishing()) {
            return;
        }
        Intent intent = new Intent(SplashActivity.this, LangugeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}