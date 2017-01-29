package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class CreateGfycatPojo {

    @SerializedName("fetchUrl")
    @Expose
    private String fetchUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;

    public String getFetchUrl() {
        return fetchUrl;
    }

    public void setFetchUrl(String fetchUrl) {
        this.fetchUrl = fetchUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
