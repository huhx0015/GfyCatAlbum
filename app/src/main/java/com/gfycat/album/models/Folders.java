package com.gfycat.album.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Steve on 1/28/2017.
 */

public class Folders extends RealmObject{

    private String folder_name;
    private RealmList<ImageOrder> gif_order;



}
