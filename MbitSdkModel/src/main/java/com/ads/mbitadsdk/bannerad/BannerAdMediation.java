package com.ads.mbitadsdk.bannerad;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.ads.mbitadsdk.AdUtils;
import com.ads.mbitadsdk.MyApp;
import com.ads.mbitsdkmodel.R;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;

import com.ads.mbitadsdk.Log;


public class BannerAdMediation {
    private Context mContext;
    View viewNative;
    private FrameLayout adContainerView;
    private boolean isAdLoadedSuccess;
    private String admobBannerId;

    //Admob Banner Veriable
    com.google.android.gms.ads.AdView adViewAdmob;
    com.google.android.gms.ads.AdSize adSizeAdmob;
    Boolean isCollapsing = false;
    String TAG = "BANNER_AD";

    LifecycleOwner lifecycleOwner;


    public BannerAdMediation(Context cntx, LifecycleOwner lifecycleOwner, String admobBannerId, Boolean isCollapsingAdShow, boolean isReloadAds) {
        this.mContext = cntx;
        this.admobBannerId = admobBannerId;
        this.isCollapsing = isCollapsingAdShow;

        isAdLoadedSuccess = false;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        viewNative = inflater.inflate(R.layout.load_fb_banner, null);
        adContainerView = viewNative.findViewById(R.id.ad_view_container);


        this.lifecycleOwner = lifecycleOwner;

        if (isReloadAds) {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            registerLifecycleObserver();
        } else {
            Log.d(TAG, "isReloadAds: " + isReloadAds);
            LoadAdmobBanner();
        }


    }


    private void registerLifecycleObserver() {
        lifecycleOwner.getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_RESUME) {
                Log.d(TAG, "ON_RESUME: " + event);
                LoadAdmobBanner();
            }
        });
    }

    //Admob Code Start Here
    public void LoadAdmobBanner() {
        Log.d(TAG, "LoadAdmobBanner : >>>>");
        try {
            adSizeAdmob = getAdSize();
            Log.d(TAG, "adSizeAdmob : >>>>");
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) adContainerView.getLayoutParams();
            layoutParams.height = adSizeAdmob.getHeightInPixels(mContext);
            Log.d(TAG, "height : >>>>" + layoutParams.height);
            adContainerView.setLayoutParams(layoutParams);


            adContainerView.post(new Runnable() {
                @Override
                public void run() {
                    loadBannerAd();

                }
            });

        } catch (Exception e) {
            Log.d(TAG, "catch : " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void loadBannerAd() {
        Log.d(TAG, "call ");
        try {
            if (adViewAdmob != null) {
                adViewAdmob.destroy();
            }
            adViewAdmob = new com.google.android.gms.ads.AdView(mContext);
            adViewAdmob.setAdListener(new com.google.android.gms.ads.AdListener() {

                @Override
                public void onAdLoaded() {

                    adViewAdmob.setOnPaidEventListener(new OnPaidEventListener() {
                        @Override
                        public void onPaidEvent(@NonNull AdValue adValue) {
                        }
                    });

                    MyApp.getInstance().EventRegister("admob_banner_load", new Bundle());
                    isAdLoadedSuccess = true;
                    adContainerView.removeAllViews();
                    adContainerView.addView(adViewAdmob);
                    Log.d(TAG, "admob_banner_load");
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    isAdLoadedSuccess = false;
                    Log.d(TAG, "admob_banner_loadAdError" + loadAdError.getMessage());
                    LoadAdmobBanner();
                    MyApp.getInstance().EventRegister("admob_banner_failed", new Bundle());
                }


                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                    Log.d(TAG, "admob_banner_onAdClicked");
                    MyApp.getInstance().EventRegister("admob_banner_click", new Bundle());
                }
            });
            adViewAdmob.setAdUnitId(admobBannerId);
            adViewAdmob.setAdSize(adSizeAdmob);
            if (isCollapsing) {
                Bundle extras = new Bundle();
                extras.putString("collapsible", "bottom");
                AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
                adViewAdmob.loadAd(adRequest);
            } else {
                AdRequest adRequest = AdUtils.getInstance().requestMediationAd(mContext);
                adViewAdmob.loadAd(adRequest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private com.google.android.gms.ads.AdSize getAdSize() {
        Log.d(TAG, "getAdSize : >>>>");
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels = adContainerView.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }
        int adWidth = (int) (adWidthPixels / density);
        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(mContext, adWidth);
        // return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth);
    }

    public View getAdpativeBanner() {
        try {
            if (viewNative.getParent() != null) {
                ((ViewGroup) viewNative.getParent()).removeView(viewNative);
            }
            return viewNative;
        } catch (Exception e) {
            e.printStackTrace();
            return viewNative;
        }
    }


}
