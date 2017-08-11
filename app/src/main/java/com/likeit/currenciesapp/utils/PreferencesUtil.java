package com.likeit.currenciesapp.utils;

import android.content.SharedPreferences;

public class PreferencesUtil {
	private static SharedPreferences preferences;

	public static void SetPreference(SharedPreferences pre){
		preferences=pre;
	}

	/************************Clean*****************************/
	public static void clear(){
		preferences.edit().clear().commit();
	}

	/***********************PUT****************************/
	public static boolean putStringValue(String key, String value) {
		return preferences.edit().putString(key, value.trim()).commit();
	}

	public static boolean putIntegerValue(String key, int value) {
		return preferences.edit().putInt(key, value).commit();
	}

	public static boolean putFloatValue(String key, float value) {
		return preferences.edit().putFloat(key, value).commit();
	}

	public static boolean putBooleanValue(String key, boolean value) {
		return preferences.edit().putBoolean(key, value).commit();
	}



	/***********************GET****************************/
	public static String getStringValue(String key) {
		return preferences.getString(key, "");
	}

	public static int getIntValue(String key) {
		return preferences.getInt(key, 0);
	}

	public static float getFloatValue(String key) {
		return preferences.getFloat(key, 0f);
	}

	public static boolean getBooleanValue(String key) {
		return preferences.getBoolean(key, false);
	}

    public static boolean getBooleanValue(String key,boolean b) {
        return preferences.getBoolean(key, b);
    }

}
