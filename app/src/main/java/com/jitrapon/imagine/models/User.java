package com.jitrapon.imagine.models;

import com.google.gson.annotations.SerializedName;
import com.jitrapon.imagine.network.API;

/**
 * POJO to store user info after deserializing from JSON
 */
public class User {

    @SerializedName(API.JSON_USERNAME)
    public String username;
}
