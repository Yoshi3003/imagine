package com.jitrapon.imagine.models;

import com.google.gson.annotations.SerializedName;
import com.jitrapon.imagine.network.API;

import java.util.List;

/**
 * POJO to store in-memory data for a photo.
 */
public class Photo {

    @SerializedName(API.JSON_IMAGES)
    public List<Image> images;

    @SerializedName(API.JSON_ID)
    public long id;

    @SerializedName(API.JSON_NAME)
    public String name;

    @SerializedName(API.JSON_USER)
    public User user;
}
