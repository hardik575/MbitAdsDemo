package com.mbitadsdk.rewardint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.mbitadsdk.Log;

import java.util.logging.Handler;

import com.mbitadsdk.AdsWaitingDailog;

public class RewardedInterstitialAds {

    private RewardedAd rewardedInterstitialAd;
    private String TAG = "RewardedInterstitialAd";
    private boolean isSuccessRewdred;
    private String admobRewardId;
    private Activity mActivity;
    private RewaredIntCallback rewaredIntCallback;
    AdsWaitingDailog adsWaitingDailog;

    public RewardedInterstitialAds(final Context cntx, String admobRewardId, RewaredIntCallback intCallback) {
        isSuccessRewdred = false;
        this.admobRewardId = admobRewardId;
        mActivity = (Activity) cntx;
        rewaredIntCallback = intCallback;

        try {
            adsWaitingDailog = new AdsWaitingDailog();
            adsWaitingDailog.show(((AppCompatActivity) mActivity).getSupportFragmentManager(), "adsWaitingDailog");
        } catch (Exception e) {
        }
        LoadRewardedIntAds();
    }

    public void LoadRewardedIntAds() {
        AdRequest request = new AdRequest.Builder().build();
        // Load the rewarded ad with the request.
        RewardedAd.load(mActivity, admobRewardId, request, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedInterstitialAd1) {
//                super.onAdLoaded(rewardedInterstitialAd);
                rewardedInterstitialAd = rewardedInterstitialAd1;
                Log.e(TAG, "onAdLoaded");
                rewaredIntCallback.rewaredIntLoaded();
                rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    /** Called when the ad failed to show full screen content. */
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        Log.i(TAG, "onAdFailedToShowFullScreenContent");
                        rewaredIntCallback.rewaredIntCloseWithOutReward();
                    }

                    /** Called when ad showed the full screen content. */
                    @Override
                    public void onAdShowedFullScreenContent() {

                        Log.i(TAG, "onAdShowedFullScreenContent");
                    }

                    /** Called when full screen content is dismissed. */
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.i(TAG, "onAdDismissedFullScreenContent");
                        if (isSuccessRewdred) {
                            rewaredIntCallback.rewaredIntCloseWithReward();
                        } else {
                            rewaredIntCallback.rewaredIntCloseWithOutReward();
                        }

                    }
                });


                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adsWaitingDailog != null) {
                            adsWaitingDailog.dismiss();
                        }
                        rewardedInterstitialAd.show(mActivity, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();
                                Log.d(TAG, "OnUserEarnedReward : " + rewardAmount + " " + rewardType);
                                isSuccessRewdred = true;
                            }
                        });
                    }
                }, 1000);

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                rewardedInterstitialAd = null;
                if (adsWaitingDailog != null) {
                    adsWaitingDailog.dismiss();
                }
                Log.e(TAG, "onAdFailedToLoad");
                Log.e(TAG, "onAdFailedToLoad" + loadAdError.getMessage());
                rewaredIntCallback.rewaredIntFailedToLoad();
            }
        });
    }

//    public void mShowRewsredInt(){
//        if (rewardedInterstitialAd != null) {
//            Log.i(TAG, "RewardedInterstitialAd Not Null");
//            rewardedInterstitialAd.show(mActivity, new OnUserEarnedRewardListener() {
//                @Override
//                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                    Log.i(TAG, "onUserEarnedReward");
//                    isSuccessRewdred = true;
//                }
//            });
//        } else {
//            Log.i(TAG, "rewardedInterstitialAd null");
//        }
//    }
}
