package com.chartier.virginie.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 03/10/2018.
 */
public class Media {
    // Serialized JSON name
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadata> mediaMetadata = null;


    //-------------------
    // GETTER & SETTER
    //-------------------

    public List<MediaMetadata> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MediaMetadata> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }
}
