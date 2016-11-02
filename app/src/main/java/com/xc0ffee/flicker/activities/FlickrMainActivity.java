package com.xc0ffee.flicker.activities;

import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xc0ffee.flicker.R;
import com.xc0ffee.flicker.adapters.FlickrImageAdapter;
import com.xc0ffee.flicker.models.FlickrRepsonseModel;
import com.xc0ffee.flicker.models.Photo;
import com.xc0ffee.flicker.network.FlickrNetworkGateway;
import com.xc0ffee.flicker.network.GetPhotosResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlickrMainActivity extends AppCompatActivity {

    @BindView(R.id.rvPhotosView)
    RecyclerView mPhotosView;

    private FlickrImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_main);

        ButterKnife.bind(this);

        mAdapter = new FlickrImageAdapter();
        mPhotosView.setAdapter(mAdapter);
        mPhotosView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mPhotosView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FlickrNetworkGateway.getInstance().getPhotos(new GetPhotosResponse() {
            @Override
            public void onSuccess(FlickrRepsonseModel model) {
                onItemsFetched(model.getPhotos().getPhoto());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void onItemsFetched(List<Photo> photoList) {
        mAdapter.addItems(photoList);
    }
}
