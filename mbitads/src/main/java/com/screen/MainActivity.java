package com.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ads.mbitadsdk.MbitAds;
import com.ads.mbitadsdk.intad.IntCallback;
import com.ads.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.ads.mbitadsdk.rewardint.RewaredIntCallback;
import com.mbitsdk.R;


public class MainActivity extends AppCompatActivity {


    Button btnBaner;
    Button btnNativeBig;
    Button btnNativeMedium;
    Button btnInterSial;
    Button btnPreLoadingNativeBig;
    Button btnInterSialFocrc;
    Button btnCollapsingBaner;
    Button btnThreeClickIterSial;
    Button btnRewardAd;
    boolean isPreloadingInt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyApplication.getInstance().initAppOpen();

        MbitAds.getInstance().PreLoadInterstitialAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712", true);

        MbitAds.getInstance().PreLoadingNativeCustom(MainActivity.this,
                R.layout.native_ad_view_large_card, MainActivity.this, false, "ca-app-pub-3940256099942544/2247696110", new NativeAdLoadSucessCallBack() {
                    @Override
                    public void onNativeAdLoadedSucessFully(View view) {
                        Toast.makeText(MainActivity.this, "Native ad Load SucessFull", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNativeAdFailedToLoad() {

                    }
                });

        btnBaner = findViewById(R.id.btnBaner);
        btnCollapsingBaner = findViewById(R.id.btnCollapsingBaner);
        btnNativeBig = findViewById(R.id.btnNativeBig);
        btnNativeMedium = findViewById(R.id.btnNativeMedium);
        btnInterSial = findViewById(R.id.btnInterSial);
        btnInterSialFocrc = findViewById(R.id.btnInterSialFocrc);
        btnPreLoadingNativeBig = findViewById(R.id.btnPreLoadingNativeBig);
        btnRewardAd = findViewById(R.id.btnRewardAd);
        btnThreeClickIterSial = findViewById(R.id.btnThreeClickIterSial);


        btnThreeClickIterSial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MbitAds.getInstance().clickCount++;
                if (MbitAds.getInstance().clickCount >= MbitAds.getInstance().getUniversalClickCount()) {
                    MbitAds.getInstance().intCallback = callBackHome;
                    MbitAds.getInstance().ForceShowIntAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712", "0");
                }

            }
        });

        btnRewardAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MbitAds.getInstance().RewardAdLoad(MainActivity.this, "ca-app-pub-3940256099942544/5224354917", new RewaredIntCallback() {
                    @Override
                    public void rewaredIntCloseWithReward() {
                        Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                        intent.putExtra("id", 9);
                        startActivity(intent);
                    }

                    @Override
                    public void rewaredIntCloseWithOutReward() {

                    }

                    @Override
                    public void rewaredIntFailedToLoad() {

                    }

                    @Override
                    public void rewaredIntClick() {

                    }

                    @Override
                    public void rewaredIntLoaded() {

                    }
                });
            }
        });


        btnInterSialFocrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MbitAds.getInstance().SameTimeShowInterstitialAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712", true, new IntCallback() {
                    @Override
                    public void adIntClose() {
                        Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                        intent.putExtra("id", 7);
                        startActivity(intent);
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
                });


            }
        });

        btnCollapsingBaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("id", 8);
                startActivity(intent);
            }
        });


        btnBaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });


        btnPreLoadingNativeBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("id", 5);
                startActivity(intent);
            }
        });

        btnNativeBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });


        btnNativeMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
            }
        });
        btnInterSial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MbitAds.getInstance().intCallback = callBackHome;
                MbitAds.getInstance().ForceShowIntAd(MainActivity.this, "ca-app-pub-3940256099942544/1033173712", "0");
            }
        });


        MbitAds.getInstance().initAppOpen();
    }

    private IntCallback callBackHome = new IntCallback() {
        @Override
        public void adIntClose() {
            Intent intent = new Intent(MainActivity.this, BannerActivity.class);
            intent.putExtra("id", 4);
            startActivity(intent);
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
}