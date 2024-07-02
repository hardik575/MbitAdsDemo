package com.screen;

import android.provider.Settings;

import androidx.lifecycle.MutableLiveData;


import com.ads.mbitadsdk.MyApp;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyApplication extends MyApp {

    private static MyApplication context;
    public String intersialid = "ca-app-pub-3940256099942544/1033173712";
    public String bannerId = "ca-app-pub-3940256099942544/9214589741";
    private StorageCommon storageCommon;

    public static MyApplication getApplication() {
        return context;
    }

    public StorageCommon getStorageCommon() {
        return storageCommon;
    }

    public MutableLiveData<Boolean> isAdCloseSplash = new MutableLiveData<>();


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        storageCommon = new StorageCommon();

    }

}
