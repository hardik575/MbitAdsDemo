package com.ads.mbitadsdk.intad;

import android.view.View;

public interface OurIntAdListner {

    void ourIntAdLoaded(View v);
    void ourIntAdFailedToLoad(String msg);
    void ourIntAdClose();
    void ourIntAdOpenError(String msg);
    void ourIntAdClick();
    void ourIntAdClickError(String msg);
}
