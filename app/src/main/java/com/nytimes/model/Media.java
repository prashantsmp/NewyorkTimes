package com.nytimes.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Media implements Serializable {

    @SerializedName("media-metadata")
    private List<MediaMetadata> metadataList;

    public List<MediaMetadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<MediaMetadata> metadataList) {
        this.metadataList = metadataList;
    }
}
