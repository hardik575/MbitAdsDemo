package com.mbitadsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.mbitadsdk.nativead.NativeAdLoadSucessCallBack;
import com.mbitsdk.R;

public class HomeActivity extends AppCompatActivity {


    RelativeLayout rlNativeAdsContainerForNativeMedium;
    ShimmerFrameLayout shimmer_container_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MbitAds.getInstance().appOpenManager.disableAppResume();

        shimmer_container_banner = findViewById(R.id.shimmer_container_banner);
        rlNativeAdsContainerForNativeMedium = findViewById(R.id.rlNativeAdsContainerForNativeMedium);


        MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);
        MbitAds.getInstance().LoadNativeCustom(this,
                R.layout.native_ad_view_large_card, this, "ca-app-pub-3940256099942544/2247696110", true, "0", new NativeAdLoadSucessCallBack() {
                    @Override
                    public void nativeAdLoaded(View view) {

                    }

                    @Override
                    public void nativeAdFailedToLoad() {

                    }
                });

//        MbitAds.getInstance().setLayoutCustom(rlNativeAdsContainerForNativeMedium, shimmer_container_banner);
//        MbitAds.getInstance().ShowNativeCustom(this, 0, this, true,
//
//                "ca-app-pub-3940256099942544/2247696110", "0",
//                new NativeAdLoadSucessCallBack() {
//                    @Override
//                    public void nativeAdLoaded(View view) {
//
//                    }
//
//                    @Override
//                    public void nativeAdFailedToLoad() {
//
//                    }
//                });

//        AdUtils.getInstance().isReadyPlaceToAppOpenAds = "1";
//        MyApp.getInstance().initAppOpen();
    }
}