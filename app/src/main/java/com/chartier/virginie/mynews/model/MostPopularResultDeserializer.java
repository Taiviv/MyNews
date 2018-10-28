package com.chartier.virginie.mynews.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Virginie Chartier alias Taiviv on 15/10/2018.
 */
  /*public class MostPopularResultDeserializer implements JsonDeserializer<MostPopularResult> {

    @Override
    public MostPopularResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MostPopularResult mostPopularResult = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(json, MostPopularResult.class);
        JsonObject jsonObject = json.getAsJsonObject();

        try{

            if (jsonObject.has("media")) {
                JsonElement elem = jsonObject.get("media");
                if (elem != null && !elem.isJsonNull()) {
                    String valuesString = elem.getAsString();
                    if (!TextUtils.isEmpty(valuesString)){
                        List<Media> values = new Gson().fromJson(valuesString, new TypeToken<ArrayList<Media>>() {}.getType());
                        mostPopularResult.setMedia(values);
                    }
                }
            }

        } catch (Exception e){
            mostPopularResult.setMedia(null);
        }
        return mostPopularResult ;
    }
}**/