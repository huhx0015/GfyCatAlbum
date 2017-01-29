package com.gfycat.album.constants;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class GfyConstants {

    // API:
    public static final String GFY_GRANT_TYPE_CLIENT = "client_credentials";
    public static final String GFY_GRANT_TYPE_PASSWORD = "password";

    // LOGIN:
    public static final String GFY_LOGIN_STATE = "GFYCATNIPLOVE";

    // SHARED PREFERENCES:
    public static final String GFY_PREFERENCES = "gfy_preferences";
    public static final String GFY_PREFERENCES_LOGIN = "gfy_login";
    public static final String GFY_PREFERENCES_PASSWORD = "gfy_password";
    public static final String GFY_PREFERENCES_USER_LOGGED_IN = "gfy_user_logged_in";
    public static final String GFY_PREFERENCES_ACCESS_TOKEN = "gfy_access_token";
    public static final String GFY_PREFERENCES_REFRESH_TOKEN = "gfy_refresh_token";

    // URL:
    public static final String BASE_URL = "https://api.gfycat.com/v1/";
    public static final String TEST_URL = "https://api.gfycat.com/v1test/";
    public static final String LOGIN_BASE_URL = "https://gfycat.com/";
    public static final String REDIRECT_URL = "gfycat://auth";
}
