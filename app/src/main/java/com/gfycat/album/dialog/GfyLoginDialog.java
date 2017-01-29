package com.gfycat.album.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.gfycat.album.R;
import com.gfycat.album.constants.GfyConstants;
import com.gfycat.album.data.GfyPreferences;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class GfyLoginDialog extends DialogFragment {

    private static final String LOG_TAG = GfyLoginDialog.class.getSimpleName();

    private String loginUrl;
    private Unbinder unbinder;

    @BindView(R.id.login_webview) WebView loginWebview;

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialog_login_view = inflater.inflate(R.layout.dialog_login, container, false);
        unbinder = ButterKnife.bind(this, dialog_login_view);

        // Sets the dialog background transparent.
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Encodes the redirect URL.
        String uriRedirect = Uri.parse(GfyConstants.REDIRECT_URL)
                .buildUpon()
                .build().toString();

        Uri loginUri = Uri.parse(GfyConstants.LOGIN_BASE_URL)
                .buildUpon()
                .appendPath("oauth")
                .appendPath("authorize")
                .appendQueryParameter("client_id", getString(R.string.gfycat_client_id))
                .appendQueryParameter("scope", "all")
                .appendQueryParameter("state", GfyConstants.GFY_LOGIN_STATE)
                .appendQueryParameter("response_type", "token")
                .appendQueryParameter("redirect_uri", uriRedirect)
                .build();
        loginUrl = loginUri.toString();

        initCookies();
        initWebView();

        return dialog_login_view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /** WEBVIEW METHODS ________________________________________________________________________ **/

    private void initCookies() {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieSyncManager.sync();
    }

    private void initWebView() {
        WebSettings settings = loginWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        loginWebview.setWebViewClient(new GfyLoginWebClient());
        loginWebview.loadUrl(loginUrl);
    }

    // handleLoginResponse(): Checks the url for the access token.
    // Format: https://example.com/callback/{access_token}&token_type=bearer&expires_in={expires_in}&scope={scope}&state={state}
    private void handleLoginResponse(String urlResponse) {

        // If new URL matches our specified REDIRECT_URL, we check the url parameters for the access
        // token.
        if (!urlResponse.equals(loginUrl) && urlResponse.contains(GfyConstants.REDIRECT_URL)) {

            // If the url does not contain "error" as part of the path, we retrieve the access token
            // from the url.
            if (!urlResponse.contains("error")) {

                // Parses the url response to get the access token.
                String accessToken = urlResponse.substring(urlResponse.lastIndexOf("/callback/") + 1, urlResponse.indexOf("&token_type"));
                Log.d(LOG_TAG, "handleLoginResponse(): Login successful. Access Token: " + accessToken);

                // Saves the access token into SharedPreferences.
                GfyPreferences.setAccessToken(accessToken, GfyPreferences.initializePreferences(getContext()));

                getDialog().dismiss(); // Dialog is dismissed.
            }
        }
    }

    private class GfyLoginWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d(LOG_TAG, "shouldOverrideUrlLoading(): New URL: " + url);
            handleLoginResponse(url);
            return false;
        }
    }
}