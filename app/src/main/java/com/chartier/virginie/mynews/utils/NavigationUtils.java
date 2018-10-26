package com.chartier.virginie.mynews.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.widget.CheckBox;

import static com.chartier.virginie.mynews.controller.WebViewActivity.EXTRA_URL;

public class NavigationUtils {

    public NavigationUtils(){

    }

    //-----------------------
    // OPEN WEB BROWSER
    //-----------------------

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


    //-----------------------
    //  CHECKBOX INPUT
    //-----------------------

    //This method Change color of all text boxes at once
    public void checkboxColorModifier(int color, CheckBox[] boxes) {
        for (CheckBox box : boxes) {
            box.setTextColor(color);
        }
    }

    public boolean onUncheckedBoxes(CheckBox[] boxes) {
        if (!(boxes[0].isChecked() || boxes[1].isChecked() || boxes[2].isChecked()
                || boxes[3].isChecked() || boxes[4].isChecked() || boxes[5].isChecked())) {
            checkboxColorModifier(Color.RED, boxes);
            return true;
        } else {
            checkboxColorModifier(Color.BLACK, boxes);
            return false;
        }

    }

}
