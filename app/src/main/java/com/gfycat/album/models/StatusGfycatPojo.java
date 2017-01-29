package com.gfycat.album.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusGfycatPojo {

    @SerializedName("task")
    @Expose
    private String task;
    @SerializedName("gfyname")
    @Expose
    private String gfyname;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getGfyname() {
        return gfyname;
    }

    public void setGfyname(String gfyname) {
        this.gfyname = gfyname;
    }

}
