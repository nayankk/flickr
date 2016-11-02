package com.xc0ffee.flicker.network;

import com.xc0ffee.flicker.models.FlickrRepsonseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by nayankk on 11/2/16.
 *
 * Interface that interacts with retrofit
 */

public interface FlickrNetworkInterface {
    @GET
    Call<FlickrRepsonseModel> fetchPhotos(@Url String url);

    @GET
    Call<FlickrRepsonseModel> fetchPhotoPage(@Url String url, @Query("page") int page);
}
