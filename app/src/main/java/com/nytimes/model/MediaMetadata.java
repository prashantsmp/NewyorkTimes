package com.nytimes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaMetadata implements Serializable {

    @SerializedName("url")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
