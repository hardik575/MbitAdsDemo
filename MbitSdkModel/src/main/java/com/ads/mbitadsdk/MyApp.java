package com.ads.mbitadsdk;

import android.os.Bundle;
import android.provider.Settings;


import androidx.multidex.MultiDexApplication;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyApp extends MultiDexApplication {
    public static MyApp context;
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
        FirebaseApp.initializeApp(context);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }


    public void setDebugMode(boolean isDebug) {
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = md5(android_id).toUpperCase(Locale.getDefault());
        List<String> testDevices = new ArrayList<>();
        testDevices.add(AdRequest.DEVICE_ID_EMULATOR);
        try {
            testDevices.add(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestConfiguration requestConfiguration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDevices).build();
        MobileAds.setRequestConfiguration(requestConfiguration);
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return "";
    }


    public void EventRegister(String event, Bundle bundle) {
        try {
            if (mFirebaseAnalytics != null) {
                mFirebaseAnalytics.logEvent(event, bundle);
            } else {
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                mFirebaseAnalytics.logEvent(event, bundle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    public static void setDemoAdTestType() {
//        if (Log.isDebugMode) {
//            AdInternalSettings.setTestMode(Log.isDebugMode);
//            AdSettings.TestAdType testAdType = AdSettings.TestAdType
//                    .valueOf(AdSettings.TestAdType.DEFAULT.getAdTypeString());
//            AdSettings.setTestAdType(testAdType);
//        }
//    }


}
