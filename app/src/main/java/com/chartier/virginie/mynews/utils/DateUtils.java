package com.chartier.virginie.mynews.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Virginie Chartier alias Taiviv on 26/10/2018.
 */

public class DateUtils {

    public DateUtils() {

    }

    //-----------------------
    // STRING FORMATTER
    //-----------------------

    //This method handle the date string format on the item of the recycler view
    public String getItemArticleFormatedDate(String dateToChange) {
        String sub[] = dateToChange.substring(2, 10).split("-");
        return String.format("%s/%s/%s", sub[2], sub[1], sub[0]);
    }


    // This method handle the date string format for the notifications
    public String getNotificationFormatDate(String dateToChange) {
        String sub[] = dateToChange.substring(0, 10).split("-");
        return String.format("%s%s%s", sub[0], sub[1], sub[2]);
    }


    //This method format the date string for the Api, if it's empty it gets the current date
    public String getEndDate(String endDate) {
        if (endDate.isEmpty())
            return setFormatCalendar();
        String[] fDate = endDate.split("/");
        return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }


    //This method format the date string for the Api, if it's empty it gets the current date - 10 years
    public String getBeginDate(String beginDate) {
        if (beginDate.isEmpty())
            return setFormatCalendarMinusYear();
        String[] fDate = beginDate.split("/");
        return String.format("%s%s%s", fDate[2], fDate[1], fDate[0]);
    }


    //This method get all the value of the checkBox widget into a single string (using Lucene syntax)
    public String getNewDesk(String[] strings) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                if (i > 0 && ((!strings[i].isEmpty())))
                    res.append(" ");
                res.append(strings[i]);
            }
        }
        //If result is empty it return "Culture" else it return the result content
        return res.toString().isEmpty() ? "" : res.toString();
    }


    //----------------------
    // DATE FORMATTER
    //----------------------

    //This method get the current date and format into "yyyyMMdd" pattern
    public String setFormatCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", simpleDateFormat.format(cal.getTime()));
    }



    //This method get the current date minus 1 year and format into "yyyyMMdd" pattern
    public String setFormatCalendarMinusYear() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return String.format("%s", sdf.format(cal.getTime()));
    }
}
