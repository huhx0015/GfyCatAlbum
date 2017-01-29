package com.gfycat.album.utils;

import android.content.Context;
import android.util.Log;

import com.gfycat.album.models.Gif;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
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

    //copies to the realm db.
    public void updateGifs(Gif gif){

        realm.beginTransaction();

        //dedupe first
        String url = gif.getGyfcatURL();
        Log.d("STEVE", url);
        RealmList<Gif> newList = new RealmList<>();
        newList.addAll(realm.where(Gif.class).equalTo("gyfcatURL", url).findAll());


        //only update if this item doesn't already exist in the db.
        if (newList.size() == 0){
            Log.d("STEVE", "updating updateGfycatDB inside DB");
            realm.copyToRealm(gif);
            realm.commitTransaction();
        }
        else
        {
            Log.d("STEVE", "not updateGfycatDB inside DB");
            realm.commitTransaction();
        }
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


    public int getIndex() {

        if (realm.where(Gif.class).findAll().max("index") == null)
            return 1;

        return realm.where(Gif.class).findAll().max("index").intValue() + 1;
    }

}
