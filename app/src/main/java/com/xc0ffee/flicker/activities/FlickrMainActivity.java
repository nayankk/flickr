package com.xc0ffee.flicker.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.xc0ffee.flicker.R;
import com.xc0ffee.flicker.adapters.FlickrImageAdapter;
import com.xc0ffee.flicker.models.FlickrRepsonseModel;
import com.xc0ffee.flicker.models.Photo;
import com.xc0ffee.flicker.network.FlickrNetworkGateway;
import com.xc0ffee.flicker.network.GetPhotosResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Mail activity */
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
                fetchFailed();
            }
        });
    }

    private void onfetchMore(int page) {
        Log.d(TAG, "fetch more. page = " + page );
        FlickrNetworkGateway.getInstance().getPhotosForPage(new GetPhotosResponse() {
            @Override
            public void onSuccess(FlickrRepsonseModel model) {
                onMoreItemsfetched(model.getPhotos().getPhoto());
            }

            @Override
            public void onFailure() {
                fetchFailed();
            }
        }, page);
    }

    private void onItemsFetched(List<Photo> photoList) {
        mAdapter.addItems(photoList);
    }

    private void onMoreItemsfetched(List<Photo> photoList) {
        mAdapter.addMoreImages(photoList);
    }

    private void fetchFailed() {
        Toast.makeText(this, "Something went wrong, please retry", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                performQuery(query);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void performQuery(String query) {
        FlickrNetworkGateway.getInstance().getPhotosForQuery(new GetPhotosResponse() {
            @Override
            public void onSuccess(FlickrRepsonseModel model) {
                fetchforQueryDone(model.getPhotos().getPhoto());
            }

            @Override
            public void onFailure() {
                fetchFailed();
            }
        }, query);
    }

    private void fetchforQueryDone(List<Photo> photoList) {
        mAdapter.addItems(photoList);
    }
}
