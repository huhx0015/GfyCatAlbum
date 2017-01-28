package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrantResponsePojo {

    @SerializedName("token_type")
    @Expose
    private String tokenType;
    
    @SerializedName("scope")
    @Expose
    private String scope;
    
    @SerializedName("expires_in")
    @Expose
    private int expiresIn;
    
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    
    @SerializedName("refresh_token_expires_in")
    @Expose
    private int refreshTokenExpiresIn;
    
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    
    @SerializedName("resource_owner")
    @Expose
    private String resourceOwner;

    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;

    public int getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public void setRefreshTokenExpiresIn(int refreshTokenExpiresIn) {
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getResourceOwner() {
        return resourceOwner;
    }

    public void setResourceOwner(String resourceOwner) {
        this.resourceOwner = resourceOwner;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
