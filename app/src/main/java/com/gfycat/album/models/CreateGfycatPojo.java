package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class CreateGfycatPojo {

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
