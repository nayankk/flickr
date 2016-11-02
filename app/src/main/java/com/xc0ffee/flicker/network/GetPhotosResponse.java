package com.xc0ffee.flicker.network;

import com.xc0ffee.flicker.models.FlickrRepsonseModel;

/**
 * Created by nayankk on 11/2/16.
 *
 * Callback to send response to activity
 */

public interface GetPhotosResponse {
    void onSuccess(FlickrRepsonseModel model);
    void onFailure();
}
