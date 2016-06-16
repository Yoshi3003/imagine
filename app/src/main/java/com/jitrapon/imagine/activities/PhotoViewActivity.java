package com.jitrapon.imagine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.Event;
import com.jitrapon.imagine.R;
import com.jitrapon.imagine.data.DataProvider;
import com.jitrapon.imagine.models.Photo;

/**
 * Responsible for viewing a photo in full size along with its information
 *
 * @author Jitrapon Tiachunpun
 */
public class PhotoViewActivity extends AppCompatActivity implements Handler.Callback {

    private static final String TAG = "PhotoViewActivity";

    private ImageView displayImageView;
    private CircularProgressView loadingIcon;
    private ImageView offlineIcon;
    private TextView offlineCaption;

    private static final int IMAGE_SIZE = 4;    // 500pix allows specifying of image size, 4 is the largest

    private DataProvider dataProvider;

    /** UI thread updater **/
    private Handler handler;

    /********************************************************
     * ACTIVITY CALLBACKS
     ********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        ApplicationState state = ApplicationState.getInstance(this);
        dataProvider = DataProvider.getInstance(state);
        handler = new Handler(this);
        dataProvider.setHandler(handler);

        setContentView(R.layout.activity_photo_view);
        displayImageView = (ImageView) findViewById(R.id.photo_full_image);
        loadingIcon = (CircularProgressView) findViewById(R.id.loading_icon);
        offlineIcon = (ImageView) findViewById(R.id.offline_icon);
        offlineCaption = (TextView) findViewById(R.id.offline_caption);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.loading));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (displayImageView != null)
            displayImageView.setVisibility(View.INVISIBLE);
        if (loadingIcon != null) {
            loadingIcon.startAnimation();
        }

        // begin loading photos from the REST endpoint
        // this is done in the background
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        else {
            long id = intent.getLongExtra(getString(R.string.extra_photo_id), -1L);

            // begin retrieving the photos!
            dataProvider.getPhoto(id, IMAGE_SIZE);
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

            // triggered when the photo information has been loaded successfully
            case Event.GET_PHOTO_SUCCESS: {
                try {
                    Photo photo = msg.obj == null ? null : (Photo) msg.obj;
                    if (photo != null) {
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle(photo.name);
                            getSupportActionBar().setSubtitle(photo.user.username);
                        }

                        // load the image!
                        if (photo.images != null && photo.images.size() > 0) {
                            displayImageView.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext())
                                    .load(photo.images.get(0).url)
                                    .error(R.drawable.error)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .crossFade(100)
                                    .fitCenter()
                                    .into(displayImageView);
                        }

                        loadingIcon.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingIcon.setVisibility(View.INVISIBLE);
                            }
                        }, 1000);
                    }
                }
                catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }

                break;
            }

            // triggered when there was a problem in loading the photo
            case Event.GET_PHOTO_FAILED: {
                loadingIcon.setVisibility(View.INVISIBLE);
                offlineIcon.setVisibility(View.VISIBLE);
                offlineCaption.setVisibility(View.VISIBLE);
                if (getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.error));

                break;
            }
        }

        return false;
    }
}