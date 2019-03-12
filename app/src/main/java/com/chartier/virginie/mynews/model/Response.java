package com.chartier.virginie.mynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 11/11/2018.
 */
public class Response {
    // Serialized JSON name
    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;


    //-------------------
    // GETTER & SETTER
    //-------------------

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

}
