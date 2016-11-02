package com.xc0ffee.flicker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xc0ffee.flicker.R;
import com.xc0ffee.flicker.models.FlickrRepsonseModel;
import com.xc0ffee.flicker.network.FlickrNetworkGateway;
import com.xc0ffee.flicker.network.GetPhotosResponse;

public class FlickrMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_main);

        FlickrNetworkGateway.getInstance().getPhotos(new GetPhotosResponse() {
            @Override
            public void onSuccess(FlickrRepsonseModel model) {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
