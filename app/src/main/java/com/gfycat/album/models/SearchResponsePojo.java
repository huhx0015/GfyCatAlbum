package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchResponsePojo {

    @SerializedName("gfycats")
    @Expose
    private List<GfycatPojo> gfycats = null;
    @SerializedName("found")
    @Expose
    private Integer found;
    @SerializedName("cursor")
    @Expose
    private String cursor;

    public List<GfycatPojo> getGfycats() {
        return gfycats;
    }

    public void setGfycats(List<GfycatPojo> gfycats) {
        this.gfycats = gfycats;
    }

    public Integer getFound() {
        return found;
    }

    public void setFound(Integer found) {
        this.found = found;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

}
