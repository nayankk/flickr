package com.xc0ffee.flicker.models;

/**
 * Created by nayankk on 11/2/16.
 *
 * Photo model
 */

public class Photo {
    String id;
    String server;
    String secret;
    String farm;

    public String getId() {
        return id;
    }

    public String getServer() {
        return server;
    }

    public String getSecret() {
        return secret;
    }

    public String  getFarm() {
        return farm;
    }
}
