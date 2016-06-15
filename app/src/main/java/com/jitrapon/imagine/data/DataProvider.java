package com.jitrapon.imagine.data;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jitrapon.imagine.ApplicationState;
import com.jitrapon.imagine.BuildConfig;
import com.jitrapon.imagine.Event;
import com.jitrapon.imagine.interfaces.ResponseListener;
import com.jitrapon.imagine.models.Category;
import com.jitrapon.imagine.models.Photo;
import com.jitrapon.imagine.network.API;
import com.jitrapon.imagine.network.RequestHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
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
    private Handler handler;

    /** thread executor that will control the running of background worker threads */
    private ExecutorService threadPool;

    /** network request handler **/
    RequestHandler requestHandler;

    /** JSON serializer **/
    Gson gson;

    /********************************************************
     * INITIALIZATIONS & HELPERS
     ********************************************************/

    private DataProvider(ApplicationState state) {
        appState = state;
        context = appState.getContext();
        threadPool = Executors.newCachedThreadPool();
        requestHandler = RequestHandler.getInstance(appState);
        gson = new Gson();
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
     * EVENT NOTIFICATION TO UI
     ********************************************************/

    /**
     * Set the handler that is handling events. This handler's looper must be of the main thread (UI thread)
     */
    public void setHandler(Handler h) {
        handler = h;
    }

    /**
     * Notify the UI thread that an event has finished, with an optional object as a result.
     */
    public void sendMessage(int eventId, Object arg) {
        if (handler != null) {
            handler.sendMessage(handler.obtainMessage(eventId, arg));
        }
    }

    /********************************************************
     * PHOTOS OPERATIONS
     ********************************************************/

    /**
     * Returns a static list of image categories. This should, in the future, be retrieved from
     * the server, but for now we return a static list.
     */
    public List<Category> getPhotoCategories() {
        return Arrays.asList(
                Category.ABSTRACT,
                Category.ANIMALS,
                Category.BLACK_AND_WHITE,
                Category.CELEBRITIES,
                Category.CITY_AND_ARCHITECTURE,
                Category.COMMERCIAL,
                Category.CONCERT,
                Category.FAMILY,
                Category.FASHION,
                Category.FILM,
                Category.FINE_ART,
                Category.FOOD,
                Category.JOURNALISM,
                Category.LANDSCAPES,
                Category.MACRO,
                Category.NATURE,
                Category.NUDE,
                Category.PEOPLE,
                Category.PERFORMING_ARTS,
                Category.SPORT,
                Category.STILL_LIFE,
                Category.STREET,
                Category.TRANSPORTATION,
                Category.TRAVEL,
                Category.UNCATEGORIZED,
                Category.UNDERWATER,
                Category.URBAN_EXPLORATION,
                Category.WEDDING);
    }

    /**
     * Retrieves list of photos based on the specified category, sorted by date of upload.
     */
    public void getPhotos(Category category) {
        if (category == null) category = Category.UNCATEGORIZED;

        String endpoint = new Uri.Builder()
                .appendEncodedPath(API.GET_PHOTOS)
                .appendQueryParameter(API.QUERY_CATEGORY, category.asQueryParameter())
                .appendQueryParameter(API.QUERY_SORT, API.PARAM_CREATED_AT)
                .appendQueryParameter(API.QUERY_CONSUMER_KEY, BuildConfig.CONSUMER_KEY)
                .build()
                .toString();

        requestHandler.get("GET PHOTOS", endpoint, new ResponseListener() {
            @Override
            public void onSuccess(final JSONObject response) {
                run(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Type type = new TypeToken<List<Photo>>() {}.getType();
                            JSONArray jsonPhotos = response.getJSONArray(API.JSON_PHOTOS);
                            List<Photo> photos = gson.fromJson(jsonPhotos.toString(), type);
                            Log.i(TAG, "Found " + photos.size() + " in the response");
                            sendMessage(Event.GET_PHOTOS_SUCCESS, photos);
                        }
                        catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, error.getMessage());
                sendMessage(Event.GET_PHOTOS_FAILED, null);
            }
        });
    }

    /**
     * Retrieves information about a single photo with a specified size [1 - 4]
     */
    public void getPhoto(long id, int size) {
        String endpoint = new Uri.Builder()
                .appendEncodedPath(API.GET_PHOTOS)
                .appendEncodedPath(Long.toString(id))
                .appendQueryParameter(API.QUERY_SIZE, Integer.toString(size))
                .appendQueryParameter(API.QUERY_CONSUMER_KEY, BuildConfig.CONSUMER_KEY)
                .build()
                .toString();

        requestHandler.get("GET PHOTO", endpoint, new ResponseListener() {
            @Override
            public void onSuccess(final JSONObject response) {
                run(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonPhoto = response.getJSONObject(API.JSON_PHOTO);
                            Photo photo = gson.fromJson(jsonPhoto.toString(), Photo.class);
                            sendMessage(Event.GET_PHOTO_SUCCESS, photo);
                        }
                        catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, error.getMessage());
                sendMessage(Event.GET_PHOTO_FAILED, null);
            }
        });
    }
}
