package com.chartier.virginie.mynews.utils;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TextUtils {

    public TextUtils(){

    }

    //-----------------------
    //  QUERY TEXT INPUT
    //-----------------------

    //This method handle the search text widget, before and after text change
    public void displayErrorMessage(final TextInputLayout textInputLayout) {
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            //The text input set up an hint for the user
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayout.setHintEnabled(true);
            }

            //When user type something the error alert is disabled
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setErrorEnabled(false);
                textInputLayout.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //This method check if the search query input is empty, and if yes an error message occur
    public boolean queryInputIsEmpty(EditText searchQuery, TextInputLayout textInputLayout, CharSequence msg) {
        if (searchQuery.getText().toString().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(msg);
            return true;

        } else {
            textInputLayout.setErrorEnabled(false);
            return false;
        }
    }


    //-----------------------
    //  MESSAGE
    //-----------------------

    //This method handle SnackBar message
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void snackBarMessage(View view, int msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

}
