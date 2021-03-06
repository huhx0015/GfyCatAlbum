package com.gfycat.album.models;

import com.gfycat.album.constants.GfyConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrantRequest {

    @SerializedName("grant_type")
    @Expose
    private String grantType;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_secret")
    @Expose
    private String clientSecret;
    @SerializedName("username")

    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    public GrantRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = GfyConstants.GFY_GRANT_TYPE_CLIENT;
    }

    public GrantRequest(String username, String password, String clientId, String clientSecret) {
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = GfyConstants.GFY_GRANT_TYPE_PASSWORD;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
