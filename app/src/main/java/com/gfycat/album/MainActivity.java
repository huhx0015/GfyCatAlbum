package com.gfycat.album;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gfycat.album.models.Gif;
import com.gfycat.album.models.Tag;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbhelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //test initialize

//        dbhelper.deleteDB(); // //delete the db: remove it when actually in use.
        dbhelper.initialize();

        Gif testGif = initTestGif();
        dbhelper.updateGifs(testGif);


        RealmResults result = dbhelper.query("cats");
        //sample code to get result
        RealmList<Gif> newList = new RealmList<>();
        newList.addAll(result);

        //do whatever you want with each gif Object

        for (Gif curGif: newList) {
            String gfyURL = curGif.getGyfcatURL();
            int index = curGif.getIndex();
            Log.d("onCreate: ", gfyURL);
        }



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Gif initTestGif(){
        Tag testTag = new Tag("#cats");
        Tag testTag2 = new Tag("#lol");
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(testTag);
        tagList.add(testTag2);
        String videoUrl = "https://zippy.gfycat.com/AdmiredNiftyCatbird.webm";
//        String videoUrl = "https://zippy.gfycat.com/IllfatedPleasantIrishterrier.webm";
        String thumbnailUrl="https://thumbs.gfycat.com/AdmiredNiftyCatbird-mobile.jpg";
//        String thumbnailUrl="https://thumbs.gfycat.com/IllfatedPleasantIrishterrier-mobile.jpg";
        return new Gif(tagList, "testCustomGifName", "this is a cat running into a door",videoUrl,thumbnailUrl, incrementIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //increments the current index.
    private int incrementIndex(){
        return dbhelper.getIndex();
    }
}
