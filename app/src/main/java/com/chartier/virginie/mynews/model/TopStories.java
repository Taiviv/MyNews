package com.chartier.virginie.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 16/09/2018.
 */
public class TopStories {
    @SerializedName("results")
    @Expose
    private List<TopStoriesResult> results = null;

    public List<TopStoriesResult> getResults() {
        return results;
    }

    public void setResults(List<TopStoriesResult> results) {
        this.results = results;
    }



}

