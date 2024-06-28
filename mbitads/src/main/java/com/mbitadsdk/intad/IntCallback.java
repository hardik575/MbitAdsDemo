package com.mbitadsdk.intad;

public interface IntCallback {
    void adIntClose();
    void adIntOpen();
    void adIntFailedToLoad();
    void adIntClick();
    void adIntLoaded();
}
