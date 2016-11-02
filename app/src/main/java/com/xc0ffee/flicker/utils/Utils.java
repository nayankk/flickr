package com.xc0ffee.flicker.utils;

import com.xc0ffee.flicker.models.Photo;

/**
 * Created by nayankk on 11/2/16.
 *
 * Utility class
 */

public class Utils {

    private static final String FORMAT = "http://farm%s.static.flickr.com/%s/%s_%s.jpg";

    public static String getImageUrl(Photo photo) {
        return String.format(FORMAT, photo.getFarm(),
                photo.getServer(), photo.getId(), photo.getSecret());
    }
}
