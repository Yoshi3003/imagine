package com.jitrapon.imagine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

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

    private ImageView imageView;
    private CircularProgressView loadingIcon;
    private static final int IMAGE_SIZE = 4;    // 500pix allows specifying of image size, 4 is the largest

    private DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        ApplicationState state = ApplicationState.getInstance(this);
        dataProvider = DataProvider.getInstance(state);
        Handler handler = new Handler(this);
        dataProvider.setHandler(handler);

        setContentView(R.layout.activity_photo_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.loading));
        imageView = (ImageView) findViewById(R.id.photo_full_image);
        loadingIcon = (CircularProgressView) findViewById(R.id.loading_icon);
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

    /********************************************************
     * EVENT CALLBACKS TO UI. This is done with Handler, but can
     * be replaced with other EventBus-like system
     ********************************************************/

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case Event.GET_PHOTO_SUCCESS: {
                Photo photo = msg.obj == null ? null : (Photo) msg.obj;
                if (photo != null) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(photo.name);
                        getSupportActionBar().setSubtitle(photo.user.username);
                    }

                    // load the image!
                    Glide.with(this)
                            .load(photo.images.get(0).url)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .crossFade(400)
                            .fitCenter()
                            .into(imageView);

                    loadingIcon.setVisibility(View.GONE);
                }

                break;
            }
        }

        return false;
    }
}