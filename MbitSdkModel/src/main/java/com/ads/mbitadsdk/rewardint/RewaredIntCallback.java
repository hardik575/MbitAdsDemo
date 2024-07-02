package com.ads.mbitadsdk.rewardint;

public interface RewaredIntCallback {

    void rewaredIntCloseWithReward();
    void rewaredIntCloseWithOutReward();
    void rewaredIntFailedToLoad();
    void rewaredIntClick();
    void rewaredIntLoaded();
}
