package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateGfycatRequest {

    @SerializedName("fetchUrl")
    @Expose
    private String fetchUrl;
    @SerializedName("title")
    @Expose
    private String title;

    public CreateGfycatRequest(String url, String title) {
        this.fetchUrl = url;
        this.title = title;
    }

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

}
