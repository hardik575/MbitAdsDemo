package com.ads.mbitadsdk.intad;

import android.content.Context;
import android.os.Bundle;

import com.ads.mbitadsdk.AdUtils;


public class AdmobIntFirebaseAnalyticalEvent {

    private Context context;
    public AdmobIntFirebaseAnalyticalEvent(Context context){
        this.context = context;
    }
    public void LoadIntAdEvent(String id){
        if(id.equals(AdUtils.getInstance().mAdmobIntIdAfterCreateVideo)){
            AdUtils.getInstance().EventRegister("int_load_after_video_creation",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdHomeThemeClick)){
            AdUtils.getInstance().EventRegister("int_load_home_screen",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdBackFromPlayer)){
            AdUtils.getInstance().EventRegister("int_load_back_from_preview_player",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdSplash)){
            AdUtils.getInstance().EventRegister("int_load_splash",new Bundle());
        }
    }

    public void CloseIntAdEvent(String id){
        if(id.equals(AdUtils.getInstance().mAdmobIntIdAfterCreateVideo)){
            AdUtils.getInstance().EventRegister("int_close_after_video_creation",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdHomeThemeClick)){
            AdUtils.getInstance().EventRegister("int_close_home_screen",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdBackFromPlayer)){
            AdUtils.getInstance().EventRegister("int_close_back_from_preview_player",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mAdmobIntIdSplash)){
            AdUtils.getInstance().EventRegister("int_close_splash",new Bundle());
        }
    }
}
