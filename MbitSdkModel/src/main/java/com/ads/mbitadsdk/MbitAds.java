package com.mbitadsdk;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mbitadsdk.bannerad.AdmobAndFbBannerMediation;
import com.mbitadsdk.intad.AdmobAndFBInterstitialAdsMediation;
import com.mbitadsdk.intad.IntCallback;
import com.mbitadsdk.nativead.NativeAdLoadCallback;
import com.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.mbitadsdk.nativead.NativeAdsBig;
import com.mbitadsdk.nativead.NativeAdsBigForHomeTheme;
import com.mbitadsdk.nativead.NativeCustomTemplate;
import com.mbitadsdk.nativead.NativeMediumTemplate;
import com.mbitadsdk.rewardint.RewardedInterstitialAds;
import com.mbitadsdk.rewardint.RewaredIntCallback;
import com.mbitsdk.R;

public class MbitAds {
    public static String APP_OPEN_RESUME_ID = "ca-app-pub-3940256099942544/9257395921";
    public static String APP_OPEN_SPASH_ID = "ca-app-pub-3940256099942544/9257395921";
    private static String ONBOARDING_SCREEN_ID_SECOND = "ca-app-pub-3940256099942544/2247696110";
    private static String ONBOARDING_SCREEN_ID_THIRD = "ca-app-pub-3940256099942544/2247696110";
    private static String ONBOARDING_SCREEN_ID_ONE = "ca-app-pub-3940256099942544/2247696110";

    private static String NATIVE_LANGUAGE_ID_ONE = "ca-app-pub-3940256099942544/2247696110";

    private static String NATIVE_LANGUAGE_HIGH_FLOOR_ID_ONE = "ca-app-pub-3940256099942544/2247696110";
    private static String NATIVE_LANGUAGE_ID_SECOND = "ca-app-pub-3940256099942544/2247696110";
    private static String NATIVE_LANGUAGE_HIGH_FLOOR_ID_SECOND = "ca-app-pub-3940256099942544/2247696110";
    private static String START_SCREEN_ID = "ca-app-pub-3940256099942544/2247696110";

    private static String INTER_HOME_ID = "ca-app-pub-3940256099942544/1033173712";

    public static String AD_MOB_HOME_SCREEN_BANNER_ID = "ca-app-pub-3940256099942544/2014213617";
    public static String FB_MOB_HOME_SCREEN_BANNER_ID = "YOUR_PLACEMENT_ID";


    //    Context context;
    int clickCount = 0;
    int universalClickCount = 3;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    public AdSDKPref prefForTagValue;
    String adAllOnOff = "0";

    public MbitAds() {

    }

    public void setAllAdOnOff(String adOnOff) {
        this.adAllOnOff = adOnOff;
    }


    public void ShowManyClickIntersial(Integer universalClickCount) {
        this.universalClickCount = universalClickCount;
    }


    public void setDebugMode(boolean isDebug) {
        MyApp.getInstance().setDebugMode(isDebug);
    }


    public static void loadHomeScreen(Activity activity) {
    }


    public void remoteConfigToDataId(Context context) {
        prefForTagValue = AdSDKPref.getInstance(context);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(2)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {

                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d("RemoteValue", "Config params updated: " + updated);
                            updateFirebaseFetchValue();
                        }
                    }
                });

    }


    private void setupAdId(Context context) {
        APP_OPEN_SPASH_ID = prefForTagValue.getString(AdSDKPref.TAG_APP_OPEN_AD_FOR_SPLASH_SCREEN_ID, context.getString(R.string.spalsh_app_resume_open_id));
//        try {
//            SPLSH_TIME = Integer.parseInt(prefForTagValue.getString(AdSDKPref.TAG_SPLASH_SCREEN_TIME_SEC, "20000"));
//        } catch (NumberFormatException e) {
//            SPLSH_TIME = 20000;
//        }

        try {
            universalClickCount = Integer.parseInt(prefForTagValue.getString(AdSDKPref.TAG_CLICK_INTERSIAL_AD_COUNTER, "2"));
        } catch (NumberFormatException e) {
            universalClickCount = 2;
        }

        ONBOARDING_SCREEN_ID_ONE = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_ONE_ID, context.getString(R.string.native_onbording_one_id));
        ONBOARDING_SCREEN_ID_SECOND = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_TWO_ID, context.getString(R.string.native_onbording_two_id));
        ONBOARDING_SCREEN_ID_THIRD = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_THREE_ID, context.getString(R.string.native_onbording_three_id));


        START_SCREEN_ID = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_START_SCREEN_ID, context.getString(R.string.native_start_screen_id));


        NATIVE_LANGUAGE_ID_ONE = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID, context.getString(R.string.native_languge_one_id));
        NATIVE_LANGUAGE_ID_SECOND = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID, context.getString(R.string.native_languge_two_id));
        NATIVE_LANGUAGE_HIGH_FLOOR_ID_ONE = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID_HIGH_FLOOR, context.getString(R.string.native_languge_one_high_floor_id));
        NATIVE_LANGUAGE_HIGH_FLOOR_ID_SECOND = prefForTagValue.getString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID_HIGH_FLOOR, context.getString(R.string.native_languge_two_high_floor_id));


        APP_OPEN_RESUME_ID = prefForTagValue.getString(AdSDKPref.TAG_APP_OPEN_RESUME_ID, context.getString(R.string.app_open_resume_id));
        AD_MOB_HOME_SCREEN_BANNER_ID = prefForTagValue.getString(AdSDKPref.TAG_BANNER_AD_ID, context.getString(R.string.bannner_id));

//        INTER_HOME_ID = prefForTagValue.getString(AdSDKPref.TAG_INTERSIAL_AD_ID, context.getString(R.string.intrsial_ad_id));


    }

    private void updateFirebaseFetchValue() {
        prefForTagValue.setString(AdSDKPref.TAG_ALL_AD_OFF, mFirebaseRemoteConfig.getString("tag_all_ad_off"));
        prefForTagValue.setString(AdSDKPref.TAG_APP_OPEN_AD_FOR_SPLASH_SCREEN, mFirebaseRemoteConfig.getString("tag_app_open_ad_for_splash_screen"));
        prefForTagValue.setString(AdSDKPref.TAG_APP_OPEN_AD_FOR_SPLASH_SCREEN_ID, mFirebaseRemoteConfig.getString("tag_app_open_ad_for_splash_screen_id"));
        prefForTagValue.setString(AdSDKPref.TAG_SPLASH_SCREEN_TIME_SEC, mFirebaseRemoteConfig.getString("tag_splash_screen_time_sec"));
        ////////////////////////////////// start onbording tag ////////////////////////////////////////////

        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN, mFirebaseRemoteConfig.getString("tag_native_ad_for_onbording_screen"));

        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_ONE_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_onbording_screen_one_id"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_TWO_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_onbording_screen_two_id"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_THREE_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_onbording_screen_three_id"));


        ////////////////////////////////// start StartScreen tag ////////////////////////////////////////////
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_START_SCREEN, mFirebaseRemoteConfig.getString("tag_native_ad_for_start_screen"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_START_SCREEN_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_start_screen_id"));


        ////////////////////////////////// start other screen tag ////////////////////////////////////////////
        prefForTagValue.setString(AdSDKPref.TAG_BANNER_AD_ON_OFF, mFirebaseRemoteConfig.getString("tag_banner_ad_on_off"));
        prefForTagValue.setString(AdSDKPref.TAG_BANNER_AD_ID, mFirebaseRemoteConfig.getString("tag_banner_ad_id"));
        prefForTagValue.setString(AdSDKPref.TAG_BANNER_AD_COLLAPSING, mFirebaseRemoteConfig.getString("tag_banner_ad_collapsing"));
        prefForTagValue.setString(AdSDKPref.TAG_INTERSIAL_AD_ON_OFF, mFirebaseRemoteConfig.getString("tag_intersial_ad_on_off"));
        prefForTagValue.setString(AdSDKPref.TAG_INTERSIAL_AD_ID, mFirebaseRemoteConfig.getString("tag_intersial_ad_id"));
        prefForTagValue.setString(AdSDKPref.TAG_CLICK_INTERSIAL_AD_COUNTER, mFirebaseRemoteConfig.getString("tag_click_intersial_ad_counter"));


        ////////////////////////////////// start languge screen tag ////////////////////////////////////////////
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN, mFirebaseRemoteConfig.getString("tag_native_ad_for_languge_screen"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_languge_screen_one_id"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID, mFirebaseRemoteConfig.getString("tag_native_ad_for_languge_screen_two_id"));


        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID_HIGH_FLOOR, mFirebaseRemoteConfig.getString("tag_native_ad_for_languge_screen_one_id_high_floor"));
        prefForTagValue.setString(AdSDKPref.TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID_HIGH_FLOOR, mFirebaseRemoteConfig.getString("tag_native_ad_for_languge_screen_two_id_high_floor"));


        ////////////////////////////////// start app resume tag ////////////////////////////////////////////
        prefForTagValue.setString(AdSDKPref.TAG_APP_OPEN_RESUME_ID, mFirebaseRemoteConfig.getString("tag_app_open_resume_id"));
        prefForTagValue.setString(AdSDKPref.TAG_APP_OPEN_RESUME_ON_OFF, mFirebaseRemoteConfig.getString("tag_app_open_resume_on_off"));

    }

    public void intisialAdMob(Context context) {

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                com.mbitadsdk.Log.d("AdmobInitialise", "onInitializationComplete : " + MobileAds.getInitializationStatus().toString());

            }
        });

    }


    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public ShimmerFrameLayout shimmer_container_banner;


    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForIntroScreen;
    public RelativeLayout rlNativeAdsContainerForCardTheme = null;

    public void LoadNativeOnBordingScreenOne(Context context, String onOff, String nativeId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForCardTheme != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
            }
            return;
        }
        Log.d("VideoEditingModel", "LoadNativeAdForNotificationDialog ");
        try {

            if (admobAndFbNativeAdMediationForIntroScreen == null) {
                admobAndFbNativeAdMediationForIntroScreen = new NativeAdsBigForHomeTheme(context, nativeId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                Log.d("NativeTemplete", "nativeAdLoadedSuccessfully");
                                MyApp.getInstance().EventRegister(eventName + "home_big_v97", new Bundle());
                                Log.d("NativeTemplete", "nativeAdLoadedSuccessfully For Onboarding Screen One");
                                try {
                                    View adNative = null;
                                    if (rlNativeAdsContainerForCardTheme != null) {
                                        shimmer_container_banner.setVisibility(View.GONE);
                                        rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForIntroScreen != null) {
                                            adNative = admobAndFbNativeAdMediationForIntroScreen.getInativeAdObjForThemeList();
                                            if (rlNativeAdsContainerForCardTheme != null && adNative != null) {
                                                rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LoadNativeAdForLanguage", "ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LoadNativeAdForLanguage", "ad View Set one");
                                                rlNativeAdsContainerForCardTheme.removeAllViews();
                                                rlNativeAdsContainerForCardTheme.addView(adNative);
                                            } else {
                                                Log.d("LoadNativeAdForLanguage", "ad View Not Set");
                                                rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LoadNativeAdForLanguage", "ad View Not Set 1");
                                            shimmer_container_banner.setVisibility(View.GONE);
                                            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                                        }

                                    } else {
                                        Log.d("NativeTemplete", "Load But Container Null Found 1");
                                    }
                                    nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                admobAndFbNativeAdMediationForIntroScreen = null;
                                shimmer_container_banner.setVisibility(View.VISIBLE);
                                rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                                LoadNativeOnBordingScreenOne(context, onOff, nativeId, nativeAdLoadSucessCallBack);

                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();

                                Log.d("NativeTemplete", "nativeAdFailedToLoad For OnBoarding Screen One");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////// native onbording two ////////////////////////////////////////

    public ShimmerFrameLayout shimmer_container_bannerSecond;

    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForIntroScreenSecond;
    public RelativeLayout rlNativeAdsContainerForCardThemeSecond = null;


    public void LoadNativeOnBordingScreenSecond(Context context, String onOff, String nativeId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForCardThemeSecond != null && shimmer_container_bannerSecond != null) {
                shimmer_container_bannerSecond.setVisibility(View.GONE);
                rlNativeAdsContainerForCardThemeSecond.setVisibility(View.GONE);
            }
            return;
        }
        Log.d("LoadNativeOnBordingScreenSecond", "LoadNativeOnBordingScreenSecond ");
        try {

            if (admobAndFbNativeAdMediationForIntroScreenSecond == null) {
                int mediationFlag = 0;
                admobAndFbNativeAdMediationForIntroScreenSecond = new NativeAdsBigForHomeTheme(context, nativeId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                Log.d("LoadNativeAdForLanguage", "nativeAdLoadedSuccessfully Intro Second");
                                View adNative = null;
                                try {
                                    if (rlNativeAdsContainerForCardThemeSecond != null) {
                                        shimmer_container_bannerSecond.setVisibility(View.GONE);
                                        rlNativeAdsContainerForCardThemeSecond.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForIntroScreenSecond != null) {
                                            adNative = admobAndFbNativeAdMediationForIntroScreenSecond.getInativeAdObjForThemeList();
                                            if (rlNativeAdsContainerForCardThemeSecond != null && adNative != null) {
                                                rlNativeAdsContainerForCardThemeSecond.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LoadNativeAdForLanguage", "Intro Second ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LoadNativeAdForLanguage", "Intro Second ad View Set one");
                                                rlNativeAdsContainerForCardThemeSecond.removeAllViews();
                                                rlNativeAdsContainerForCardThemeSecond.addView(adNative);
                                            } else {
                                                Log.d("LoadNativeAdForLanguage", "Intro Second ad View Not Set");
                                                rlNativeAdsContainerForCardThemeSecond.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LoadNativeAdForLanguage", "Intro Second ad View Not Set 1");
                                            shimmer_container_bannerSecond.setVisibility(View.GONE);
                                            rlNativeAdsContainerForCardThemeSecond.setVisibility(View.GONE);
                                        }
                                    } else {
                                        Log.d("LoadNativeAdForLanguage", "Intro Second Load But Container Null Found 1");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                shimmer_container_bannerSecond.setVisibility(View.VISIBLE);
                                rlNativeAdsContainerForCardThemeSecond.setVisibility(View.GONE);
                                LoadNativeOnBordingScreenSecond(context, onOff, nativeId, nativeAdLoadSucessCallBack);
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ////////////////////////////// native onbording three ////////////////////////////////////////

    public ShimmerFrameLayout shimmer_container_bannerThird;

    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForIntroScreenThird;
    public RelativeLayout rlNativeAdsContainerForCardThemeThird = null;

    public void LoadNativeOnBordingScreenThird(Context context, String onOff, String nativeAdId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForCardThemeThird != null && shimmer_container_bannerThird != null) {
                shimmer_container_bannerThird.setVisibility(View.GONE);
                rlNativeAdsContainerForCardThemeThird.setVisibility(View.GONE);
            }

            return;
        }
        Log.d("VideoEditingModel", "LoadNativeOnBordingScreenThird ");
        try {
            if (admobAndFbNativeAdMediationForIntroScreenThird == null) {
                int mediationFlag = 0;
                admobAndFbNativeAdMediationForIntroScreenThird = new NativeAdsBigForHomeTheme(context, nativeAdId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                View adNative = null;
                                Log.d("LoadNativeAdForLanguage", "nativeAdLoadedSuccessfully Intro Third");
                                try {
                                    if (rlNativeAdsContainerForCardThemeThird != null) {
                                        shimmer_container_bannerThird.setVisibility(View.GONE);
                                        rlNativeAdsContainerForCardThemeThird.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForIntroScreenThird != null) {
                                            adNative = admobAndFbNativeAdMediationForIntroScreenThird.getInativeAdObjForThemeList();
                                            if (rlNativeAdsContainerForCardThemeThird != null && adNative != null) {
                                                rlNativeAdsContainerForCardThemeThird.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LoadNativeAdForLanguage", "Intro Third ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LoadNativeAdForLanguage", "Intro Third ad View Set one");
                                                rlNativeAdsContainerForCardThemeThird.removeAllViews();
                                                rlNativeAdsContainerForCardThemeThird.addView(adNative);
                                            } else {
                                                Log.d("LoadNativeAdForLanguage", "Intro Third ad View Not Set");
                                                rlNativeAdsContainerForCardThemeThird.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LoadNativeAdForLanguage", "Intro Third ad View Not Set 1");
                                            shimmer_container_bannerThird.setVisibility(View.GONE);
                                            rlNativeAdsContainerForCardThemeThird.setVisibility(View.GONE);
                                        }
                                    } else {
                                        Log.d("LoadNativeAdForLanguage", "Intro Third Load But Container Null Found 1");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                shimmer_container_bannerThird.setVisibility(View.VISIBLE);
                                rlNativeAdsContainerForCardThemeThird.setVisibility(View.GONE);
                                LoadNativeOnBordingScreenThird(context, onOff, nativeAdId, nativeAdLoadSucessCallBack);
                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                                Log.d("NativeTemplete", "Intro Third nativeAdFailedToLoad");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Native Template For Lang
    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForLangSelect;
    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForLangSelectHighFloor;
    public String isHighFloorNativeLoadedForLang = "-1";
    public String isNativeLoadedForLang = "-1";
    public boolean isLangNativeAdsLoaded;
    public RelativeLayout rlNativeAdsContainerForLangScreenOne = null;

    public void LoadHighFloorNativeLangugeSelect(Context context, String onOff, String highFloorAdMobId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        isLangNativeAdsLoaded = false;
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForLangScreenOne != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);
            }
            return;
        }

        Log.d("LangNativeAd", "LoadHighFloorNativeVideoLangSelect ");
        try {
            if (admobAndFbNativeAdMediationForLangSelectHighFloor == null) {
                int mediationFlag = 0;
                admobAndFbNativeAdMediationForLangSelectHighFloor = new NativeAdsBigForHomeTheme(context, highFloorAdMobId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                isHighFloorNativeLoadedForLang = "1";
//                                LoadNativeOnBordingScreenOne(context);
                                isLangNativeAdsLoaded = true;


                                View adNative = null;


                                Log.d("LangNativeAd", "High Floor Lang Native nativeAdLoadedSuccessfully");

                                try {
                                    if (rlNativeAdsContainerForLangScreenOne != null) {
                                        shimmer_container_banner.setVisibility(View.GONE);
                                        rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForLangSelectHighFloor != null) {
                                            adNative = admobAndFbNativeAdMediationForLangSelectHighFloor.getInativeAdObjForThemeList();
                                            if (rlNativeAdsContainerForLangScreenOne != null && adNative != null) {
                                                rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LangNativeAd", "ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LangNativeAd", "ad View Set one");
                                                rlNativeAdsContainerForLangScreenOne.removeAllViews();
                                                rlNativeAdsContainerForLangScreenOne.addView(adNative);

                                            } else {
                                                Log.d("LangNativeAd", "ad View Not Set");
                                                rlNativeAdsContainerForLangScreenOne.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LangNativeAd", "ad View Not Set 1");
                                        }
                                    } else {
                                        Log.d("LangNativeAd", "High Floor Lang Native Load But Container Null Found 1");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
//                                LoadNativeOnBordingScreenOne(context);
                                isLangNativeAdsLoaded = false;

                                Log.d("LangNativeAd", "High Floor Native onAdFailedToLoad for lang Selection");
                                isHighFloorNativeLoadedForLang = "0";
                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
//                                LoadNativeVideoLangSelect(context);

                                Log.d("LangNativeAd", "nativeAdFailedToLoad For High Floor Lang Native ");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadNativeLangugeSelect(Context context, String onOff, String nativeIdOne, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        isLangNativeAdsLoaded = false;
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForLangScreenOne != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);
            }

            return;
        }

        Log.d("LangNativeAd", "Normal Floor Lang Native  Call");
        try {
            if (admobAndFbNativeAdMediationForLangSelect == null) {
                int mediationFlag = 0;
                admobAndFbNativeAdMediationForLangSelect = new NativeAdsBigForHomeTheme(context, nativeIdOne,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                isNativeLoadedForLang = "1";
//                                LoadNativeOnBordingScreenOne(context);
                                isLangNativeAdsLoaded = true;
                                Log.d("LangNativeAd", "Normal Floor Lang Native nativeAdLoadedSuccessfully");
                                View adNative = null;


                                if (isHighFloorNativeLoadedForLang.equalsIgnoreCase("0")) {
                                    try {
                                        if (rlNativeAdsContainerForLangScreenOne != null) {
                                            shimmer_container_banner.setVisibility(View.GONE);
                                            rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);

                                            if (admobAndFbNativeAdMediationForLangSelect != null) {
                                                adNative = admobAndFbNativeAdMediationForLangSelect.getInativeAdObjForThemeList();
                                                if (rlNativeAdsContainerForLangScreenOne != null && adNative != null) {
                                                    rlNativeAdsContainerForLangScreenOne.setVisibility(View.VISIBLE);
                                                    if (adNative.getParent() != null) {
                                                        Log.d("LangNativeAd", "ad View Set");
                                                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                    }
                                                    Log.d("LangNativeAd", "ad View Set one");
                                                    rlNativeAdsContainerForLangScreenOne.removeAllViews();
                                                    rlNativeAdsContainerForLangScreenOne.addView(adNative);
                                                } else {
                                                    Log.d("LangNativeAd", "ad View Not Set");
                                                    rlNativeAdsContainerForLangScreenOne.setVisibility(View.GONE);
                                                }
                                            } else {
                                                //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                                Log.d("LangNativeAd", "ad View Not Set 1");
                                                shimmer_container_banner.setVisibility(View.GONE);
                                                rlNativeAdsContainerForLangScreenOne.setVisibility(View.GONE);
                                            }
                                        } else {
                                            Log.d("LangNativeAd", "Normal Floor Lang Native Load But Container Null Found 1");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    Log.d("LangNativeAd", "Normal Floor Lang Native Load But High Floor Still Loading");
                                }
                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);

                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                isNativeLoadedForLang = "0";
//                                LoadNativeOnBordingScreenOne(context);
                                isLangNativeAdsLoaded = false;
                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                                Log.d("LangNativeAd", "Normal Floor Lang Native nativeAdFailedToLoad");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {

        }
    }


    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForLangSelectTwo;
    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForLangSelectHighFloorTwo;
    public String isHighFloorNativeLoadedForLangTwo = "-1";
    public String isNativeLoadedForLangTwo = "-1";

    public RelativeLayout rlNativeAdsContainerForLangScreenTwo = null;

    public void LoadHighFloorNativeLangugeSelectTwo(Context context, String onOff, String highFloorAdMobId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForLangScreenTwo != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
            }
            return;
        }


        Log.d("LangNativeAd", "LoadHighFloorNativeVideoLangSelect Two");
        try {
            if (admobAndFbNativeAdMediationForLangSelectHighFloorTwo == null) {

                admobAndFbNativeAdMediationForLangSelectHighFloorTwo = new NativeAdsBigForHomeTheme(context, highFloorAdMobId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                isHighFloorNativeLoadedForLangTwo = "1";
//                                LoadNativeOnBordingScreenSecond(context);


                                Log.d("LangNativeAd", "High Floor Lang Two Native nativeAdLoadedSuccessfully");
                                View adNative = null;
                                try {
                                    if (rlNativeAdsContainerForLangScreenTwo != null) {
                                        shimmer_container_banner.setVisibility(View.GONE);
                                        rlNativeAdsContainerForLangScreenTwo.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForLangSelectHighFloorTwo != null) {
                                            adNative = admobAndFbNativeAdMediationForLangSelectHighFloorTwo.getInativeAdObjForThemeList();
                                            if (rlNativeAdsContainerForLangScreenTwo != null && adNative != null) {
                                                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LangNativeAd", "ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LangNativeAd", "ad View Set one");
                                                rlNativeAdsContainerForLangScreenTwo.removeAllViews();
                                                rlNativeAdsContainerForLangScreenTwo.addView(adNative);
                                            } else {
                                                Log.d("LangNativeAd", "ad View Not Set");
                                                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LangNativeAd", "ad View Not Set 1");
                                        }
                                    } else {
                                        Log.d("LangNativeAd", "High Floor Lang Two Native Load But Container Null Found 1");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);

                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {


                                Log.d("LangNativeAd", "High Floor Native onAdFailedToLoad for lang Two Selection");
                                isHighFloorNativeLoadedForLangTwo = "0";
//                                LoadNativeOnBordingScreenSecond(context);
                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();

                                Log.d("LangNativeAd", "nativeAdFailedToLoad For High Floor Lang Two Native ");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            } else {
                Log.d("LangNativeAd", "LoadHighFloorNativeVideoLangSelect Two call But Laready Initilize");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadNativeLangugeSelectTwo(Context context, String onOff, String nativeadMobId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rlNativeAdsContainerForLangScreenTwo != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
            }
            return;
        }

        Log.d("LangNativeAd", "Normal Floor Lang Two Native  Call");
        try {
            if (admobAndFbNativeAdMediationForLangSelectTwo == null) {
                int mediationFlag = 0;
                admobAndFbNativeAdMediationForLangSelectTwo = new NativeAdsBigForHomeTheme(context, nativeadMobId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                isNativeLoadedForLangTwo = "1";

                                Log.d("LangNativeAd", "Normal Floor Lang Two Native nativeAdLoadedSuccessfully");
                                View adNative = null;
                                if (isHighFloorNativeLoadedForLangTwo.equalsIgnoreCase("0")) {
                                    try {
                                        if (rlNativeAdsContainerForLangScreenTwo != null) {
                                            shimmer_container_banner.setVisibility(View.GONE);
                                            rlNativeAdsContainerForLangScreenTwo.setVisibility(View.VISIBLE);

                                            if (admobAndFbNativeAdMediationForLangSelectTwo != null) {
                                                adNative = admobAndFbNativeAdMediationForLangSelectTwo.getInativeAdObjForThemeList();
                                                if (rlNativeAdsContainerForLangScreenTwo != null && adNative != null) {
                                                    rlNativeAdsContainerForLangScreenTwo.setVisibility(View.VISIBLE);
                                                    if (adNative.getParent() != null) {
                                                        Log.d("LangNativeAd", "ad View Set");
                                                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                    }
                                                    Log.d("LangNativeAd", "ad View Set one");
                                                    rlNativeAdsContainerForLangScreenTwo.removeAllViews();
                                                    rlNativeAdsContainerForLangScreenTwo.addView(adNative);
                                                } else {
                                                    Log.d("LangNativeAd", "ad View Not Set");
                                                    rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
                                                }
                                            } else {
                                                //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                                Log.d("LangNativeAd", "ad View Not Set 1");
                                                shimmer_container_banner.setVisibility(View.GONE);
                                                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
                                            }
                                        } else {
                                            Log.d("LangNativeAd", "Normal Floor Lang Two Native Load But Container Null Found 1");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.d("LangNativeAd", "Normal Floor Lang Two Native Load But High Floor Still Loading");
                                }
                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);

                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                isNativeLoadedForLang = "0";
                                Log.d("LangNativeAd", "Normal Floor Lang Two Native nativeAdFailedToLoad");
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShowLangugeOne(Context context, String onOff, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme) {

        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            shimmerContainerBanner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {
            View adNative;
            shimmer_container_banner = shimmerContainerBanner;
            android.util.Log.d("LangNativeAd", "=====Native Ads For Lang=====" + isHighFloorNativeLoadedForLang);
            if (admobAndFbNativeAdMediationForLangSelectHighFloor != null &&
                    isHighFloorNativeLoadedForLang.equalsIgnoreCase("1")) {
                android.util.Log.d("LangNativeAd", "=====High Floor Native Ads For Lang Display=====");

                adNative = admobAndFbNativeAdMediationForLangSelectHighFloor.getInativeAdObjForThemeList();

                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                    shimmerContainerBanner.setVisibility(View.GONE);
                    if (adNative.getParent() != null) {
                        android.util.Log.d("LangNativeAd", "ad View Set");
                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                    }
                    android.util.Log.d("LangNativeAd", "ad View Set one");
                    rlNativeAdsContainerForCardTheme.removeAllViews();
                    rlNativeAdsContainerForCardTheme.addView(adNative);

                } else {
                    android.util.Log.d("LangNativeAd", "ad View Not Set");
                }

            } else if (admobAndFbNativeAdMediationForLangSelect != null &&
                    isHighFloorNativeLoadedForLang.equalsIgnoreCase("-1") &&
                    isNativeLoadedForLang.equalsIgnoreCase("1")) {
                android.util.Log.d("LangNativeAd", "=====Normal Native Ads For Lang Display=====");

                adNative = admobAndFbNativeAdMediationForLangSelect.getInativeAdObjForThemeList();

                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                    shimmer_container_banner.setVisibility(View.GONE);
                    if (adNative.getParent() != null) {
                        android.util.Log.d("LangNativeAd", "ad View Set");
                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                    }
                    android.util.Log.d("LangNativeAd", "ad View Set one");
                    rlNativeAdsContainerForCardTheme.removeAllViews();
                    rlNativeAdsContainerForCardTheme.addView(adNative);

                } else {
                    android.util.Log.d("LangNativeAd", "ad View Not Set");
                }
            } else {
                android.util.Log.d("LangNativeAd", "=====Native Ads Not Loaded, wait for display=====");
                rlNativeAdsContainerForLangScreenOne = rlNativeAdsContainerForCardTheme;
            }

        }
    }

    public void ShowLangugeTwo(Context context, String onOff, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme) {

        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            shimmerContainerBanner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {

            View adNative;
            shimmer_container_banner = shimmerContainerBanner;

            android.util.Log.d("LangNativeAd", "=====Native Ads For Lang Two=====");

            if (admobAndFbNativeAdMediationForLangSelectHighFloorTwo != null &&
                    isHighFloorNativeLoadedForLangTwo.equalsIgnoreCase("1")) {
                android.util.Log.d("LangNativeAd", "=====High Floor Native Ads For Lang Two Display=====");

                adNative = admobAndFbNativeAdMediationForLangSelectHighFloorTwo.getInativeAdObjForThemeList();

                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                    shimmerContainerBanner.setVisibility(View.GONE);
                    if (adNative.getParent() != null) {
                        android.util.Log.d("LangNativeAd", "ad View Set");
                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                    }
                    android.util.Log.d("LangNativeAd", "ad View Set one");
                    rlNativeAdsContainerForCardTheme.removeAllViews();
                    rlNativeAdsContainerForCardTheme.addView(adNative);

                } else {
                    android.util.Log.d("LangNativeAd", "ad View Not Set");
                }

            } else if (admobAndFbNativeAdMediationForLangSelectTwo != null &&
                    isHighFloorNativeLoadedForLangTwo.equalsIgnoreCase("-1") &&
                    isNativeLoadedForLangTwo.equalsIgnoreCase("1")) {
                android.util.Log.d("LangNativeAd", "=====Normal Native Ads For Lang Two Display=====");

                adNative = admobAndFbNativeAdMediationForLangSelectTwo.getInativeAdObjForThemeList();

                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);
                    shimmerContainerBanner.setVisibility(View.GONE);
                    if (adNative.getParent() != null) {
                        android.util.Log.d("LangNativeAd", "ad View Set");
                        ((ViewGroup) adNative.getParent()).removeView(adNative);
                    }
                    android.util.Log.d("LangNativeAd", "ad View Set one");
                    rlNativeAdsContainerForCardTheme.removeAllViews();
                    rlNativeAdsContainerForCardTheme.addView(adNative);

                } else {
                    android.util.Log.d("LangNativeAd", "ad View Not Set");
                }
            } else {
                android.util.Log.d("LangNativeAd", "=====Lang Two Native Ads Not Loaded, wait for display=====");
                rlNativeAdsContainerForLangScreenTwo = rlNativeAdsContainerForCardTheme;
            }
        }

    }


    //Native Template For End

    /////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////// Native Start Screen ////////////////////////////////////////
    public NativeAdsBigForHomeTheme admobAndFbNativeAdMediationForStartScreen;
    RelativeLayout rltNativeStartView;

    public void LoadNativeStartScreen(Context context, String onOff, String startNativeadMobId, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
            if (rltNativeStartView != null && shimmer_container_banner != null) {
                shimmer_container_banner.setVisibility(View.GONE);
                rlNativeAdsContainerForLangScreenTwo.setVisibility(View.GONE);
            }
            return;
        }
        Log.d("LoadNativeStartScreen", "LoadNativeStartScreen ");
        try {
            if (admobAndFbNativeAdMediationForStartScreen == null) {
                admobAndFbNativeAdMediationForStartScreen = new NativeAdsBigForHomeTheme(context, startNativeadMobId,
                        new NativeAdLoadCallback() {
                            @Override
                            public void nativeAdLoadedSuccessfully(String eventName) {
                                Log.d("LoadNativeStartScreen", "nativeAdLoadedSuccessfully");

                                View adNative = null;
                                try {
                                    if (rltNativeStartView != null) {
                                        shimmer_container_banner.setVisibility(View.GONE);
                                        rltNativeStartView.setVisibility(View.VISIBLE);

                                        if (admobAndFbNativeAdMediationForStartScreen != null) {
                                            adNative = admobAndFbNativeAdMediationForStartScreen.getInativeAdObjForThemeList();
                                            if (rltNativeStartView != null && adNative != null) {
                                                rltNativeStartView.setVisibility(View.VISIBLE);
                                                if (adNative.getParent() != null) {
                                                    Log.d("LoadNativeStartScreen", "ad View Set");
                                                    ((ViewGroup) adNative.getParent()).removeView(adNative);
                                                }
                                                Log.d("LoadNativeAdForLanguage", "ad View Set one");
                                                rltNativeStartView.removeAllViews();
                                                rltNativeStartView.addView(adNative);
                                            } else {
                                                Log.d("LoadNativeStartScreen", "ad View Not Set");
                                                rltNativeStartView.setVisibility(View.GONE);
                                            }
                                        } else {
                                            //   MyApplication.getInstance().LoadNativeAdForHomeTheme(rlNativeAdsContainerForCardTheme);
                                            Log.d("LoadNativeStartScreen", "ad View Not Set 1");
                                            shimmer_container_banner.setVisibility(View.GONE);
                                            rltNativeStartView.setVisibility(View.GONE);
                                        }
                                    } else {
                                        Log.d("LoadNativeStartScreen", "Load But Container Null Found 1");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                            }

                            @Override
                            public void nativeAdFailedToLoad(String msg) {
                                admobAndFbNativeAdMediationForStartScreen = null;
                                Log.d("LoadNativeStartScreen", "nativeAdFailedToLoad");
                                if (rltNativeStartView != null) {
                                    shimmer_container_banner.setVisibility(View.VISIBLE);
                                    rltNativeStartView.setVisibility(View.GONE);
                                }
                                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                                LoadNativeStartScreen(context, onOff, startNativeadMobId, nativeAdLoadSucessCallBack);
                            }

                            @Override
                            public void nativeAdClick() {

                            }

                            @Override
                            public void nativeAdClickError(String msg) {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ShowStartScreen(Context context, String onOff, String startScreenId, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {


        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off")
        ) {
            shimmer_container_banner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {
            shimmer_container_banner = shimmerContainerBanner;
            View adNative;


            if (admobAndFbNativeAdMediationForStartScreen != null) {
                android.util.Log.d("showStartScreen", " if ");
                adNative = admobAndFbNativeAdMediationForStartScreen.getInativeAdObjForThemeList();
                if (adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerContainerBanner.setVisibility(View.GONE);
                            rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {

                                android.util.Log.d("showStartScreen", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            }
                            android.util.Log.d("showStartScreen", "ad View Set one");
                            rlNativeAdsContainerForCardTheme.removeAllViews();
                            rlNativeAdsContainerForCardTheme.addView(adNative);
                        }
                    }, 100);
                    nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                } else {
                    android.util.Log.d("showStartScreen", " else  else");
                    android.util.Log.d("showStartScreen", "ad View Not Set");
                    shimmerContainerBanner.setVisibility(View.VISIBLE);
                    rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                }
            } else {
                android.util.Log.d("showStartScreen", " else ");
                this.rltNativeStartView = rlNativeAdsContainerForCardTheme;
                LoadNativeStartScreen(context, onOff, startScreenId, nativeAdLoadSucessCallBack);
                android.util.Log.d("showStartScreen", "ad View Not Set 1");
            }


        }
    }


    ///////////////////////////// Home Intersial ads /////////////////////////////////

    public AdmobAndFBInterstitialAdsMediation mHomeScreenIntAdsObj;
    public IntCallback callBackForHomeScreen;

    public void LoadInterstitialAdForHomeScreen(Context context) {

        if (adAllOnOff.equals("off")) {
            if (callBackForHomeScreen != null) {
                callBackForHomeScreen.adIntClose();
            }
            return;
        }

        if (AdSDKPref.getInstance(context).getString(AdSDKPref.TAG_INTERSIAL_AD_ON_OFF, "0").equals("off")) {
            if (callBackForHomeScreen != null) {
                callBackForHomeScreen.adIntClose();
            }
            return;
        }

        if (!isNetworkAvailable(context)) {
            if (callBackForHomeScreen != null) {
                callBackForHomeScreen.adIntClose();
            }
            return;
        }

        if (mHomeScreenIntAdsObj != null && mHomeScreenIntAdsObj.isAdsAvailable()) {
            return;
        }

        MyApp.getInstance().EventRegister("Inter_Ad_Requested", new Bundle());

        mHomeScreenIntAdsObj = new AdmobAndFBInterstitialAdsMediation(context, INTER_HOME_ID, new IntCallback() {
            @Override
            public void adIntClose() {
                MyApp.getInstance().EventRegister("Inter_Ad_Closed", new Bundle());
                clickCount = 0;
                callBackForHomeScreen.adIntClose();
                mHomeScreenIntAdsObj = null;
                LoadInterstitialAdForHomeScreen(context);
            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {
                MyApp.getInstance().EventRegister("Inter_Ad_FailedToLoad", new Bundle());
                mHomeScreenIntAdsObj = null;
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

    private AppCompatActivity mHomeScreenActObj;
    AdsWaitingDailog adsWaitingDailog;

    public void ShowIntForHomeScreen(AppCompatActivity mCurrentAct) {
        mHomeScreenActObj = mCurrentAct;


        if (mHomeScreenIntAdsObj != null && mHomeScreenIntAdsObj.isAdsAvailable()) {
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

                        if (mHomeScreenIntAdsObj != null) {
                            mHomeScreenIntAdsObj.ShowMediationAds(mHomeScreenActObj);
                        } else {
                            callBackForHomeScreen.adIntClose();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1500);
        } else {
            LoadInterstitialAdForHomeScreen(mCurrentAct);
        }
    }

    /////////////////////////////////////////// show native ad ////////////////////////////////////////////////


    public void ShowOnBording1(Context context, String onOff, String nativeId, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equals("off")) {
            shimmerContainerBanner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {
            shimmer_container_banner = shimmerContainerBanner;
            View adNative;

            if (admobAndFbNativeAdMediationForIntroScreen != null) {
                adNative = admobAndFbNativeAdMediationForIntroScreen.getInativeAdObjForThemeList();
                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerContainerBanner.setVisibility(View.GONE);
                            rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {
                                android.util.Log.d("showOnBording1", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            }
                            android.util.Log.d("showOnBording1", "ad View Set one");
                            rlNativeAdsContainerForCardTheme.removeAllViews();
                            rlNativeAdsContainerForCardTheme.addView(adNative);
                        }
                    }, 100);
                } else {
                    android.util.Log.d("showOnBording1", "ad View Not Set");
                    rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                }
            } else {
                this.rlNativeAdsContainerForCardTheme = rlNativeAdsContainerForCardTheme;
                LoadNativeOnBordingScreenOne(context, onOff, nativeId, nativeAdLoadSucessCallBack);
                android.util.Log.d("showOnBording1", "ad View Not Set 1");
            }
        }

    }

    public void ShowOnBording2(Context context, String onOff, String nativeId, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equals("off")) {
            shimmerContainerBanner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {
            shimmer_container_bannerSecond = shimmerContainerBanner;
            View adNative;

            if (admobAndFbNativeAdMediationForIntroScreenSecond != null) {
                adNative = admobAndFbNativeAdMediationForIntroScreenSecond.getInativeAdObjForThemeList();
                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerContainerBanner.setVisibility(View.GONE);
                            rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {
                                android.util.Log.d("showOnBording2", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            }
                            android.util.Log.d("showOnBording2", "ad View Set one");
                            rlNativeAdsContainerForCardTheme.removeAllViews();
                            rlNativeAdsContainerForCardTheme.addView(adNative);
                        }
                    }, 100);

                    nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                } else {
                    android.util.Log.d("showOnBording2", "ad View Not Set");
                    rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                }
            } else {
                this.rlNativeAdsContainerForCardThemeSecond = rlNativeAdsContainerForCardTheme;
                LoadNativeOnBordingScreenSecond(context, onOff, nativeId, nativeAdLoadSucessCallBack);
                android.util.Log.d("showOnBording2", "ad View Not Set 1");
            }
        }

    }

    public void ShowOnBording3(Context context, String onOff, String nativeId, ShimmerFrameLayout shimmerContainerBanner, RelativeLayout rlNativeAdsContainerForCardTheme, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equals("off")) {
            shimmerContainerBanner.setVisibility(View.GONE);
            rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
        } else {
            shimmer_container_bannerThird = shimmerContainerBanner;
            View adNative;

            if (admobAndFbNativeAdMediationForIntroScreenThird != null) {
                adNative = admobAndFbNativeAdMediationForIntroScreenThird.getInativeAdObjForThemeList();
                if (rlNativeAdsContainerForCardTheme != null && adNative != null) {

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shimmerContainerBanner.setVisibility(View.GONE);
                            rlNativeAdsContainerForCardTheme.setVisibility(View.VISIBLE);

                            if (adNative.getParent() != null) {
                                android.util.Log.d("showOnBording3", "ad View Set");
                                ((ViewGroup) adNative.getParent()).removeView(adNative);
                            }
                            android.util.Log.d("showOnBording3", "ad View Set one");
                            rlNativeAdsContainerForCardTheme.removeAllViews();
                            rlNativeAdsContainerForCardTheme.addView(adNative);
                        }
                    }, 100);


                } else {
                    android.util.Log.d("showOnBording3", "ad View Not Set");
                    rlNativeAdsContainerForCardTheme.setVisibility(View.GONE);
                }
                nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
            } else {
                this.rlNativeAdsContainerForCardThemeThird = rlNativeAdsContainerForCardTheme;
                LoadNativeOnBordingScreenThird(context, onOff, nativeId, nativeAdLoadSucessCallBack);
                android.util.Log.d("showOnBording3", "ad View Not Set 1");
            }
        }

    }


    ///////////////////////////////////////// Native Medium Ads Load /////////////////////////////////////////////////////


    public NativeMediumTemplate nativeMediumTemplate;


    public void LoadNativeMedium(Context context, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
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
                            MyApp.getInstance().EventRegister(eventName + "home_big_v97", new Bundle());

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
                            nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                            LoadNativeMedium(context, lifecycleOwner, adMobNativeId, isReloadAds, onOff, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "nativeAdFailedToLoad For OnBoarding Screen One");
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

    public void ShowNativeMedium(Context context, LifecycleOwner lifecycleOwner, boolean isRelaoadAds, String adMobNativeId, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {

        if (adAllOnOff.equals("off") || onOff.equals("off")) {
            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
        } else {

            View adNative;

            if (nativeMediumTemplate != null) {
                adNative = nativeMediumTemplate.getNativeTamplate();
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
                            nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                        }
                    }, 100);


                } else {
                    android.util.Log.d("showNativeMedium", "ad View Not Set");

                }
            } else {

                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();

                LoadNativeMedium(context, lifecycleOwner, adMobNativeId, isRelaoadAds, onOff, nativeAdLoadSucessCallBack);

//                LoadNativeMedium(context, adMobNativeId, fbNativeId, allOnOff, onOff, mediationFlag, nativeAdLoadSucessCallBack);
                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
            }
        }

    }


    ///////////////////////////////////////// Native Custom Ads Load /////////////////////////////////////////////////////


    public NativeCustomTemplate nativeCustomTemplate;
    public RelativeLayout rlNativeAdsAll = null;
    public ShimmerFrameLayout shimerNativeLayoutAll = null;


    public void setLayoutCustom(RelativeLayout relativeLayout, ShimmerFrameLayout shimmerFrameLayout) {
        rlNativeAdsAll = relativeLayout;
        shimerNativeLayoutAll = shimmerFrameLayout;
    }

    public void LoadNativeCustom(Context context, int layout, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {
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
                                        adNative = nativeCustomTemplate.getInativeAdObjForThemeList();
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
                            nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                        }

                        @Override
                        public void nativeAdFailedToLoad(String msg) {
                            shimerNativeLayoutAll.setVisibility(View.VISIBLE);
                            rlNativeAdsAll.setVisibility(View.GONE);
                            nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                            LoadNativeCustom(context, layout, lifecycleOwner, adMobNativeId, true, onOff, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "nativeAdFailedToLoad For OnBoarding Screen One");
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
        if (adAllOnOff.equals("off") || onOff.equals("off")) {
            if (shimerNativeLayoutAll != null && rlNativeAdsAll != null) {
                shimerNativeLayoutAll.setVisibility(View.GONE);
                rlNativeAdsAll.setVisibility(View.GONE);
            }
        } else {
            View adNative;
            if (nativeCustomTemplate != null) {
                adNative = nativeCustomTemplate.getInativeAdObjForThemeList();
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
                            nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                        }
                    }, 100);
                } else {
                    android.util.Log.d("ShowNativeCustom", "ad View Not Set");
                }
            } else {
                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                LoadNativeCustom(context, layout, lifecycleOwner, adMobNativeId, isRelaoadAds, onOff, nativeAdLoadSucessCallBack);
                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
            }
        }

    }


    ///////////////////////////////////////// Native Large Ads Load /////////////////////////////////////////////////////


    public NativeAdsBig nativeBigTemplate;


    public void LoadNativeBig(Context context, LifecycleOwner lifecycleOwner, String adMobNativeId, boolean isReloadAds, String onOff, NativeAdLoadSucessCallBack nativeAdLoadSucessCallBack) {
        if (adAllOnOff.equals("off") || onOff.equalsIgnoreCase("off") || !isNetworkAvailable(context)) {

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
                                            nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
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
                            nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                            LoadNativeBig(context, lifecycleOwner, adMobNativeId, true, onOff, nativeAdLoadSucessCallBack);
                            Log.d("LoadNativeMedium", "nativeAdFailedToLoad For OnBoarding Screen One");
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

        if (adAllOnOff.equals("off") || onOff.equals("off")) {
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
                            nativeAdLoadSucessCallBack.nativeAdLoaded(adNative);
                        }
                    }, 100);
                } else {
                    android.util.Log.d("showNativeMedium", "ad View Not Set");

                }
            } else {
                nativeAdLoadSucessCallBack.nativeAdFailedToLoad();
                LoadNativeBig(context, lifecycleOwner, adMobNativeId, isRelaoadAds, onOff, nativeAdLoadSucessCallBack);

                android.util.Log.d("showNativeMedium", "ad View Not Set 1");
            }
        }

    }


    //////////////////////////////////// other intersioal Ad /////////////////////////////////


    public AdmobAndFBInterstitialAdsMediation mIntAdsObj;
    public IntCallback intCallback;

    public void PreLoadInterstitialAd(Context context, String adMobIntId, String onOff) {

        if (adAllOnOff.equals("off")) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (onOff.equals("off")) {
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

        if (mIntAdsObj != null && mIntAdsObj.isAdsAvailable()) {
            return;
        }


        MyApp.getInstance().EventRegister("Inter_Ad_Requested", new Bundle());

        mIntAdsObj = new AdmobAndFBInterstitialAdsMediation(context, adMobIntId, new IntCallback() {
            @Override
            public void adIntClose() {
                MyApp.getInstance().EventRegister("Inter_Ad_Closed", new Bundle());
                clickCount = 0;
                intCallback.adIntClose();
                mIntAdsObj = null;
                PreLoadInterstitialAd(context, adMobIntId, onOff);
            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {
                MyApp.getInstance().EventRegister("Inter_Ad_FailedToLoad", new Bundle());
                mIntAdsObj = null;
                PreLoadInterstitialAd(context, adMobIntId, onOff);
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

    private AppCompatActivity mOtherScreenActObj;

    public void ShowIntForOtherScreen(AppCompatActivity mCurrentAct, String adMobIntId, String onOff) {
        mOtherScreenActObj = mCurrentAct;


        if (mIntAdsObj != null && mIntAdsObj.isAdsAvailable()) {
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

                        if (mIntAdsObj != null) {
                            mIntAdsObj.ShowMediationAds(mOtherScreenActObj);
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


//    AppOpenManager appOpenManager;
//
//    public void initAppOpenSplash(Application activity) {
////        MyApplication.getInstance().EventRegister("Splash_App_Open_Request", new Bundle());
//        appOpenManager = new AppOpenManager(activity);
//    }

    //////////////////////////////// Baner Load ////////////////////////////////


//    public void LoadBanner(Context context, String adBannerId, String fbBannerId, String allAdOnOff, String adonOff, String mediationFlag, LinearLayout llAdContainer, FrameLayout ad_view_container, String isCollapsingAd) {
//
//
//        if (allAdOnOff.equals("off") || adonOff.equals("off") || !isNetworkAvailable(context)) {
//            llAdContainer.setVisibility(View.GONE);
//            ad_view_container.setVisibility(View.GONE);
//        } else {
//            AdmobAndFbBannerMediation adMediation = new AdmobAndFbBannerMediation(context, adBannerId, fbBannerId, "", mediationFlag, isCollapsingAd);
//            View adptiveBannerObj = adMediation.getAdpativeBanner();
//            if (adptiveBannerObj != null) {
//                ad_view_container.removeAllViews();
//                ad_view_container.addView(adptiveBannerObj);
//            }
//        }
//    }

    public void LoadBanner(Context context, LifecycleOwner lifecycleOwner, String adBannerId, String adonOff, LinearLayout llAdContainer, FrameLayout ad_view_container, String isCollapsingAd, boolean isReloading) {

        if (adAllOnOff.equals("off") || adonOff.equals("off") || !isNetworkAvailable(context)) {
            llAdContainer.setVisibility(View.GONE);
            ad_view_container.setVisibility(View.GONE);
        } else {
            AdmobAndFbBannerMediation adMediation = new AdmobAndFbBannerMediation(context, lifecycleOwner, adBannerId, isCollapsingAd, isReloading);
            View adptiveBannerObj = adMediation.getAdpativeBanner();
            if (adptiveBannerObj != null) {
                ad_view_container.removeAllViews();
                ad_view_container.addView(adptiveBannerObj);
            }
        }
    }


    public AppOpenManagerSplash appOpenManagerSplash;

    public void initAppOpenSplash(Activity activity, String appOpenId, IntCallback intCallback) {
        if (adAllOnOff.equals("off")) {
            intCallback.adIntClose();
            return;
        }
        appOpenManagerSplash = new AppOpenManagerSplash(activity, appOpenId, intCallback);
    }


    public AppOpenManager appOpenManager;

    public void initAppOpen() {
        if (adAllOnOff.equals("off")) {
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

        if (adAllOnOff.equals("off")) {
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


    public void SameTimeShowInterstitialAd(Context context, String adMobIntId, String onOff, IntCallback intCallback) {

        if (adAllOnOff.equals("off")) {
            if (intCallback != null) {
                intCallback.adIntClose();
            }
            return;
        }

        if (onOff.equals("off")) {
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

        mIntAdsObj = new AdmobAndFBInterstitialAdsMediation(context, adMobIntId, new IntCallback() {
            @Override
            public void adIntClose() {
                MyApp.getInstance().EventRegister("Inter_Ad_Closed", new Bundle());
                clickCount = 0;
                intCallback.adIntClose();
                mIntAdsObj = null;
            }

            @Override
            public void adIntOpen() {

            }

            @Override
            public void adIntFailedToLoad() {
                MyApp.getInstance().EventRegister("Inter_Ad_FailedToLoad", new Bundle());
                mIntAdsObj = null;
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
                if (mIntAdsObj != null) {
                    mIntAdsObj.mInterstitialAdApp.show((AppCompatActivity) context);
                } else {
                    intCallback.adIntClose();
                }
            }
        });
    }
}
