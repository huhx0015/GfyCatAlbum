package com.gfycat.album.activities;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.gfycat.album.constants.GfyConstants;
import com.gfycat.album.utils.DatabaseHelper;
import com.gfycat.album.R;
import com.gfycat.album.models.Gif;
import com.gfycat.album.ui.views.GifsCompletionView;
import com.gfycat.album.models.Tag;
import com.gfycat.album.ui.adapters.GifRecyclerViewAdapter;
import com.tokenautocomplete.TokenCompleteTextView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.OrderedRealmCollection;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity  implements TokenCompleteTextView.TokenListener{

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    private DatabaseHelper dbhelper = new DatabaseHelper(this);

    // LAYOUT VARIABLES:
    private Unbinder unbinder;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // SEARCH VARIABLES
    private String searchQuery = "";

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.gif_completion_view) GifsCompletionView gifsCompletionView;
    @BindView(R.id.activity_main_recyclerview) RecyclerView gfyRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        // TODO: Check if database is empty. If it is, we show NUX activity.
        checkForEmptyDatabase();

        initView(); // Initializes the view for this activity.

        //test initialize

//        dbhelper.deleteDB(); // //delete the db: remove it when actually in use.
        dbhelper.initialize();
        Gif testGif = initTestGif("TEST GIF 1", "Hi there 1", "http://www.google.com/what1.mp4");
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

        // Test RecyclerView:
        setRecyclerView(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                displayGifsCompletionView(!searchQuery.isEmpty());
                setCompletionView();
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchQuery = "";
                displayGifsCompletionView(false);
                setCompletionView();
                return true;
            }
        });

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTokenAdded(Object token) {
        Toast.makeText(this, "token added", Toast.LENGTH_SHORT).show();
        // TODO: Update the recyclerView with items with the tag.
    }

    @Override
    public void onTokenRemoved(Object token) {
        Toast.makeText(this, "token removed", Toast.LENGTH_SHORT).show();
        // TODO: Update the recyclerView with items with the tag.
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {
        initToolbar();
        initRecyclerView();
        initCompletionView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setLogo(R.drawable.gfycatlogo);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        gfyRecyclerView.setLayoutManager(layoutManager);
    }

    private void initCompletionView() {
        ArrayList<String> searchTerms = new ArrayList<>();
        ArrayAdapter completionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchTerms);
        gifsCompletionView.setTokenListener(this);
        gifsCompletionView.setAdapter(completionAdapter);
        gifsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);
    }

    private void setRecyclerView(OrderedRealmCollection<Gif> data) {
        GifRecyclerViewAdapter gifRecyclerViewAdapter = new GifRecyclerViewAdapter(data, this);
        gfyRecyclerView.setAdapter(gifRecyclerViewAdapter);
    }

    private void setCompletionView() {
        gifsCompletionView.clear();
        String[] searchTerms = searchQuery.split("\\s+");
        for (String term : searchTerms) {
            gifsCompletionView.addObject(term);
        }
    }

    // displayGifsCompletionView(): Shows/hides the gifsCompletionView.
    private void displayGifsCompletionView(boolean isDisplay) {
        if (isDisplay) {
            gifsCompletionView.setVisibility(View.VISIBLE);
        } else {
            gifsCompletionView.setVisibility(View.GONE);
        }
    }

    /** ACTION METHODS _________________________________________________________________________ **/

    public void launchVideoIntent(String url) {
        Intent videoIntent = new Intent(this, VideoActivity.class);
        videoIntent.putExtra(GfyConstants.GFY_VIDEO_URL_INTENT_EXTRA, url);
        startActivity(videoIntent);
    }

    public void displayActionDialog(int index, final String url) {
        final Dialog actionDialog = new Dialog(this, R.style.AppTheme_Dialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        View actionDialogView = inflater.inflate(R.layout.dialog_action, null);
        actionDialog.setContentView(actionDialogView);

        Button shareButton = (Button) actionDialog.findViewById(R.id.share_button);
        Button editButton = (Button) actionDialog.findViewById(R.id.edit_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareLinkIntent(url);
                actionDialog.dismiss();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Define edit action here.
                actionDialog.dismiss();
            }
        });

        actionDialog.show();
    }

    private void shareLinkIntent(String url) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(shareIntent, "Share GfyCat link using..."));
    }

    /** DATABASE METHODS _______________________________________________________________________ **/

    private void checkForEmptyDatabase() {
        boolean databaseEmpty = false; // TODO: Change this value later.
        if (databaseEmpty) {
            Intent nuxIntent = new Intent(this, EmptyActivity.class);
            startActivity(nuxIntent);
            finish();
        }
    }

    private Gif initTestGif(String gifName, String desc, String videoUrl){
        Tag testTag = new Tag("#cats");
        Tag testTag2 = new Tag("#lol");
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(testTag);
        tagList.add(testTag2);
//        String videoUrl = "https://zippy.gfycat.com/IllfatedPleasantIrishterrier.webm";
        String thumbnailUrl="https://thumbs.gfycat.com/AdmiredNiftyCatbird-mobile.jpg";
//        String thumbnailUrl="https://thumbs.gfycat.com/IllfatedPleasantIrishterrier-mobile.jpg";
        return new Gif(tagList, gifName, desc, videoUrl, thumbnailUrl, incrementIndex());
    }

    //increments the current index.
    private int incrementIndex(){
        return dbhelper.getIndex();
    }
}
