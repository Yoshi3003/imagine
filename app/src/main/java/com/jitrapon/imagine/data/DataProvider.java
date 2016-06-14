package com.jitrapon.imagine.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.interfaces.ResponseListener;
import com.jitrapon.imagine.models.Category;
import com.jitrapon.imagine.network.API;
import com.jitrapon.imagine.network.RequestHandler;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that abstracts away the CRUD operations of data model. The workflow of data usually
 * follows the following pattern:
 *
 * Network -> Parse data -> Update Database -> Update in-memory models -> Update UI
 *
 * @author Jitrapon Tiachunpun
 */
public class DataProvider {

    private static final String TAG = "DataProvider";

    private static DataProvider instance;
    private ApplicationState appState;
    private Context context;

    /** thread executor that will control the running of background worker threads */
    private ExecutorService threadPool;

    /** network request handler **/
    RequestHandler requestHandler;

    /********************************************************
     * INITIALIZATIONS & HELPERS
     ********************************************************/

    private DataProvider(ApplicationState state) {
        appState = state;
        context = appState.getContext();
        threadPool = Executors.newCachedThreadPool();
        requestHandler = RequestHandler.getInstance(appState);
    }

    public static synchronized DataProvider getInstance(ApplicationState state) {
        if (instance == null) {
            instance = new DataProvider(state);
        }
        return instance;
    }

    private void run(Runnable runnable) {
        threadPool.submit(runnable);
    }

    /********************************************************
     * PHOTOS OPERATIONS
     ********************************************************/

    /**
     * Retrieves list of photos based on the specified category
     */
    public void getPhotos(Category category) {
        requestHandler.get("GET PHOTOS", API.GET_PHOTOS + "/" + category.getURLName(), new ResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d(TAG, "Sucesss, receiving something!");
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }
}
