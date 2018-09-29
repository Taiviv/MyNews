package com.chartier.virginie.mynews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static com.chartier.virginie.mynews.controller.WebViewActivity.EXTRA_URL;



/**
 * Created by Virginie Chartier alias Taiviv on 19/09/2018.
 */
public class Helper {

    public Helper() {
    }

    //=======================
    // STRING FORMATTER
    //=======================


    //This method handle the date string format on the item of the recycler view
    public String getItemArticleFormatedDate(String dateToChange) {
        String sub[] = dateToChange.substring(2, 10).split("-");
        return String.format("%s/%s/%s", sub[2], sub[1], sub[0]);
    }


    //=======================
    // OPEN WEB BROWSER
    //=======================

    //This method use to open a Uri thanks the WebViewer from the device
    @Deprecated
    public void openWebBrowser(String url, Context context) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    //This method start an activity by displaying a web content inside
    public void openActivityAsBrowser(String url, Context context, Class aClass) {
        Intent intent2 = new Intent(context, aClass);
        intent2.putExtra(EXTRA_URL, url);
        context.startActivity(intent2);
    }
}
