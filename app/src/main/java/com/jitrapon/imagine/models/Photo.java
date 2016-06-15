package com.jitrapon.imagine.models;

import com.google.gson.annotations.SerializedName;
import com.jitrapon.imagine.network.API;

/**
 * POJO to store in-memory data for a photo.
 */
public class Photo {

    @SerializedName(API.JSON_ID)
    private long id;

    @SerializedName(API.JSON_NAME)
    private String name;
}
