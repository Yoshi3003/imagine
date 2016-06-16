package com.jitrapon.imagine;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Handles abstraction to the application global states. This includes setting values.
 * Currently, the settings are stored in SharedPreferences. This is a Singleton class.
 * This is to demo that any global application states should be stored inside this class.
 *
 * @author Jitrapon Tiachunpun
 */
public class ApplicationState {

    private static ApplicationState instance;

    private Context context;
    private SharedPreferences settings;

    /********************************************************
     * INITIALIZATIONS & HELPERS
     ********************************************************/

    /**
     * Create a new instance of this class and initialize the access to SharedPreferences
     */
    private ApplicationState(Context ctx) {
        context = ctx.getApplicationContext();
        settings = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized ApplicationState getInstance(Context ctx) {
        if (instance == null) {
            instance = new ApplicationState(ctx);
        }
        return instance;
    }

    public Context getContext() { return context; }
}
