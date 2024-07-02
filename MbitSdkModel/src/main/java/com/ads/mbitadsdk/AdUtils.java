package com.ads.mbitadsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;

public class AdUtils {

    private static AdUtils instance = new AdUtils();

    //Int Ads Id
    public String mAdmobIntIdSplash, mAdmobIntIdHomeThemeClick, mAdmobIntIdBackFromPlayer,
            mAdmobIntIdAfterCreateVideo;

    public String mFanIntIdForHomeThemeClick, mFanIntIdForAfterCreateVideo, mFanIntIdForCropPhoto;


    public String isReadyPlaceToAppOpenAds = "0";
    public boolean isSpashScreenAdLoad;
    public boolean isBannerClick;


    public static AdUtils getInstance() {
        return instance;
    }


    public AdUtils() {

    }

    public AdRequest requestMediationAd(Context mContext) {
        AdRequest adRequest = new AdRequest.Builder().build();
        return adRequest;
    }

    @SuppressLint("MissingPermission")
    public void EventRegister(String event, Bundle bundle) {
        // MyApplication.getInstance().EventRegister(event, bundle);
    }
}
