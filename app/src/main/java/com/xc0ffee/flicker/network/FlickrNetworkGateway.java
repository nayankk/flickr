package com.xc0ffee.flicker.network;

import android.util.Log;

import com.xc0ffee.flicker.models.FlickrRepsonseModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nayankk on 11/2/16.
 *
 * Wrapper class to encapsulate network related stuffs
 */

public class FlickrNetworkGateway {

    private static final String BASE_URL = "https://api.flickr.com/";
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=kittens";
    private static final String TAG = FlickrNetworkGateway.class.getSimpleName();

    private static FlickrNetworkGateway mGateway;

    private final Retrofit mRetrofit;
    private final FlickrNetworkInterface mApiService;

    private FlickrNetworkGateway() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        OkHttpClient httpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        mApiService = mRetrofit.create(FlickrNetworkInterface.class);
    }

    public static FlickrNetworkGateway getInstance() {
        if (mGateway == null) {
            mGateway = new FlickrNetworkGateway();
        }
        return mGateway;
    }

    public void getPhotos(final GetPhotosResponse callback) {
        Call<FlickrRepsonseModel> call = mApiService.fetchPhotos(URL);
        call.enqueue(new Callback<FlickrRepsonseModel>() {
            @Override
            public void onResponse(Call<FlickrRepsonseModel> call, Response<FlickrRepsonseModel> response) {
                Log.d(TAG, "onSuccess");
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FlickrRepsonseModel> call, Throwable t) {
                Log.d(TAG, "onFailure");
                callback.onFailure();
            }
        });
    }

    public void getPhotosForPage(final GetPhotosResponse callback, final int pageId) {
        Call<FlickrRepsonseModel> call = mApiService.fetchPhotoPage(URL, pageId);
        call.enqueue(new Callback<FlickrRepsonseModel>() {
            @Override
            public void onResponse(Call<FlickrRepsonseModel> call, Response<FlickrRepsonseModel> response) {
                Log.d(TAG, "onSuccess");
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FlickrRepsonseModel> call, Throwable t) {
                Log.d(TAG, "onFailure");
                callback.onFailure();
            }
        });
    }

    public void getPhotosForQuery(final GetPhotosResponse callback, final String text) {
        Call<FlickrRepsonseModel> call = mApiService.fetchPhotoQuery(URL, text);
        call.enqueue(new Callback<FlickrRepsonseModel>() {
            @Override
            public void onResponse(Call<FlickrRepsonseModel> call, Response<FlickrRepsonseModel> response) {
                Log.d(TAG, "onSuccess");
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FlickrRepsonseModel> call, Throwable t) {
                Log.d(TAG, "onFailure");
                callback.onFailure();
            }
        });
    }
}