package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookmarkPojo {

    @SerializedName("errorMessage")
    @Expose
    private ErrorMessage errorMessage;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
