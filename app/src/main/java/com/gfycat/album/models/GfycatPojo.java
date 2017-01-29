package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GfycatPojo {

    @SerializedName("gfyItem")
    @Expose
    private GfyItem gfyItem;

    public GfyItem getGfyItem() {
        return gfyItem;
    }

    public void setGfyItem(GfyItem gfyItem) {
        this.gfyItem = gfyItem;
    }
}
