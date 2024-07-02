package com.ads.mbitadsdk.nativead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ads.mbitadsdk.AdUtils;
import com.ads.mbitadsdk.nativetemplates.NativeTemplateStyle;
import com.ads.mbitadsdk.nativetemplates.TemplateViewForHome;
import com.ads.mbitsdkmodel.R;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.ads.mbitadsdk.Log;


public class NativeMediumTemplate {
    View viewNative;
    Context mContext;
    int mediationFlag;
    String admobNativeAdId;

    //FB Native Ad Conteneir
    private NativeAdLayout nativeAdLayout;
    private NativeBannerAd nativeBannerAd;
    private LinearLayout adView;
    private NativeAdLoadCallback nativeAdLoadCallback;


    public boolean isNativeAdsLoaded;
    public String TAG = "HD";
    LifecycleOwner lifecycleOwner;


    public NativeMediumTemplate(Context mContext, LifecycleOwner lifecycleOwner, boolean isReloadAds, String admobNativeAdId, NativeAdLoadCallback nativeAdLoadCallback) {
        this.mContext = mContext;
        isNativeAdsLoaded = false;
        this.admobNativeAdId = admobNativeAdId;
        this.nativeAdLoadCallback = nativeAdLoadCallback;
        this.lifecycleOwner = lifecycleOwner;

        if (isReloadAds) {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            registerLifecycleObserver();
        } else {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            loadAdTamplete();
        }
    }


    TemplateViewForHome template;

    public boolean isReloadAds = true;

    public void loadAdTamplete() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        viewNative = inflater.inflate(R.layout.native_ad_tmplate_for_home, null);

        AdLoader adLoader = new AdLoader.Builder(mContext, admobNativeAdId).withAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d(TAG, "Failed to Load : " + loadAdError.getMessage());
                nativeAdLoadCallback.nativeAdFailedToLoad("admob_native_ad_failed_");
            }


            @Override
            public void onAdClicked() {
                nativeAdLoadCallback.nativeAdClickError("admob_native_ad_click_");
                // Log the click event or other custom behavior.
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                nativeAdLoadCallback.nativeAdLoadedSuccessfully("admob_native_ad_load_");
                Log.d(TAG, "Load : Native ");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

        }).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                isNativeAdsLoaded = true;
                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                template = viewNative.findViewById(R.id.my_template);
                template.setStyles(styles);
                if (template != null) {
                    Log.d(TAG, "Not Null TemplateView");
                }
                template.setNativeAd(nativeAd);
            }
        }).build();
        adLoader.loadAd(AdUtils.getInstance().requestMediationAd(mContext));
    }


    public View getNativeTamplate() {

        if (viewNative.getParent() != null) {
            ((ViewGroup) viewNative.getParent()).removeView(viewNative);
        }
        return viewNative;
    }


    private void registerLifecycleObserver() {
        lifecycleOwner.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_RESUME) {

                Log.d(TAG, "ON_RESUME: " + event);
                loadAdTamplete();
            }
        });
    }
}
