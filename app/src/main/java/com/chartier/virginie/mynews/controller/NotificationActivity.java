package com.chartier.virginie.mynews.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.utils.AlarmReceiver;
import com.chartier.virginie.mynews.utils.DateUtils;
import com.chartier.virginie.mynews.utils.NavigationUtils;
import com.chartier.virginie.mynews.utils.TextUtils;
import com.chartier.virginie.mynews.utils.SharedPreferencesUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    // FOR DATA

    public final String[] BOX_VALUES = {"Culture", "Environment", "Foreign", "Politics", "Sports", "Technology"};
    public String[] checkboxData = new String[6];
    public TextUtils mTextUtils = new TextUtils();
    public NavigationUtils mNavigationUtils  = new NavigationUtils();
    public DateUtils mDateUtils = new DateUtils();
    private CheckBox[] mCheckBoxes;
    private PendingIntent mPendingIntent;
    private AlarmManager mAlarmManager;
    private SharedPreferencesUtils mStorage = new SharedPreferencesUtils();


    // FOR DESIGN

    @BindView(R.id.query_text_input_layout) TextInputLayout floatingHintLabel;
    @BindView(R.id.search_query_term) EditText mSearchQuery;
    @BindView(R.id.checkbox_1) CheckBox mCheckBox1;
    @BindView(R.id.checkbox_2) CheckBox mCheckBox2;
    @BindView(R.id.checkbox_3) CheckBox mCheckBox3;
    @BindView(R.id.checkbox_4) CheckBox mCheckBox4;
    @BindView(R.id.checkbox_5) CheckBox mCheckBox5;
    @BindView(R.id.checkbox_6) CheckBox mCheckBox6;
    @BindView(R.id.switch_button) Switch mSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        this.mCheckBoxes = new CheckBox[]{mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4, mCheckBox5, mCheckBox6};
        this.configureToolbar();
        this.mTextUtils.displayErrorMessage(floatingHintLabel);
        this.switchButton();
        this.configureAlarmManager(this);
    }


    // -----------------
    // CONFIGURATION
    // -----------------

    // This method calls the toolbar layout and fixed it on the action bar then a return home function is displayed.
    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // This method configure the alarm manager to perform an async task automatically
    private void configureAlarmManager(Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(NotificationActivity.this,
                0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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


    // This method manage the switch Button (with a test Alarm)
    private void switchButton() {
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Every time user click on button input query is checked
                    if (mTextUtils.queryInputIsEmpty(mSearchQuery, floatingHintLabel, getResources().getString(R.string.query_error)))
                        mSwitch.setChecked(false);
                    // At least one checkbox must be checked
                    if (mNavigationUtils.onUncheckedBoxes(mCheckBoxes)) {
                        mSwitch.setChecked(false);
                        mTextUtils.snackBarMessage(findViewById(R.id.activity_notification), R.string.box_unchecked);
                    }
                    // if text input is empty or all checkboxes are empty no access to the activity
                    if (!(mSearchQuery.getText().toString().isEmpty()) && !(mNavigationUtils.onUncheckedBoxes(mCheckBoxes))) {
                        mSwitch.setChecked(true);
                        // Launch Alarm Manager
                        dataTransmission();
                        startAlarm();
                    } // Alarm Manager is disabled
                } else {
                    stopAlarm();
                }
            }
        });
    }


    // ---------------------------------------------
    // SCHEDULE TASK (AlarmManager & Data)
    // ---------------------------------------------

    // This method start Alarm
    private void startAlarm() {
        // The schedule is set to be launch at midnight
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 20);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, 1);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, mPendingIntent);
        Toast.makeText(this, "Alarm Activated !", Toast.LENGTH_SHORT).show();
    }

    // This method stop Alarm
    private void stopAlarm() {
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert mAlarmManager != null;
        mAlarmManager.cancel(mPendingIntent);
        Toast.makeText(this, "Alarm Deactivated !", Toast.LENGTH_SHORT).show();
    }


    // Data is transmitted to another class through SharedPreferences.
    public void dataTransmission() {
        String[] value = {mSearchQuery.getText().toString(), mDateUtils.getNewDesk(checkboxData)};
        StringBuilder stringBuilder = new StringBuilder();
        for (String aValue : value) {
            stringBuilder.append(aValue).append(",");
        }
        // method "saveData" store data in SharedPreferences
        mStorage.saveData(this, stringBuilder.toString());
    }
}
