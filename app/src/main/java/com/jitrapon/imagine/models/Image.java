package com.jitrapon.imagine.models;

import com.google.gson.annotations.SerializedName;
import com.jitrapon.imagine.network.API;

/**
 * POJO class for serializing Image info
 */
public class Image {

    @SerializedName(API.JSON_URL)
    public String url;
}
