package com.mbitadsdk.nativead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.mbitadsdk.AdUtils;
import com.mbitadsdk.Log;
import com.mbitsdk.R;


public class NativeCustomTemplate {

    private View clickable, viewNative;
    public Context mContext;

    String admobNativeAdId;

    private NativeAdLoadCallback nativeAdLoadCallback;
    private String ourAdmobNativeId;

    public boolean isNativeAdsAviable;
    public String TAG = "MediumNative";

    int customLayout;
    LifecycleOwner lifecycleOwner;

    public NativeCustomTemplate(Context mContext, int layout, LifecycleOwner lifecycleOwner, boolean isReloadAds, String admobNativeAdId, NativeAdLoadCallback nativeAdLoadCallback) {
//        this.mContext = mContext;
        isNativeAdsAviable = false;
        if (lifecycleOwner instanceof Context) {
            this.mContext = (Context) lifecycleOwner;
        }

        this.ourAdmobNativeId = admobNativeAdId;
        this.admobNativeAdId = admobNativeAdId;

        this.nativeAdLoadCallback = nativeAdLoadCallback;
        if (layout != 0) {
            this.customLayout = layout;
            Log.d(TAG, "if: " + customLayout);

        } else {
            this.customLayout = R.layout.native_ad_view_large_card;
        }

        Log.d(TAG, "ourAdmobNativeId : " + ourAdmobNativeId);
        Log.d(TAG, "admobNativeAdId : " + admobNativeAdId);

//        loadAdTamplete();

        this.lifecycleOwner = lifecycleOwner;

        if (isReloadAds) {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            registerLifecycleObserver();
        } else {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            loadAdTamplete();
        }
    }

    public void loadAdTamplete() {
        viewNative = LayoutInflater.from(mContext).inflate(customLayout, null);


        clickable = viewNative.findViewById(R.id.unified);


        inflateNativeAds();
    }


    public void inflateNativeAds() {
        try {

            AdLoader.Builder builder = new AdLoader.Builder(mContext, admobNativeAdId);
            builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                    NativeAdView adView = (NativeAdView) clickable;

                    nativeAd.setOnPaidEventListener(new OnPaidEventListener() {

                        @Override
                        public void onPaidEvent(@NonNull AdValue adValue) {
                        }
                    });
                    Log.d(TAG, "onUnifiedNativeAdLoaded ");
                    isNativeAdsAviable = true;

                    if (clickable!=null){
                        Log.d(TAG, "viewNative: not null" + nativeAd);
                    }else {
                        Log.d(TAG, "viewNative: null" + nativeAd);
                    }

                    populateUnifiedNativeAdView(nativeAd, adView);
                }
            });


            VideoOptions videoOptions = new VideoOptions.Builder()
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build();

            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    nativeAdLoadCallback.nativeAdFailedToLoad("admob_native_ad_failed_for_");
                    Log.d(TAG, "admob_failed_native_ad_for_create_video : " + loadAdError.getMessage());

                }


                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    isNativeAdsAviable = true;
                    Log.d(TAG, "admob_load_native_ad_for_create_video : ");
                    nativeAdLoadCallback.nativeAdLoadedSuccessfully("admob_native_ad_load_for_");
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    Log.d(TAG, "admob_click_native_ad_for_create_video : ");
                    nativeAdLoadCallback.nativeAdClickError("admob_native_ad_click_for_");
                    //MyApplication.getInstance().EventRegister("admob_click_native_ad_for_create_video",new Bundle());
                }
            }).build();

            adLoader.loadAd(AdUtils.getInstance().requestMediationAd(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateUnifiedNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.

        Log.d(TAG, "populateUnifiedNativeAdView ");

        if (adView.findViewById(R.id.ad_media) != null) {
            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
            adView.getMediaView().setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        }


        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            //String upperString = nativeAd.getCallToAction().substring(0, 1).toUpperCase() + nativeAd.getCallToAction().substring(1).toLowerCase();
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        //adView.setAdChoicesView(AdChoicesView);
        if (nativeAd.getIcon() == null) {
            // adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);

    }

    public View getInativeAdObjForThemeList() {
        Log.e("AAAAAAAAAA", "not null" + viewNative.getParent());
        if (viewNative.getParent() != null) {
            Log.e("AAAAAAAAAA", "not null");
            ((ViewGroup) viewNative.getParent()).removeView(viewNative);
        } else {
            Log.e("AAAAAAAAAA", " null");
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
