package com.jitrapon.imagine.network;

/**
 * Contains all the API endpoints
 */
public class API {

    /** All JSON serializable fields **/
    public static final String JSON_ID = "id";
    public static final String JSON_NAME = "name";
    public static final String JSON_PHOTO = "photo";
    public static final String JSON_PHOTOS = "photos";
    public static final String JSON_IMAGES = "images";
    public static final String JSON_URL = "url";
    public static final String JSON_USER = "user";
    public static final String JSON_USERNAME = "username";
    public static final String JSON_CURRENT_PAGE = "current_page";
    public static final String JSON_TOTAL_PAGES = "total_pages";

    /** All query parameters **/
    public static final String QUERY_CONSUMER_KEY = "consumer_key";
    public static final String QUERY_CATEGORY = "only";
    public static final String QUERY_FEATURE = "feature";
    public static final String PARAM_FRESH_WEEK = "fresh_week";
    public static final String QUERY_SORT = "sort";
    public static final String PARAM_CREATED_AT = "created_at";
    public static final String QUERY_SIZE = "size";
    public static final String QUERY_PAGE = "page";

    /** All endpoints **/
    public static final String GET_PHOTOS = "photos";
}
