package com.chartier.virginie.mynews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Virginie Chartier alias Taiviv on 26/10/2018.
 */

// This class which handle the SharedPreferences and data
public class SharedPreferencesUtils {

    public static final String SEARCH_ARTICLE_NOTIFICATION_VALUES = "SEARCH_ARTICLE_NOTIFICATION_VALUES";
    public static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";

    public SharedPreferencesUtils(){

    }

    // This method allow to save data with SharedPreferences
    public void saveData(Context context, String s) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SEARCH_ARTICLE_NOTIFICATION_VALUES, s);
        editor.apply();
    }

    // This method allow to retrieve data with SharedPreferences and use it
    public String loadData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(SEARCH_ARTICLE_NOTIFICATION_VALUES, null);
    }
}
