package com.chartier.virginie.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 16/09/2018.
 */
public class MostPopular {
    @SerializedName("results")
    @Expose
    private List<MostPopularResult> results = null;

    public List<MostPopularResult> getResults() {
        return results;
    }

    public void setResults(List<MostPopularResult> results) {
        this.results = results;
    }

    }


