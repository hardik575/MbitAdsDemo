package com.screen;

import android.view.View;

import androidx.lifecycle.MutableLiveData;


import com.google.android.gms.ads.nativead.NativeAd;

public class StorageCommon {
    public MutableLiveData<View> nativeAdsLanguage = new MutableLiveData<>();
    public MutableLiveData<View> nativeAdsLanguageSecond = new MutableLiveData<>();

    public NativeAd nativeAdHigh;
    public NativeAd nativeAdMedium;
    public NativeAd nativeAdNormal;
}
