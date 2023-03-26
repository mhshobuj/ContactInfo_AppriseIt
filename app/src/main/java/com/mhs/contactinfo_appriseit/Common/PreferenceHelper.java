package com.mhs.contactinfo_appriseit.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class PreferenceHelper {
    private SharedPreferences prefs;
    private Context context;
    private static Configuration configuration;

    private static Locale locale;

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static void insertData(Context context,String key,String value){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String retriveData(Context context,String key){
        return getPrefs(context).getString(key,"");
    }

    public static void deleteData(Context context,String key){
        SharedPreferences.Editor editor=getPrefs(context).edit();
        editor.remove(key);
        editor.apply();
    }
}
