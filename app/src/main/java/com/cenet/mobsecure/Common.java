package com.cenet.mobsecure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ANKIT on 8/7/2018.
 */

public class Common {
    public static String getPreferenceString(Context context,String str1,String str2) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str1,str2);
    }
    public static void setPreferenceString(Context context,String str1,String str2) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(str1,str2);
        editor.commit();
    }
    public static void clearPre(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
