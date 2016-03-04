package com.example.android.capstone.components.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.capstone.R;

import java.util.Random;

/**
 * Created by baybora on 3/4/16.
 */
public class DeliveryRouteAppWidgetService extends Service {

    private static final String LOG_TAG = DeliveryRouteAppWidgetService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.delivery_route_appwidget_layout);
            Log.w(LOG_TAG, String.valueOf(number));


            /*remoteViews.setTextViewText(R.id.update,
                    "Random: " + String.valueOf(number));*/

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    DeliveryRouteAppWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            //remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
