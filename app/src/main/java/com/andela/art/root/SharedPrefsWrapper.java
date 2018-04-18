package com.andela.art.root;

/**
 * Created by zack on 4/18/18.
 */

public interface SharedPrefsWrapper {

    /**
     *
     * @param key - key
     * @param value - value
     */
    void putBoolean(String key, boolean value);

    /**
     *
     * @param key - key
     * @return boolean
     */
    boolean getBoolean(String key);

    /**
     *
     * @param key - key
     * @param value - value
     */
    void putString(String key, String value);

    /**
     *
     * @param key - key
     * @return string
     */
    String getString(String key);

}
