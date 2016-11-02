package com.xc0ffee.flicker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xc0ffee.flicker.R;
import com.xc0ffee.flicker.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nayankk on 11/2/16.
 *
 * Recycler view adapter
 */

public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageAdapter.ViewHolder> {

    private List<Photo> mPhotos = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo photo = mPhotos.get(position);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPhoto) ImageView mPhoto;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    /* Clears and adds items to adapater */
    public void addItems(List<Photo> photoList) {
        mPhotos.clear();
        mPhotos.addAll(photoList);
        notifyItemRangeChanged(0, photoList.size());
    }
}
