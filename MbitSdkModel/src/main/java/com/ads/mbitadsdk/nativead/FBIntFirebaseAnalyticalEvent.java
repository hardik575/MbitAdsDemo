package com.ads.mbitadsdk.nativead;

import android.content.Context;
import android.os.Bundle;

import com.ads.mbitadsdk.AdUtils;


public class FBIntFirebaseAnalyticalEvent {

    private Context context;
    public FBIntFirebaseAnalyticalEvent(Context context){
        this.context = context;
    }
    public void LoadIntAdEvent(String id){

        if(id.equals(AdUtils.getInstance().mFanIntIdForAfterCreateVideo)){
            AdUtils.getInstance().EventRegister("fan_int_load_after_video_creation",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mFanIntIdForCropPhoto)){
            AdUtils.getInstance().EventRegister("fan_int_load_crop_photo_done",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mFanIntIdForHomeThemeClick)){
            AdUtils.getInstance().EventRegister("fan_int_load_home_screen",new Bundle());
        }
    }

    public void CloseIntAdEvent(String id){
        if(id.equals(AdUtils.getInstance().mFanIntIdForAfterCreateVideo)){
            AdUtils.getInstance().EventRegister("fan_int_close_after_video_creation",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mFanIntIdForCropPhoto)){
            AdUtils.getInstance().EventRegister("fan_int_close_crop_photo_done",new Bundle());
        }else if(id.equals(AdUtils.getInstance().mFanIntIdForHomeThemeClick)){
            AdUtils.getInstance().EventRegister("fan_int_close_home_screen",new Bundle());
        }
    }
}
