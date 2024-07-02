package com.ads.mbitadsdk.nativead;

public interface NativeAdLoadCallback {
     void nativeAdLoadedSuccessfully(String eventName);
     void nativeAdFailedToLoad(String msg);
     void nativeAdClick();
     void nativeAdClickError(String msg);


}
