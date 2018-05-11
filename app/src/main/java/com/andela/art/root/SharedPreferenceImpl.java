package com.andela.art.root;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zack on 4/18/18.
 */

public class SharedPreferenceImpl implements SharedPrefsWrapper {
    private SharedPreferences sharedPreferences;

    private final Context context;
    private static final String PREFS = "Wyat_prefs";

    /**
     *
     * @param context - context
     */
    public SharedPreferenceImpl(Context context) {
        this.context = context;
    }

    /**
     *
     * @param key - key
     * @param value - value
     */
    @Override
    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, 0).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     *
     * @param key - key
     * @return boolean
     */
    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    /**
     *
     * @param key - key
     * @param value - value
     */
    @Override
    public void putString(String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, 0).edit();
        editor.putString(key, value);
        editor.apply();

    }

    /**
     *
     * @param key - key
     * @return string
     */
    @Override
    public String getString(String key) {

        sharedPreferences = context.getSharedPreferences(PREFS, 0);


        return sharedPreferences.getString(key, null);
    }

    public void clear() {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, 0).edit();
        editor.clear();
        editor.apply();
    }
}
