package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class CreateGfycatPojo {

    @SerializedName("isOk")
    @Expose
    private Boolean isOk;
    @SerializedName("gfyname")
    @Expose
    private String gfyname;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("uploadType")
    @Expose
    private String uploadType;
    @SerializedName("fetchUrl")
    @Expose
    private String fetchUrl;
    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public String getGfyname() {
        return gfyname;
    }

    public void setGfyname(String gfyname) {
        this.gfyname = gfyname;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getFetchUrl() {
        return fetchUrl;
    }

    public void setFetchUrl(String fetchUrl) {
        this.fetchUrl = fetchUrl;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
