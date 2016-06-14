package com.jitrapon.imagine.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.R;
import com.jitrapon.imagine.data.DataProvider;

/**
 * This is the main entry activity of this application. It will handle authentication automatically,
 * if the app is not authenticated, and displays list of images from the REST source.
 *
 * @author Jitrapon Tiachunpun
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /** global application states **/
    private ApplicationState appState;

    /** data handler **/
    private DataProvider dataProvider;

    /********************************************************
     * ACTIVITY CALLBACKS
     ********************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize states and data handler
        appState = ApplicationState.getInstance(this);
        dataProvider = DataProvider.getInstance(appState);

        dataProvider.getPhotos();
    }

    /********************************************************
     * PRIVATE ACTIONS
     ********************************************************/

}
