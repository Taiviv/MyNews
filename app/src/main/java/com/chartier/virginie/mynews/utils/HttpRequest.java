package com.chartier.virginie.mynews.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chartier.virginie.mynews.R;


/**
 * Created by Virginie Chartier alias Taiviv on 19/09/2018.
 */

/* This class provides several methods that handle the behavior of the user interface before,
 * during, and after running the http request
 */
public class HttpRequest {

    public HttpRequest() {
    }


    //This method personalised the progressbar color
    public void progressBarHandler(ProgressBar progressBar, Context context) {
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
    }


    //This method manages the progress bar is started just before the beginning of the http request
    public void updateUIWhenStartingHTTPRequest(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }


    //This method manages on complete status the http request, the UI interface stop to refresh screen
    public void updateUIWhenStopingHTTPRequest(SwipeRefreshLayout refresh, ProgressBar bar) {
        bar.setVisibility(View.GONE);
        refresh.setRefreshing(false);
    }

    //This method a toast message is displayed to alert the user When the http request is on error status
    public void internetDisable(ProgressBar progressBar, String text, Context context) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}

