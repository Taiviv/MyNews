package com.chartier.virginie.mynews.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.chartier.virginie.mynews.R;
import com.chartier.virginie.mynews.apis.NewYorkTimeStream;
import com.chartier.virginie.mynews.controller.WebViewActivity;
import com.chartier.virginie.mynews.model.SearchArticle;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.chartier.virginie.mynews.controller.WebViewActivity.EXTRA_URL;


/**
 * Created by Virginie Chartier alias Taiviv on 26/10/2018.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private Disposable mDisposable;
    private DateUtils mDateUtils = new DateUtils();
    private String httpResult = "";
    private String subTextUrl = "";
    private String[] arrays;
    private SharedPreferencesUtils mStorage = new SharedPreferencesUtils();

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Load the data into a variable
        arrays = mStorage.loadData(context).split(",");
        // Http request is launched, during the process it check if new article are available
        executeNotificationHttpRequest(context);
    }


    // This method notifies the user even if the application is not launched
    public void sendNotification(Context context) {
        final int NOTIFICATION_ID = 234;
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_URL, subTextUrl);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get an instance of NotificationManager
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.text_notification))
                .setSubText(subTextUrl)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notifications need a channel from SDK >= 26 (Oreo)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence channelName = "Message";
            String channelID = context.getString(R.string.default_notification_channel_id);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelID, channelName, importance);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }

        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }


    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // This method handle the Http rest request takes a model object as parameter and display a result
    private void executeNotificationHttpRequest(final Context context) {

        this.mDisposable = NewYorkTimeStream.streamFetchNotifications(arrays[0],
                "news_desk:(" + arrays[1] + ")")
                .subscribeWith(new DisposableObserver<SearchArticle>() {

                    @Override
                    public void onNext(SearchArticle articleNotification) {
                        //Check is the result is not empty
                        if (articleNotification.getResponse().getDocs().size() != 0) {
                            httpResult = articleNotification.getResponse().getDocs().get(0).getPubDate();
                            subTextUrl = articleNotification.getResponse().getDocs().get(0).getWebUrl();
                            Log.d("Notification", "On Next: " + mDateUtils.setFormatCalendar() + " VS " + mDateUtils.getNotificationFormatDate(httpResult));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Notification", "On Error " + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Notification", "On Complete !");
                        Toast.makeText(context, "Alarm Manager is ok !", Toast.LENGTH_SHORT).show();
                        // Send a notification if the data is available and match with a date format of the current day
                        if (!httpResult.equals("") && mDateUtils.getNotificationFormatDate(httpResult).equals(mDateUtils.setFormatCalendar())) {
                            sendNotification(context);
                        }
                    }
                });
    }

    // Dispose subscription
    private void disposeWhenDestroy() {
        if (this.mDisposable != null && !this.mDisposable.isDisposed())
            this.mDisposable.dispose();
        onDestroy();
    }

    // This method called for better performances
    public void onDestroy() {
        this.disposeWhenDestroy();
    }
}
