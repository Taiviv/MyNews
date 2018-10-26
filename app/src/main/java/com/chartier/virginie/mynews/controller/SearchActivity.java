package com.chartier.virginie.mynews.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.DatePicker;
import android.widget.EditText;

import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.utils.DateUtils;
import com.chartier.virginie.mynews.utils.NavigationUtils;
import com.chartier.virginie.mynews.utils.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    // FOR DATA

    public static final String SEARCH_ARTICLE_VALUES = "SEARCH_ARTICLE_VALUES";
    public String[] checkboxData = new String[6];
    public String[] BOX_VALUES = {"Culture", "Environment", "Foreign", "Politics", "Sports", "Technology"};
    public DateUtils mDateUtils = new DateUtils();
    public TextUtils mTextUtils = new TextUtils();
    public NavigationUtils mNavigationUtils = new NavigationUtils();
    private CheckBox[] mCheckBoxes;
    private Calendar mCalendar;
    private String mBeginDate2, mEndDate2;

    // FOR DESIGN

    @BindView(R.id.query_text_input_layout) TextInputLayout floatingHintLabel;
    @BindView(R.id.search_query_term) EditText mSearchQuery;
    @BindView(R.id.end_date) EditText mEndDate;
    @BindView(R.id.begin_date) EditText mBeginDate;
    @BindView(R.id.activity_search_button)Button mSearchButton;
    @BindView(R.id.checkbox_1) CheckBox mCheckBox1;
    @BindView(R.id.checkbox_2) CheckBox mCheckBox2;
    @BindView(R.id.checkbox_3) CheckBox mCheckBox3;
    @BindView(R.id.checkbox_4) CheckBox mCheckBox4;
    @BindView(R.id.checkbox_5) CheckBox mCheckBox5;
    @BindView(R.id.checkbox_6) CheckBox mCheckBox6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        // Initialized mCheckBoxes variable
        this.mCheckBoxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};
        // Sets methods
        this.configureToolbar();
        this.mTextUtils.displayErrorMessage(floatingHintLabel);
        this.mSearchButton.setOnClickListener(this);
        // Sets methods Date
        this.initializeOnClickBeginDateListener();
        this.nullifyBeginDate();
        this.initializeOnClickEndDateListener();
        this.nullifyEndDate();
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    // This method calls the toolbar layout and fixed it on the action bar then a return home function is displayed.
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configureAndShowActivity() {
        String[] value = {mSearchQuery.getText().toString(), mDateUtils.getNewDesk(checkboxData),
                mDateUtils.getBeginDate(mBeginDate.getText().toString()), mDateUtils.getEndDate(mEndDate.getText().toString())};

        Intent intent = new Intent(getBaseContext(), SearchActivityList.class);
        intent.putExtra(SEARCH_ARTICLE_VALUES, value);
        startActivity(intent);
    }


    //---------------
    //  DATE INPUT
    //---------------

    // -----------------------------------------------------------
    // BEGIN DATE : Text View Listener + DatePicker + Label update
    // -----------------------------------------------------------

    // This method attaches a listener to a TextView that calls a DatePickerDialog when the user clicks on it
    private void initializeOnClickBeginDateListener() {
        mCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBeginDateLabel();
            }

        };

        mBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    // This method updates the value of the text field with the chosen date, the displayed date will be in format: dd/mm/yyyy
    private void updateBeginDateLabel() {
        String mFormat = "dd/MM/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mFormat, Locale.US);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");

        mBeginDate.setText(simpleDateFormat.format(mCalendar.getTime()));
        mBeginDate2 = simpleDateFormat2.format(mCalendar.getTime());
    }

    // This method set date value to null
    private void nullifyBeginDate() {
        mBeginDate2 = null;
    }

    // ---------------------------------------------------------
    // END DATE : TextView Listener + DatePicker + Label update
    // ---------------------------------------------------------

    // This method attaches a listener to a TextView that calls a DatePickerDialog when the user clicks on it
    private void initializeOnClickEndDateListener() {
        mCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDateLabel();
            }

        };

        mEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SearchActivity.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    // This method updates the value of the text field with the chosen date, the displayed date will be in format: dd/mm/yyyy
    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/yyyy";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

        mEndDate.setText(sdf.format(mCalendar.getTime()));
        mEndDate2 = sdf2.format(mCalendar.getTime());

    }

    // This method set date value to null
    private void nullifyEndDate() {
        mEndDate2 = null;
    }


    //-------------------
    //  CHECKBOX INPUT
    //-------------------

    // This method handles the behavior of the checkboxes at the click and if a box is ticked then an action is executed
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        checkboxData[0] = BOX_VALUES[0];
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_1:
                if (checked) {
                    checkboxData[0] = BOX_VALUES[0];
                } else {
                    checkboxData[0] = "";
                }
                break;
            case R.id.checkbox_2:
                if (checked) {
                    checkboxData[1] = BOX_VALUES[1];
                } else {
                    checkboxData[1] = "";
                }
                break;
            case R.id.checkbox_3:
                if (checked) {
                    checkboxData[2] = BOX_VALUES[2];
                } else {
                    checkboxData[2] = "";
                }
                break;
            case R.id.checkbox_4:
                if (checked) {
                    checkboxData[3] = BOX_VALUES[3];
                } else {
                    checkboxData[3] = "";
                }
                break;
            case R.id.checkbox_5:
                if (checked) {
                    checkboxData[4] = BOX_VALUES[4];
                } else {
                    checkboxData[4] = "";
                }
                break;
            case R.id.checkbox_6:
                if (checked) {
                    checkboxData[5] = BOX_VALUES[5];
                } else {
                    checkboxData[5] = "";
                }
                break;
        }
    }


    //------------------
    //  SEARCH BUTTON
    //------------------

    // This method manages the search button that performs all control and verification actions
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        // Every time user click on button input query is checked
        mTextUtils.queryInputIsEmpty(mSearchQuery, floatingHintLabel, getResources().getString(R.string.query_error));
        // At least one check box must be checked
        if (mNavigationUtils.onUncheckedBoxes(mCheckBoxes))
            mTextUtils.snackBarMessage(findViewById(R.id.activity_search), R.string.box_unchecked);
        // if text input is empty OR all checkboxes are empty no access to the activity
        if (!(mSearchQuery.getText().toString().isEmpty()) && !(mNavigationUtils.onUncheckedBoxes(mCheckBoxes))) {
            configureAndShowActivity();
        }
    }
}
