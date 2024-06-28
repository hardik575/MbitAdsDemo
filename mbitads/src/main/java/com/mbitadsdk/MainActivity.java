package com.mbitadsdk;

import static com.mbitadsdk.MbitAds.AD_MOB_HOME_SCREEN_BANNER_ID;
import static com.mbitadsdk.MbitAds.FB_MOB_HOME_SCREEN_BANNER_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mbitadsdk.intad.IntCallback;
import com.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.mbitadsdk.rewardint.RewardedInterstitialAds;
import com.mbitadsdk.rewardint.RewaredIntCallback;
import com.mbitsdk.R;

public class MainActivity extends AppCompatActivity {


    //    RelativeLayout rlNativeAdsContainerForCardTheme;
    RelativeLayout rlNativeAdsContainerForNativeMedium;
    ShimmerFrameLayout shimmer_container_banner;


    String admobId = "ca-app-pub-3940256099942544/2247696110";
    String fbmobId = "YOUR_PLACEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        loadAdaptiveBaner();
//        rlNativeAdsContainerForCardTheme = findViewById(R.id.rlNativeAdsContainerForCardTheme);
        shimmer_container_banner = findViewById(R.id.shimmer_container_banner);

        rlNativeAdsContainerForNativeMedium = findViewById(R.id.rlNativeAdsContainerForNativeMedium);
        MbitAds.getInstance().LoadInterstitialAdForOtherScreen(MainActivity.this, "ca-app-pub-3940256099942544/1033173712", "0");

//        MbitAds.getInstance().setAllAdOnOff("off");
        MbitAds.getInstance().setDebugMode(false);

        MbitAds.getInstance().initAppOpen();


        loadAdaptiveBaner();


//        MbitAds.getInstance().rlNativeAdsCustom = rlNativeAdsContainerForNativeMedium;
//        MbitAds.getInstance().shimmerFrameLayoutNativeAdsCustom = shimmer_container_banner;

        MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);
        MbitAds.getInstance().LoadNativeCustom(MainActivity.this,
                R.layout.native_ad_view_large_card, MainActivity.this, admobId, true, "0", new NativeAdLoadSucessCallBack() {
                    @Override
                    public void nativeAdLoaded(View view) {

                    }

                    @Override
                    public void nativeAdFailedToLoad() {

                    }
                });


//        functionClass.LoadInterstitialAdForHomeScreen(this);


//        mbitAds.showNativeMedium(MainActivity.this, MainActivity.this, true, shimmer_container_banner, rlNativeAdsContainerForNativeMedium, admobId, "0", "0", new NativeAdLoadSucessCallBack() {
//            @Override
//            public void nativeAdLoaded(View view) {
//
//            }
//
//            @Override
//            public void nativeAdFailedToLoad() {
//
//            }
//        });


//        mbitAds.LoadNativeMedium(MainActivity.this, admobId, fbmobId, "0", "0", 0, new NativeAdLoadSucessCallBack() {
//            @Override
//            public void nativeAdLoaded(View view) {
//
//            }
//
//            @Override
//            public void nativeAdFailedToLoad() {
//
//            }
//        });

    }

    private void loadAdaptiveBaner() {
        LinearLayout llAdContainer = findViewById(R.id.llAdContainer);
        FrameLayout ad_view_container = findViewById(R.id.ad_view_container);

        MbitAds.getInstance().LoadBanner(this, AD_MOB_HOME_SCREEN_BANNER_ID, "0", llAdContainer, ad_view_container, "1");

    }

    public void onclick(View view) {

//        functionClass.callBackForHomeScreen = callBackHome;
//        mbitAds.initAppOpenSplash(MainActivity.this, new IntCallback() {
//            @Override
//            public void adIntClose() {
//                Log.e("AAAAAAAAAAA", "adIntClose ==>");
//                startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            }
//
//            @Override
//            public void adIntOpen() {
//
//            }
//
//            @Override
//            public void adIntFailedToLoad() {
//
//            }
//
//            @Override
//            public void adIntClick() {
//
//            }
//
//            @Override
//            public void adIntLoaded() {
//
//            }
//        });

//        MbitAds.getInstance().intCallback = callBackHome;
//        MbitAds.getInstance().ShowIntForOtherScreen(MainActivity.this,"ca-app-pub-3940256099942544/1033173712","0");


//        startActivity(new Intent(MainActivity.this, HomeActivity.class));


//        MbitAds.getInstance().RewardAdLoad(MainActivity.this, "ca-app-pub-3940256099942544/5224354917", new RewaredIntCallback() {
//            @Override
//            public void rewaredIntCloseWithReward() {
//                startActivity(new Intent(MainActivity.this, HomeActivity.class));
//            }
//
//            @Override
//            public void rewaredIntCloseWithOutReward() {
//
//            }
//
//            @Override
//            public void rewaredIntFailedToLoad() {
//
//            }
//
//            @Override
//            public void rewaredIntClick() {
//
//            }
//
//            @Override
//            public void rewaredIntLoaded() {
//
//            }
//        });

        startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }

    private IntCallback callBackHome = new IntCallback() {
        @Override
        public void adIntClose() {

            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }

        @Override
        public void adIntOpen() {

        }

        @Override
        public void adIntFailedToLoad() {

        }

        @Override
        public void adIntClick() {

        }

        @Override
        public void adIntLoaded() {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("MAIN", "onResume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MAIN", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MAIN", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MAIN", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MAIN", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MAIN", "onRestart");
    }
}