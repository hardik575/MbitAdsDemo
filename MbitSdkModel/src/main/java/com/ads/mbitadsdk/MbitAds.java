package com.ads.mbitadsdk;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.ads.mbitadsdk.bannerad.BannerAdMediation;
import com.ads.mbitadsdk.intad.AdmobInterstitialAdsMediation;
import com.ads.mbitadsdk.intad.IntCallback;
import com.ads.mbitadsdk.nativead.NativeAdLoadCallback;
import com.ads.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.ads.mbitadsdk.nativead.NativeAdsBig;
import com.ads.mbitadsdk.nativead.NativeCustomTemplate;
import com.ads.mbitadsdk.nativead.NativeMediumTemplate;
import com.ads.mbitadsdk.rewardint.RewardedInterstitialAds;
import com.ads.mbitadsdk.rewardint.RewaredIntCallback;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MbitAds {


    //    Context context;
    public int clickCount = 0;

    public int getUniversalClickCount() {
        return universalClickCount;
    }

    public void setUniversalClickCount(int universalClickCount) {
        this.universalClickCount = universalClickCount;
    }

    int universalClickCount = 3;

    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    public AdSDKPref prefForTagValue;
    boolean isAllAdShow = true;

    public MbitAds() {

    }

    public void SetAllAdOnOff(boolean isAllAdShow) {
        this.isAllAdShow = isAllAdShow;
    }


    public void setDebugMode(boolean isDebug) {
        MyApp.getInstance().setDebugMode(isDebug);
    }


    public static void loadHomeScreen(Activity activity) {
    }

    public void intisialAdMob(Context context) {

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("AdmobInitialise", "onInitializationComplete : " + MobileAds.getInitializationStatus().toString());

            }
        });

    }

    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////// Home Intersial ads /////////////////////////////////


    AdsWaitingDailog adsWaitingDailog;


    public NativeMediumTemplate nativeMediumTemplate;


    public void LoadNativeMedium(Context context, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, boolean isShowAds, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (!isAllAdShow || !isShowAds || !isNetworkAvailable(context)) {
            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
            return;
        }

        try {
            nativeMediumTemplate = new NativeMediumTemplate(context, lifecycleOwner, isReloadAds, adMobNativeId,
                    new NativeAdLoadCallback() {
                        @Override
                        public void nativeAdLoadedSuccessfully(String eventName) {
                            Log.d("LoadNativeMedium", "nativeAdLoadedSuccessfully");


                            try {
                                if (rlNativeAdsAll != null) {
                                    shimerNativeLayoutAll.setVisibility(View.GONE);
                                    rlNativeAdsAll.setVisibility(View.VISIBLE);
                                    View adNative;
                                    if (nativeMediumTemplate != null) {
                                        adNative = nativeMediumTemplate.getNativeTamplate();
                                        if (rlNativeAdsAll != null && adNative != null) {
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rlNativeAdsAll.setVisibility(View.VISIBLE);
                                                    if (adNative.getParent() != null) {
                                                        Log.d("LoadNativeMedium", "ad View Set");
                                                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                    } else {
                                                        Log.d("LoadNativeMedium", "ad View Set null");
                                                    }
                                                    Log.d("LoadNativeMedium", "ad View Set one");
                                                    rlNativeAdsAll.removeAllViews();
                                                    rlNativeAdsAll.addView(adNative);
                                                }
                                            }, 100);
                                        } else {
                                            Log.d("LoadNativeMedium", "ad View Not Set");
                                            rlNativeAdsAll.setVisibility(View.GONE);
                                        }
                                    } else {
                                        //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsMedium);
                                        Log.d("LoadNativeMedium", "ad View Not Set 1");
                                        shimerNativeLayoutAll.setVisibility(View.GONE);
                                        rlNativeAdsAll.setVisibility(View.GONE);

                                    }
                                } else {
                                    Log.d("LoadNativeMedium", "Load But Container Null Found 1");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void nativeAdFailedToLoad(String msg) {
                            shimerNativeLayoutAll.setVisibility(View.VISIBLE);
                            rlNativeAdsAll.setVisibility(View.GONE);
                            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
                            LoadNativeMedium(context, lifecycleOwner, adMobNativeId, isReloadAds, isShowAds, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "onNativeAdFailedToLoad For OnBoarding Screen One");
                        }

                        @Override
                        public void nativeAdClick() {

                        }

                        @Override
                        public void nativeAdClickError(String msg) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void ShowNativeMedium(Context context, LifecycleOwner lifecycleOwner, boolean isRelaoadAds, String adMobNativeId, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
//
//        if (!isAllAdShow || onOff.equals("off")) {
//            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
//                shimerNativeLayoutAll.setVisibility(View.GONE);
//                rlNativeAdsAll.setVisibility(View.GONE);
//            }
//        } else {
//
//            View adNative;
//
//            if (nativeMediumTemplate != null) {
//                adNative = nativeMediumTemplate.getNativeTamplate();
//                if (rlNativeAdsAll != null && adNative != null) {
//
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            shimerNativeLayoutAll.setVisibility(View.GONE);
//                            rlNativeAdsAll.setVisibility(View.VISIBLE);
//
//                            if (adNative.getParent() != null) {
//                                android.util.Log.d("showNativeMedium", "ad View Set");
//                                ((ViewGroup) adNative.getParent()).removeView(adNative);
//                            }
//                            android.util.Log.d("showNativeMedium", "ad View Set one");
//                            rlNativeAdsAll.removeAllViews();
//                            rlNativeAdsAll.addView(adNative);
//                            nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
//                        }
//                    }, 100);
//
//
//                } else {
//                    android.util.Log.d("showNativeMedium", "ad View Not Set");
//
//                }
//            } else {
//
//                nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
//
//                LoadNativeMedium(context, lifecycleOwner, adMobNativeId, isRelaoadAds, isna, nativeAdLoadSucessCallBack);
//
////                LoadNativeMedium(context, adMobNativeId, fbNativeId, allOnOff, onOff, mediationFlag, nativeAdLoadSucessCallBack);
//                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
//            }
//        }
//
//    }


    ///////////////////////////////////////// Native Custom Ads Load /////////////////////////////////////////////////////


    public NativeCustomTemplate nativeCustomTemplate;
    public RelativeLayout rlNativeAdsAll = null;
    public ShimmerFrameLayout shimerNativeLayoutAll = null;


    public void setLayoutCustom(RelativeLayout relativeLayout, ShimmerFrameLayout shimmerFrameLayout) {
        rlNativeAdsAll = relativeLayout;
        shimerNativeLayoutAll = shimmerFrameLayout;

    }

    public void LoadOnTimeNativeCustom(Context context, int layout, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, boolean isShowAds, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (!isAllAdShow || !isShowAds || !isNetworkAvailable(context)) {
            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
            return;
        }

        try {
            nativeCustomTemplate = new NativeCustomTemplate(context, layout, lifecycleOwner, isReloadAds, adMobNativeId,
                    new NativeAdLoadCallback() {
                        @Override
                        public void nativeAdLoadedSuccessfully(String eventName) {
                            Log.d("LoadNativeMedium", "nativeAdLoadedSuccessfully");
                            View adNative = null;
                            try {
                                if (rlNativeAdsAll != null) {
                                    Log.d("LoadNativeMedium", "not null");
                                    shimerNativeLayoutAll.setVisibility(View.GONE);
                                    rlNativeAdsAll.setVisibility(View.VISIBLE);

                                    if (nativeCustomTemplate != null) {
                                        Log.d("LoadNativeMedium", " nativeCustomTemplate not null");
                                        adNative = nativeCustomTemplate.getViewData();
                                        if (rlNativeAdsAll != null && adNative != null) {

                                            View finalAdNative = adNative;
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rlNativeAdsAll.setVisibility(View.VISIBLE);
                                                    if (finalAdNative.getParent() != null) {
                                                        Log.d("LoadNativeMedium", "ad View Set");
                                                        ((ViewGroup) finalAdNative.getParent()).removeView(finalAdNative);
                                                    } else {
                                                        Log.d("LoadNativeMedium", "ad View Set null");
                                                    }
                                                    Log.d("LoadNativeMedium", "ad View Set one");
                                                    rlNativeAdsAll.removeAllViews();
                                                    rlNativeAdsAll.addView(finalAdNative);
                                                }
                                            }, 100);
                                        } else {
                                            Log.d("LoadNativeMedium", "ad View Not Set");
                                            rlNativeAdsAll.setVisibility(View.GONE);
                                        }
                                    } else {
                                        //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsMedium);
                                        Log.d("LoadNativeMedium", "ad View Not Set 1");
                                        Log.d("LoadNativeMedium", " nativeCustomTemplate null");
                                        shimerNativeLayoutAll.setVisibility(View.GONE);
                                        rlNativeAdsAll.setVisibility(View.GONE);
                                    }
                                } else {
                                    Log.d("LoadNativeMedium", " null");
                                    Log.d("LoadNativeMedium", "Load But Container Null Found 1");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
                        }

                        @Override
                        public void nativeAdFailedToLoad(String msg) {
                            shimerNativeLayoutAll.setVisibility(View.VISIBLE);
                            rlNativeAdsAll.setVisibility(View.GONE);
                            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
//                            LoadOnTimeNativeCustom(context, layout, lifecycleOwner, adMobNativeId, true, onOff, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "onNativeAdFailedToLoad For OnBoarding Screen One");
                        }

                        @Override
                        public void nativeAdClick() {

                        }

                        @Override
                        public void nativeAdClickError(String msg) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowNativeCustom(Context context, int layout, LifecycleOwner lifecycleOwner, boolean isRelaoadAds, String adMobNativeId, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (!isAllAdShow || onOff.equals("off")) {
            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
        } else {
            View adNative;
            if (nativeCustomTemplate != null) {
                adNative = nativeCustomTemplate.getViewData();
                if (rlNativeAdsAll != null && adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimerNativeLayoutAll.setVisibility(View.GONE);
                            rlNativeAdsAll.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {
                                android.util.Log.d("ShowNativeCustom", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            } else {

                            }
                            android.util.Log.d("ShowNativeCustom", "ad View Set one");
                            rlNativeAdsAll.removeAllViews();
                            rlNativeAdsAll.addView(adNative);
                            nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
                        }
                    }, 100);
                } else {
                    android.util.Log.d("ShowNativeCustom", "ad View Not Set");
                }
            } else {
                nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
//                LoadOnTimeNativeCustom(context, layout, lifecycleOwner, adMobNativeId, isRelaoadAds, onOff, nativeAdLoadSucessCallBack);
                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
            }
        }

    }


    ///////////////////////////////////////// Native Large Ads Load /////////////////////////////////////////////////////


    public NativeAdsBig nativeBigTemplate;


    public void LoadNativeBig(Context context, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (!isAllAdShow || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {

            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
            return;
        }

        try {
            nativeBigTemplate = new NativeAdsBig(context, lifecycleOwner, isReloadAds, adMobNativeId,
                    new NativeAdLoadCallback() {
                        @Override
                        public void nativeAdLoadedSuccessfully(String eventName) {
                            Log.d("LoadNativeMedium", "nativeAdLoadedSuccessfully");
                            MyApp.getInstance().EventRegister(eventName + "home_big_v97", new Bundle());

                            try {
                                if (rlNativeAdsAll != null) {
                                    shimerNativeLayoutAll.setVisibility(View.GONE);
                                    rlNativeAdsAll.setVisibility(View.VISIBLE);
                                    View adNative;
                                    if (nativeBigTemplate != null) {
                                        adNative = nativeBigTemplate.getInativeAdObjForThemeList();
                                        if (rlNativeAdsAll != null && adNative != null) {
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    rlNativeAdsAll.setVisibility(View.VISIBLE);
                                                    if (adNative.getParent() != null) {
                                                        Log.d("LoadNativeMedium", "ad View Set");
                                                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                    } else {
                                                        Log.d("LoadNativeMedium", "ad View Set null");
                                                    }
                                                    Log.d("LoadNativeMedium", "ad View Set one");
                                                    rlNativeAdsAll.removeAllViews();
                                                    rlNativeAdsAll.addView(adNative);
                                                }
                                            }, 100);
                                        } else {
                                            Log.d("LoadNativeMedium", "ad View Not Set");
                                            rlNativeAdsAll.setVisibility(View.GONE);
                                            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
                                        }
                                    } else {
                                        //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsMedium);
                                        Log.d("LoadNativeMedium", "ad View Not Set 1");
                                        shimerNativeLayoutAll.setVisibility(View.GONE);
                                        rlNativeAdsAll.setVisibility(View.GONE);

                                    }
                                } else {
                                    Log.d("LoadNativeMedium", "Load But Container Null Found 1");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void nativeAdFailedToLoad(String msg) {
                            shimerNativeLayoutAll.setVisibility(View.VISIBLE);
                            rlNativeAdsAll.setVisibility(View.GONE);
                            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
                            LoadNativeBig(context, lifecycleOwner, adMobNativeId, true, onOff, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "onNativeAdFailedToLoad For OnBoarding Screen One");
                        }

                        @Override
                        public void nativeAdClick() {

                        }

                        @Override
                        public void nativeAdClickError(String msg) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowNativeBig(Context context, LifecycleOwner lifecycleOwner, boolean isRelaoadAds, String adMobNativeId, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (!isAllAdShow || onOff.equals("off")) {
            shimerNativeLayoutAll.setVisibility(View.GONE);
            rlNativeAdsAll.setVisibility(View.GONE);
        } else {

            View adNative;

            if (nativeBigTemplate != null) {
                adNative = nativeBigTemplate.getInativeAdObjForThemeList();
                if (rlNativeAdsAll != null && adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimerNativeLayoutAll.setVisibility(View.GONE);
                            rlNativeAdsAll.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {
                                android.util.Log.d("showNativeMedium", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            }
                            android.util.Log.d("showNativeMedium", "ad View Set one");
                            rlNativeAdsAll.removeAllViews();
                            rlNativeAdsAll.addView(adNative);
                            nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
                        }
                    }, 100);
                } else {
                    android.util.Log.d("showNativeMedium", "ad View Not Set");

                }
            } else {
                nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
                LoadNativeBig(context, lifecycleOwner, adMobNativeId, isRelaoadAds, onOff, nativeAdLoadSucessCallBack);

                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
            }
        }

    }


    //////////////////////////////////// other intersioal Ad /////////////////////////////////


    public AdmobInterstitialAdsMediation mPreLoadIntAdsObj;
    public IntCallback intCallback;

    public void PreLoadInterstitialAd(Context context, String adMobIntId, boolean isShowAds) {

        if (!isAllAdShow) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (!isShowAds) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (!isNetworkAvailable(context)) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (mPreLoadIntAdsObj != null && mPreLoadIntAdsObj.isAdsAvailable()) {
            return;
        }

        MyApp.getInstance().EventRegister("Inter_Ad_Requested", new Bundle());

        mPreLoadIntAdsObj = new AdmobInterstitialAdsMediation(context, adMobIntId, new IntCallback() {
            @Override
            public void adIntClose() {
                MyApp.getInstance().EventRegister("Inter_Ad_Closed", new Bundle());
                clickCount = 0;
                intCallback.adIntClose();
                mPreLoadIntAdsObj = null;
                PreLoadInterstitialAd(context, adMobIntId, isShowAds);
            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {
                MyApp.getInstance().EventRegister("Inter_Ad_FailedToLoad", new Bundle());
                mPreLoadIntAdsObj = null;
                PreLoadInterstitialAd(context, adMobIntId, isShowAds);
            }

            @Override
            public void adIntClick() {
            }

            @Override
            public void adIntLoaded() {
                MyApp.getInstance().EventRegister("Inter_Ad_Loaded", new Bundle());
            }
        });
    }

    private AppCompatActivity mPreloadScreenActObj;

    public void ForceShowIntAd(AppCompatActivity mCurrentAct, String adMobIntId, String onOff) {
        mPreloadScreenActObj = mCurrentAct;

        if (mPreLoadIntAdsObj != null && mPreLoadIntAdsObj.isAdsAvailable()) {
            try {
                adsWaitingDailog = new AdsWaitingDailog();
                adsWaitingDailog.show(mCurrentAct.getSupportFragmentManager(), "adsWaitingDailog");
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (adsWaitingDailog != null && adsWaitingDailog.isVisible()) {
                            adsWaitingDailog.dismiss();
                        }

                        if (mPreLoadIntAdsObj != null) {
                            mPreLoadIntAdsObj.ShowMediationAds(mPreloadScreenActObj);
                        } else {
                            intCallback.adIntClose();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1500);
        } else {
            PreLoadInterstitialAd(mCurrentAct, adMobIntId, onOff);
        }
    }

    public void LoadBanner(Context context, LifecycleOwner lifecycleOwner, String adBannerId, boolean isShowAds, LinearLayout llAdContainer, FrameLayout ad_view_container, Boolean isCollapsingAdShow, boolean isReloading) {

        if (!isAllAdShow || !isShowAds || !isNetworkAvailable(context)) {
            llAdContainer.setVisibility(View.GONE);
            ad_view_container.setVisibility(View.GONE);
        } else {
            BannerAdMediation adMediation = new BannerAdMediation(context, lifecycleOwner, adBannerId, isCollapsingAdShow, isReloading);
            View adptiveBannerObj = adMediation.getAdpativeBanner();
            if (adptiveBannerObj != null) {
                ad_view_container.removeAllViews();
                ad_view_container.addView(adptiveBannerObj);
            }
        }
    }


    public AppOpenManagerSplash appOpenManagerSplash;

    public void initAppOpenSplash(Activity activity, String appOpenId, IntCallback intCallback) {
        if (!isAllAdShow) {
            intCallback.adIntClose();
            return;
        }
        appOpenManagerSplash = new AppOpenManagerSplash(activity, appOpenId, intCallback);
    }


    public AppOpenManager appOpenManager;

    public void initAppOpen() {
        if (!isAllAdShow) {
            return;
        }
        AdUtils.getInstance().isReadyPlaceToAppOpenAds = "1";
        appOpenManager = new AppOpenManager(MyApp.getInstance());
    }


    private static MbitAds instance = new MbitAds();

    public static MbitAds getInstance() {
        return instance;
    }


    ////////////////////////////////////// Reward ad ////////////////////////////////////////


    public void RewardAdLoad(Context context, String adRewardId, RewaredIntCallback rewaredIntCallback) {

        if (!isAllAdShow) {
            rewaredIntCallback.rewaredIntCloseWithReward();
            return;
        }
        new RewardedInterstitialAds(context, adRewardId, new RewaredIntCallback() {
            @Override
            public void rewaredIntCloseWithReward() {
//                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                rewaredIntCallback.rewaredIntCloseWithReward();
            }

            @Override
            public void rewaredIntCloseWithOutReward() {
                rewaredIntCallback.rewaredIntCloseWithOutReward();

            }

            @Override
            public void rewaredIntFailedToLoad() {
                rewaredIntCallback.rewaredIntFailedToLoad();

            }

            @Override
            public void rewaredIntClick() {
                rewaredIntCallback.rewaredIntClick();

            }

            @Override
            public void rewaredIntLoaded() {
                rewaredIntCallback.rewaredIntLoaded();
            }
        });
    }


    public AdmobInterstitialAdsMediation mIntSameTimeObj;

    public void SameTimeShowInterstitialAd(Context context, String adMobIntId, Boolean isShowAds, IntCallback intCallback) {

        if (!isAllAdShow) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (isShowAds.equals("off")) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (!isNetworkAvailable(context)) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }


        MyApp.getInstance().EventRegister("Inter_Ad_Requested", new Bundle());


        try {
            adsWaitingDailog = new AdsWaitingDailog();
            adsWaitingDailog.show(((AppCompatActivity) context).getSupportFragmentManager(), "adsWaitingDailog");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mIntSameTimeObj = new AdmobInterstitialAdsMediation(context, adMobIntId, new IntCallback() {
            @Override
            public void adIntClose() {
                MyApp.getInstance().EventRegister("Inter_Ad_Closed", new Bundle());
                clickCount = 0;
                intCallback.adIntClose();
                mIntSameTimeObj = null;
            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {
                MyApp.getInstance().EventRegister("Inter_Ad_FailedToLoad", new Bundle());
                mIntSameTimeObj = null;
            }

            @Override
            public void adIntClick() {
            }

            @Override
            public void adIntLoaded() {
                MyApp.getInstance().EventRegister("Inter_Ad_Loaded", new Bundle());
                if (context == null) {
                    intCallback.adIntClose();
                    return;
                }
                adsWaitingDailog.dismiss();
                if (mIntSameTimeObj != null) {
                    mIntSameTimeObj.mInterstitialAdApp.show((AppCompatActivity) context);
                } else {
                    intCallback.adIntClose();
                }
            }
        });
    }

    NativeCustomTemplate preLoadingNative = null;

    public void PreLoadingNativeCustom(Context context, int layout, LifecycleOwner lifecycleOwner, boolean isShowAds, String nativeId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (!isAllAdShow || !isShowAds || !isNetworkAvailable(context)) {
            return;
        }

        Log.d("PreLoadingNativeCustom", "==>");
        try {
            preLoadingNative = new NativeCustomTemplate(context, layout, lifecycleOwner, false, nativeId,
                    new NativeAdLoadCallback() {
                        @Override
                        public void nativeAdLoadedSuccessfully(String eventName) {
                            Log.d("LoadNativeMedium", "nativeAdLoadedSuccessfully");
                            View adNative = preLoadingNative.getViewData();
                            nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
                        }

                        @Override
                        public void nativeAdFailedToLoad(String msg) {
                            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();

                        }

                        @Override
                        public void nativeAdClick() {

                        }

                        @Override
                        public void nativeAdClickError(String msg) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShowForceNativeCustom(Context context, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        try {
            View adNative = preLoadingNative.getViewData();

            if (adNative != null) {
                if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                    shimerNativeLayoutAll.setVisibility(View.GONE);

                    rlNativeAdsAll.removeAllViews();
                    rlNativeAdsAll.addView(adNative);
                    nativeAdLoadSucessCallBack.onNativeAdLoadedSucessFully(adNative);
                } else {
                    nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
                }

            } else {
                nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
            }
        } catch (Exception e) {
            nativeAdLoadSucessCallBack.onNativeAdFailedToLoad();
        }


    }


}
