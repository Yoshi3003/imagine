package com.jitrapon.imagine;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Handles abstraction to the application global states.
 *
 * @author Jitrapon Tiachunpun
 */
public class ApplicationState {

    private static ApplicationState instance;

    private Context context;
    private ConnectivityManager connectivityManager;

    /********************************************************
     * INITIALIZATIONS & HELPERS
     ********************************************************/

    /**
     * Create a new instance of this class and initialize the access to SharedPreferences
     */
    private ApplicationState(Context ctx) {
        context = ctx.getApplicationContext();

        connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static synchronized ApplicationState getInstance(Context ctx) {
        if (instance == null) {
            instance = new ApplicationState(ctx);
        }
        return instance;
    }

    public Context getContext() { return context; }

    public boolean isConnected() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
