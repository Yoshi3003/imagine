package com.jitrapon.imagine.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Base callback interface to be implemented by the clients registered to receive
 * network-based events
 */
public interface ResponseListener {

    void onSuccess(JSONObject response);

    void onError(VolleyError error);
}
