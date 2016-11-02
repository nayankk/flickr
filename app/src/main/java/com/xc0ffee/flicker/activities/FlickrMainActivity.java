package com.xc0ffee.flicker.activities;

import android.content.pm.ProviderInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

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

    private static final String TAG = FlickrMainActivity.class.getSimpleName();

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
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mPhotosView.setLayoutManager(layoutManager);
        mPhotosView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        EndlessRecyclerViewScrollListener scrollListener =
                new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                onfetchMore(page);
            }
        };
        mPhotosView.addOnScrollListener(scrollListener);

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

    private void onfetchMore(int page) {
        Log.d(TAG, "fetch more. page = " + page );
        FlickrNetworkGateway.getInstance().getPhotos(new GetPhotosResponse() {
            @Override
            public void onSuccess(FlickrRepsonseModel model) {
                onMoreItemsfetched(model.getPhotos().getPhoto());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void onItemsFetched(List<Photo> photoList) {
        mAdapter.addItems(photoList);
    }

    private void onMoreItemsfetched(List<Photo> photoList) {
        mAdapter.addMoreImages(photoList);
    }
}
