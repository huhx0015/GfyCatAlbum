package com.gfycat.album.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Steve on 1/28/2017.
 */

public class Tag extends RealmObject {
    @Index
    private String tag;

    public Tag(){

    }

    public Tag(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
