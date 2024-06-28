package com.mbitadsdk;

import android.content.Context;
import android.content.SharedPreferences;

public class AdSDKPref {



    private static final String PREF_NAME = "slideshow_pref";



    private SharedPreferences m_csPref;
    private static final int MODE_PRIVATE = 0;




    public static final String TAG_ALL_AD_OFF = "tag_all_ad_off";

    public static final String TAG_APP_OPEN_AD_FOR_SPLASH_SCREEN = "tag_app_open_ad_for_splash_screen";
    public static final String TAG_APP_OPEN_AD_FOR_SPLASH_SCREEN_ID = "tag_app_open_ad_for_splash_screen_id";
    public static final String TAG_SPLASH_SCREEN_TIME_SEC = "tag_splash_screen_time_sec";


    public static final String TAG_NATIVE_AD_FOR_ONBORDING_SCREEN = "tag_native_ad_for_onbording_screen";
    public static final String TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_ONE_ID = "tag_native_ad_for_onbording_screen_one_id";
    public static final String TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_TWO_ID = "tag_native_ad_for_onbording_screen_two_id";
    public static final String TAG_NATIVE_AD_FOR_ONBORDING_SCREEN_THREE_ID = "tag_native_ad_for_onbording_screen_three_id";

    public static final String TAG_NATIVE_AD_FOR_START_SCREEN = "tag_native_ad_for_start_screen";
    public static final String TAG_NATIVE_AD_FOR_START_SCREEN_ID = "tag_native_ad_for_start_screen_id";
    public static final String TAG_BANNER_AD_ON_OFF = "tag_banner_ad_on_off";
    public static final String TAG_BANNER_AD_ID = "tag_banner_ad_id";
    public static final String TAG_BANNER_AD_COLLAPSING = "tag_banner_ad_collapsing";
    public static final String TAG_INTERSIAL_AD_ON_OFF = "tag_intersial_ad_on_off";
    public static final String TAG_INTERSIAL_AD_ID = "tag_intersial_ad_id";
    public static final String TAG_CLICK_INTERSIAL_AD_COUNTER = "tag_click_intersial_ad_counter";

    public static final String TAG_NATIVE_AD_FOR_LANGUGE_SCREEN = "tag_native_ad_for_languge_screen";
    public static final String TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID = "tag_native_ad_for_languge_screen_one_id";
    public static final String TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID = "tag_native_ad_for_languge_screen_two_id";

    public static final String TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_ONE_ID_HIGH_FLOOR = "tag_native_ad_for_languge_screen_one_id_high_floor";
    public static final String TAG_NATIVE_AD_FOR_LANGUGE_SCREEN_TWO_ID_HIGH_FLOOR = "tag_native_ad_for_languge_screen_two_id_high_floor";

    public static final String TAG_APP_OPEN_RESUME_ID = "tag_app_open_resume_id";
    public static final String TAG_APP_OPEN_RESUME_ON_OFF = "tag_app_open_resume_on_off";

    public static AdSDKPref getInstance(Context context) {
        try {
            return new AdSDKPref(context, PREF_NAME, MODE_PRIVATE);
        } catch (Exception e) {

        }

        return null;
    }

    private AdSDKPref(final Context context, final String pref_name,
                      final int mode) {
        super();
        this.m_csPref = context.getSharedPreferences(pref_name, mode);
    }

    public String getString(final String key, final String defaultValue) {
        return this.m_csPref.getString(key, defaultValue);
    }

    public void setString(String key, String value) {
        final SharedPreferences.Editor edit = this.m_csPref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public int getInt(final String key, final int defaultValue) {
        return this.m_csPref.getInt(key, defaultValue);
    }

    public void clear(String key) {
        m_csPref.edit().remove(key).commit();
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        return this.m_csPref.getBoolean(key, defaultValue);
    }

    public int putBoolean(final String key, final boolean defaultValue) {
        final SharedPreferences.Editor edit = this.m_csPref.edit();
        edit.putBoolean(key, defaultValue);
        edit.apply();
        return 0;
    }

    public int putInt(final String key, final int defaultValue) {
        final SharedPreferences.Editor edit = this.m_csPref.edit();
        edit.putInt(key, defaultValue);
        edit.apply();
        return 0;
    }

    public int putString(final String key, final String defaultValue) {
        final SharedPreferences.Editor edit = this.m_csPref.edit();
        edit.putString(key, defaultValue);
        edit.apply();
        return 0;
    }
}
