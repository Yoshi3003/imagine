package com.jitrapon.imagine;

import android.content.Context;

/**
 * Handles abstraction to the application global states. This includes setting values.
 * Currently, for this demo, we only store the app's connection state.
 *
 * @author Jitrapon Tiachunpun
 */
public class ApplicationState {

    private static ApplicationState instance;

    private Context context;

    /********************************************************
     * INITIALIZATIONS & HELPERS
     ********************************************************/

    /**
     * Create a new instance of this class and initialize the access to SharedPreferences
     */
    private ApplicationState(Context ctx) {
        context = ctx.getApplicationContext();
    }

    public static synchronized ApplicationState getInstance(Context ctx) {
        if (instance == null) {
            instance = new ApplicationState(ctx);
        }
        return instance;
    }

    public Context getContext() { return context; }
}
