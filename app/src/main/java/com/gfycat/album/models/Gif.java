package com.gfycat.album.models;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Steve on 1/28/2017.
 */

public class Gif extends RealmObject{
    private RealmList<Tag> TagList;
    @Index
    private String userGifName;
    @Index
    private String textDescription;
    private String gyfcatURL;
    private String thumbnailURL;
    @PrimaryKey
    private int index;

    //default blank constructor
    public Gif() {
    }

    public Gif (ArrayList<Tag> tags, @Nullable String userGifName, @Nullable String textDescription, String gyfcatURL, String thumbnailURL){

        TagList = new RealmList<>();

        for (Tag tag: tags)
             TagList.add(tag); {
        }
        this.userGifName = userGifName;
        this.textDescription = textDescription;
        this.gyfcatURL = gyfcatURL;
        this.thumbnailURL = thumbnailURL;
    }


    public RealmList<Tag> getTagsList() {
        return TagList;
    }

    public void setTagsList(RealmList<Tag> tagList) {
        TagList = tagList;
    }

    public String getUserGifName() {
        return userGifName;
    }

    public void setUserGifName(String userGifName) {
        this.userGifName = userGifName;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getGyfcatURL() {
        return gyfcatURL;
    }

    public void setGyfcatURL(String gyfcatURL) {
        this.gyfcatURL = gyfcatURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
