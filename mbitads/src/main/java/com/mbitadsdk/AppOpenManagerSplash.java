package com.mbitadsdk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback;
import com.mbitadsdk.intad.IntCallback;


import java.util.Objects;

public class AppOpenManagerSplash {

    private Activity activity;
    public AppOpenAd appOpenAd = null;
    private AppOpenAdLoadCallback loadCallback;

    public String isAppOpenLoaded;
    public IntCallback intCallback;
    String appOpenId;

    boolean isAppResumeEnabled = true;


    public AppOpenManagerSplash(Activity activity, String id, IntCallback intCallback) {
        isAppOpenLoaded = "2";
        this.activity = activity;
        this.intCallback = intCallback;
        appOpenId = id;

        fetchAd();
    }


    private void fetchAd() {


        android.util.Log.d("AppOpenManagerSplash", "1 App Open Id : " + appOpenId);

        loadCallback = new AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {

                isAppOpenLoaded = "1";
                appOpenAd = ad;
                MyApp.getInstance().EventRegister("Splash_App_Open_Loaded", new Bundle());
                AdUtils.getInstance().isSpashScreenAdLoad = true;
                android.util.Log.d("AppOpenManagerSplash", "onAppOpenAdLoaded : " + ad.getResponseInfo());

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (!AdUtils.getInstance().isBannerClick) {
                        showAdIfAvailable();
                    }
                }, 3000);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                // Handle the error.
                isAppOpenLoaded = "2";
                MyApp.getInstance().EventRegister("splash_scr_view", new Bundle());
                android.util.Log.d("AppOpenManagerSplash", "onAppOpenAdFailedToLoad : " + loadAdError.getMessage());
                appOpenAd = null;
                if (!AdUtils.getInstance().isBannerClick) {
                    AdUtils.getInstance().isSpashScreenAdLoad = true;
                    if (!isAppResumeEnabled) {
                        isAppResumeEnabled = true;
                        intCallback.adIntClose();
//                        FunctionClass.loadHomeScreen(activity);
                    }
                }
            }
        };

        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                activity, appOpenId, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback
        );
    }

    AdsWaitingDailog adsWaitingDailog;

    public void showAdIfAvailable() {

        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        android.util.Log.d("AppOpenManagerSplash", "Will show ad.");
        android.util.Log.d("AppOpenManagerSplash", "Suitable Place For Display AppOpen Ads");

        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                android.util.Log.d("AppOpenManagerSplash", "onAdDismissedFullScreenContent");
                // Set the reference to null so isAdAvailable() returns false.
                MyApp.getInstance().EventRegister("Splash_App_Open_Close", new Bundle());
                appOpenAd = null;
                AdUtils.getInstance().isSpashScreenAdLoad = true;
                if (!isAppResumeEnabled) {
                    isAppResumeEnabled = true;
                    intCallback.adIntClose();
//                    functionClass.loadHomeScreen(activity);
                }
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                MyApp.getInstance().EventRegister("Splash_App_Open_Failed_To_Show", new Bundle());
                appOpenAd = null;
                android.util.Log.d("AppOpenManagerSplash", "onAdFailedToShowFullScreenContent");
                AdUtils.getInstance().isSpashScreenAdLoad = true;
                if (!isAppResumeEnabled) {
                    isAppResumeEnabled = true;
                    intCallback.adIntClose();
//                    functionClass.loadHomeScreen(activity);
                }
            }

            @Override
            public void onAdShowedFullScreenContent() {
            }
        };
        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
        if (!isAppResumeEnabled) {
            // MyApplication.getInstance().LoadHighFloorNativeVideoLangSelectTwo();
            try {
                adsWaitingDailog = new AdsWaitingDailog();
                adsWaitingDailog.show(
                        ((AppCompatActivity) activity).getSupportFragmentManager(),
                        "adsWaitingDailog"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adsWaitingDailog != null && adsWaitingDailog.isVisible()) {
                                adsWaitingDailog.dismiss();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (appOpenAd != null) {
                    appOpenAd.show(activity);
                }
            }, 1500);
        }
    }
}
