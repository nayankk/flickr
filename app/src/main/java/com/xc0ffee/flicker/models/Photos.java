package com.xc0ffee.flicker.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nayankk on 11/2/16.
 *
 * Photos model
 */

public class Photos {
    int page;
    int pages;
    List<Photo> photo = new ArrayList<>();

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<Photo> getPhoto() {
        return photo;
    }
}
