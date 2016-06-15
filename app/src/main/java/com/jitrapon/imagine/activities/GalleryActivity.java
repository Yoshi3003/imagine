package com.jitrapon.imagine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.Event;
import com.jitrapon.imagine.R;
import com.jitrapon.imagine.adapters.GalleryAdapter;
import com.jitrapon.imagine.data.DataProvider;
import com.jitrapon.imagine.interfaces.ItemClickedListener;
import com.jitrapon.imagine.models.Category;
import com.jitrapon.imagine.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the activity that will display a list of photos belonging to a particular category.
 *
 * @author Jitrapon Tiachunpun
 */
public class GalleryActivity extends AppCompatActivity implements Handler.Callback, ItemClickedListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "GalleryActivity";
    private static final int NUM_GRID_COLUMNS = 2;
    private GalleryAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    /** this is the model that will be synced with the adapter **/
    private List<Photo> photos;

    private DataProvider dataProvider;

    private Category category;

    private Handler handler;

    /********************************************************
     * ACTIVITY CALLBACKS
     ********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize states and data handler
        photos = new ArrayList<>();
        ApplicationState state = ApplicationState.getInstance(this);
        dataProvider = DataProvider.getInstance(state);
        handler = new Handler(this);
        dataProvider.setHandler(handler);

        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setColorSchemeResources(
                R.color.colorAccent
            );
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery_view);
        adapter = new GalleryAdapter(getApplicationContext(), photos, this);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_GRID_COLUMNS);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        // begin loading photos from the REST endpoint
        // this is done in the background
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        else {
            category = Category.valueOf(intent.getStringExtra(getString(R.string.extra_category)));
            if (getSupportActionBar() != null) getSupportActionBar().setTitle(category.asQueryParameter());

            // begin retrieving the photos!
            dataProvider.getPhotos(category);

            // this is to force the icon of refreshing to show
            // if we don't do this, it won't show!
            // credit: http://stackoverflow.com/questions/26858692/swiperefreshlayout-setrefreshing-not-showing-indicator-initially
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // make sure that when this activity is resumed, its handler will be notified of messages
        dataProvider.setHandler(handler);
    }

    /********************************************************
     * EVENT CALLBACKS TO UI. This is done with Handler, but can
     * be replaced with other EventBus-like system
     ********************************************************/

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case Event.GET_PHOTOS_SUCCESS: {
                List<Photo> temp = msg.obj == null ? null : (List<Photo>) msg.obj;
                if (temp != null) {
                    photos.clear();
                    photos.addAll(temp);
                }
                else {
                    photos.clear();
                }

                // force the adapter to rebind the data because the model has been updated
                adapter.notifyDataSetChanged();

                // stop the refreshing icon
                refreshLayout.setRefreshing(false);

                break;
            }
            case Event.GET_PHOTOS_FAILED: {

                break;
            }
        }

        return false;
    }

    /********************************************************
     * REFRESH CALLBACK
     ********************************************************/

    @Override
    public void onRefresh() {
        dataProvider.getPhotos(category);
    }

    /********************************************************
     * PHOTO CLICK CALLBACK
     ********************************************************/

    @Override
    public void onItemClicked(Object obj) {
        if (obj instanceof Photo) {
            Photo photo = (Photo) obj;
            Log.d(TAG, "Viewing " + photo.name);

            Intent intent = new Intent(this, PhotoViewActivity.class);
            intent.putExtra(getString(R.string.extra_photo_id), photo.id);
            startActivity(intent);
        }
    }
}
