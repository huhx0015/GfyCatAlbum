package com.gfycat.album.activities;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.gfycat.album.DatabaseHelper;
import com.gfycat.album.R;
import com.gfycat.album.models.Gif;
import com.gfycat.album.models.Tag;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private DatabaseHelper dbhelper = new DatabaseHelper(this);
    private Unbinder unbinder;

    // RECYCLERVIEW VARIABLES
    private RecyclerViewDragDropManager dragDropManager;

    @BindView(R.id.activity_main_recyclerview) RecyclerView gfyRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        dragDropManager = new RecyclerViewDragDropManager();
        
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

        // Associate searchable configuration with the SearchView
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this,"touch touch", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //increments the current index.
    private int incrementIndex(){
        return dbhelper.getIndex();
    }
}