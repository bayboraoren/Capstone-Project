package com.example.android.capstone.components.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by baybora on 3/4/16.
 */
public class DeliveryRouteAppWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = DeliveryRouteAppWidgetProvider.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Log.w(LOG_TAG, "onUpdate method called");
        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                DeliveryRouteAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(),
                DeliveryRouteAppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        context.startService(intent);

    }
}