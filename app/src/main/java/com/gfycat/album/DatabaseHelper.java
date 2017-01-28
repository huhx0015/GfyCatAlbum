package com.gfycat.album;

import android.content.Context;

import com.gfycat.album.models.Gif;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.async.QueryUpdateTask;

/**
 * Created by Steve on 1/28/2017.
 */

public class DatabaseHelper {

    private Context context;
    Realm realm;


    public DatabaseHelper(Context context) {
        this.context = context;
    }

    public void initialize(){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void deleteDB(){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
        realm.close();
        realm.deleteRealm(realm.getConfiguration());
    }

    public void updateGifs(Gif gif){
        realm.beginTransaction();
        //copies to the realm db.
        realm.copyToRealm(gif);
        realm.commitTransaction();

    }

    public RealmResults query(String queryString) {
        RealmResults<Gif> result = realm.where(Gif.class)
                .beginGroup()
                .contains("userGifName", queryString)
                .or()
                .contains("textDescription", queryString)
                .or()
                .contains("TagList.tag", queryString) //this may not be searchable because its a list
                .endGroup()
                .findAll();

        return result;
    }


}
