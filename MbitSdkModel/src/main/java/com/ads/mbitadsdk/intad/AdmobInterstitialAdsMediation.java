package com.ads.mbitadsdk.intad;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ads.mbitadsdk.AdUtils;
import com.ads.mbitadsdk.Log;
import com.ads.mbitadsdk.MbitAds;
import com.ads.mbitadsdk.MyApp;

public class AdmobInterstitialAdsMediation {
    public InterstitialAd mInterstitialAdApp;
    private Context context;
    private String admobId;
    private IntCallback intCallback;
    private String TAG = "IntMediation";


    public AdmobInterstitialAdsMediation(Context cntx, String admobInterstitialId, IntCallback intCallback) {
        context = cntx;
        admobId = admobInterstitialId;
        this.intCallback = intCallback;
        LoadInterstitialAd();
    }

    public void LoadInterstitialAd() {

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, admobId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAdApp = interstitialAd;
                intCallback.adIntLoaded();

                mInterstitialAdApp.setOnPaidEventListener(new OnPaidEventListener() {

                    @Override
                    public void onPaidEvent(@NonNull AdValue adValue) {

                        Bundle params = new Bundle();

                        params.putLong("valuemicros", adValue.getValueMicros());
                        params.putString("currency", adValue.getCurrencyCode());
                        params.putInt("precision", adValue.getPrecisionType());
                        params.putString("adunitid", admobId);
                        params.putString("network", mInterstitialAdApp.getResponseInfo().getMediationAdapterClassName());


                    }
                });

                Log.i(TAG, "onAdLoaded");
                new AdmobIntFirebaseAnalyticalEvent(context).LoadIntAdEvent(admobId);
                Log.d("IntMediation", "Admob OnAdLoaded");

                mInterstitialAdApp.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        // Called when fullscreen content is dismissed.
                        mInterstitialAdApp = null;
                        MyApp.getInstance().EventRegister("int_onAdFailedToShowFullScreenContent", new Bundle());
                        if (MbitAds.getInstance().appOpenManager != null) {
                            MbitAds.getInstance().appOpenManager.enableAppResume();
                        }
                        AdUtils.getInstance().isReadyPlaceToAppOpenAds = "1";
                        new AdmobIntFirebaseAnalyticalEvent(context).CloseIntAdEvent(admobId);
                        intCallback.adIntClose();
                        Log.d("IntMediation", "Failed To Open Int");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        AdUtils.getInstance().isReadyPlaceToAppOpenAds = "0";
                        if (MbitAds.getInstance().appOpenManager != null) {
                            MbitAds.getInstance().appOpenManager.disableAppResume();
                        }
                        // Called when fullscreen content failed to show.
                        Log.d("IntMediation", "Int Ads Open");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.

                        mInterstitialAdApp = null;
                        AdUtils.getInstance().isReadyPlaceToAppOpenAds = "1";
                        if (MbitAds.getInstance().appOpenManager != null) {
                            MbitAds.getInstance().appOpenManager.disableAppResume();
                        }
                        new AdmobIntFirebaseAnalyticalEvent(context).CloseIntAdEvent(admobId);
                        intCallback.adIntClose();
                        Log.d("IntMediation", "Admob onAdClosed");
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i(TAG, loadAdError.getMessage());
                mInterstitialAdApp = null;
                intCallback.adIntFailedToLoad();

            }
        });


    }


    public void ShowMediationAds(Activity mcurrentActivity) {
        boolean showAds = true;
        if (context == null) {
            intCallback.adIntClose();
            return;
        }
        if (mInterstitialAdApp != null) {
            mInterstitialAdApp.show(mcurrentActivity);
        } else {
            intCallback.adIntClose();
        }

    }

    public boolean isAdsAvailable = false;

    public boolean isAdsAvailable() {
        if (mInterstitialAdApp != null) {
            isAdsAvailable = true;
        } else {
            isAdsAvailable = false;
        }
        return isAdsAvailable;
    }
}
