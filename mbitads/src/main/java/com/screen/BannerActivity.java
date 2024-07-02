package com.screen;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.mbitadsdk.MbitAds;
import com.ads.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.facebook.shimmer.ShimmerFrameLayout;

import com.mbitsdk.R;


public class BannerActivity extends AppCompatActivity {


    int id = 0;

    LinearLayout llBannnerAds;
    RelativeLayout rltAdView;
    RelativeLayout rltNativeMedium;
    TextView txTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        llBannnerAds = findViewById(R.id.llBannnerAds);
        rltAdView = findViewById(R.id.rltAdView);
        rltNativeMedium = findViewById(R.id.rltNativeMedium);
        txTextView = findViewById(R.id.txtInterSila);


        id = getIntent().getIntExtra("id", 0);
        Log.e("SSSS", "==>" + id);
        if (id == 1) {

            llBannnerAds.setVisibility(View.VISIBLE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.GONE);


            LinearLayout llAdContainer = findViewById(R.id.llAdContainer);
            FrameLayout ad_view_container = findViewById(R.id.ad_view_container);

            MbitAds.getInstance().LoadBanner(this, this, MyApplication.getApplication().bannerId, true, llAdContainer, ad_view_container, false, false);

        } else if (id == 2) {

            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.VISIBLE);
            rltNativeMedium.setVisibility(View.GONE);

            ShimmerFrameLayout shimmer_container_banner = findViewById(R.id.shimmer_container_banner_large);

            RelativeLayout rlNativeAdsContainerForNativeMedium = findViewById(R.id.rlNativeAdsContainerForCardTheme);

            MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);
            MbitAds.getInstance().LoadOnTimeNativeCustom(BannerActivity.this,
                    R.layout.native_ad_view_large_card, BannerActivity.this, "ca-app-pub-3940256099942544/2247696110", true, true, new NativeAdLoadSucessCallBack() {
                        @Override
                        public void onNativeAdLoadedSucessFully(View view) {

                        }

                        @Override
                        public void onNativeAdFailedToLoad() {

                        }
                    });

        } else if (id == 3) {

            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.VISIBLE);


            ShimmerFrameLayout shimmer_container_banner = findViewById(R.id.shimmer_container_banner_medium);

            RelativeLayout rlNativeAdsContainerForNativeMedium = findViewById(R.id.rlNativeAdsContainerForNativeMedium);

            MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);
            MbitAds.getInstance().LoadNativeMedium(BannerActivity.this, BannerActivity.this, "ca-app-pub-3940256099942544/2247696110"
                    , true, true, new NativeAdLoadSucessCallBack() {
                        @Override
                        public void onNativeAdLoadedSucessFully(View view) {

                        }

                        @Override
                        public void onNativeAdFailedToLoad() {

                        }
                    });


        } else if (id == 4) {
            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.GONE);
            txTextView.setVisibility(View.VISIBLE);


        } else if (id == 5) {

            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.VISIBLE);
            rltNativeMedium.setVisibility(View.GONE);

            ShimmerFrameLayout shimmer_container_banner = findViewById(R.id.shimmer_container_banner_large);

            RelativeLayout rlNativeAdsContainerForNativeMedium = findViewById(R.id.rlNativeAdsContainerForCardTheme);

            MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);

            MbitAds.getInstance().ShowForceNativeCustom(BannerActivity.this, new NativeAdLoadSucessCallBack() {
                @Override
                public void onNativeAdLoadedSucessFully(View view) {

                }

                @Override
                public void onNativeAdFailedToLoad() {
                    shimmer_container_banner.setVisibility(View.GONE);
                    rlNativeAdsContainerForNativeMedium.setVisibility(View.GONE);

                }
            });


        } else if (id == 7) {
            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.GONE);
            txTextView.setVisibility(View.VISIBLE);

        } else if (id == 8) {

            llBannnerAds.setVisibility(View.VISIBLE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.GONE);
            LinearLayout llAdContainer = findViewById(R.id.llAdContainer);
            FrameLayout ad_view_container = findViewById(R.id.ad_view_container);
            MbitAds.getInstance().LoadBanner(this, this, MyApplication.getApplication().bannerId, true, llAdContainer, ad_view_container, true, false);

        } else if (id == 9) {
            llBannnerAds.setVisibility(View.GONE);
            rltAdView.setVisibility(View.GONE);
            rltNativeMedium.setVisibility(View.GONE);
            txTextView.setText("Reward Ad Show");
            txTextView.setVisibility(View.VISIBLE);
        }


    }
}