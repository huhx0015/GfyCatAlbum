package com.gfycat.album.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.gfycat.album.R;
import com.gfycat.album.constants.GfyConstants;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class GfyPreferences {

    /** SHARED PREFERENCES FUNCTIONALITY _______________________________________________________ **/

    private static int getPreferenceResource() {
        return R.xml.gfy_preference;
    }

    public static SharedPreferences initializePreferences(Context context) {
        return context.getSharedPreferences(GfyConstants.GFY_PREFERENCES, Context.MODE_PRIVATE);
    }

    // setDefaultPreferences(): Sets the shared preference values to default values.
    public static void setDefaultPreferences(boolean isReset, Context context) {

        int prefResource = getPreferenceResource();

        // Resets the preference values to default values.
        if (isReset) {
            SharedPreferences preferences = initializePreferences(context);
            preferences.edit().clear().apply();
        }

        // Sets the default values for the SharedPreferences object.
        PreferenceManager.setDefaultValues(context, GfyConstants.GFY_PREFERENCES, Context.MODE_PRIVATE, prefResource, true);
    }

    /** GET PREFERENCES FUNCTIONALITY __________________________________________________________ **/

    public static boolean getUserLoggedIn(SharedPreferences preferences) {
        return preferences.getBoolean(GfyConstants.GFY_PREFERENCES_USER_LOGGED_IN, false);
    }

    public static String getLogin(SharedPreferences preferences) {
        return preferences.getString(GfyConstants.GFY_PREFERENCES_LOGIN, "");
    }

    public static String getPassword(SharedPreferences preferences) {
        return preferences.getString(GfyConstants.GFY_PREFERENCES_PASSWORD, "");
    }

    public static String getToken(SharedPreferences preferences) {
        return preferences.getString(GfyConstants.GFY_PREFERENCES_TOKEN, "");
    }

    /** SET PREFERENCES FUNCTIONALITY __________________________________________________________ **/

    public static void setUserLoggedIn(boolean isLoggedIn, SharedPreferences preferences) {
        SharedPreferences.Editor prefEdit = preferences.edit();
        prefEdit.putBoolean(GfyConstants.GFY_PREFERENCES_USER_LOGGED_IN, isLoggedIn);
        prefEdit.apply();
    }

    public static void setLogin(String loginName, SharedPreferences preferences) {
        SharedPreferences.Editor prefEdit = preferences.edit();
        prefEdit.putString(GfyConstants.GFY_PREFERENCES_LOGIN, loginName);
        prefEdit.apply();
    }

    public static void setPassword(String password, SharedPreferences preferences) {
        SharedPreferences.Editor prefEdit = preferences.edit();
        prefEdit.putString(GfyConstants.GFY_PREFERENCES_PASSWORD, password);
        prefEdit.apply();
    }

    public static void setToken(String token, SharedPreferences preferences) {
        SharedPreferences.Editor prefEdit = preferences.edit();
        prefEdit.putString(GfyConstants.GFY_PREFERENCES_PASSWORD, token);
        prefEdit.apply();
    }
}