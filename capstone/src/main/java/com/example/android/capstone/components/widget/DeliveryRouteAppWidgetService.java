package com.example.android.capstone.components.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.android.capstone.LoginActivity;
import com.example.android.capstone.R;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;

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

            OrderEntity selectedOrderEntity = OrderEntityHelper.getSelectedOrder();

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.delivery_route_appwidget_layout);


            if (selectedOrderEntity != null) {
                remoteViews.setTextViewText(R.id.order_name, selectedOrderEntity.getName());
                remoteViews.setTextViewText(R.id.customer_name, selectedOrderEntity.getCustomer());
                remoteViews.setTextViewText(R.id.order_distance_km, selectedOrderEntity.getDistanceKM());
                remoteViews.setImageViewBitmap(R.id.order_imagebase64, Utils.convertBase64ToImage(selectedOrderEntity.getImageBase64()));
            }else{
                remoteViews.setTextViewText(R.id.order_name, getText(R.string.app_name));
                remoteViews.setTextViewText(R.id.customer_name, getText(R.string.please_select_order));
                remoteViews.setImageViewBitmap(R.id.order_imagebase64,Utils.convertDrawabletoBitmap(this,R.mipmap.ic_launcher));
            }

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    DeliveryRouteAppWidgetProvider.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            //remoteViews.setOnClickPendingIntent(R.id.layout, pendingIntent);


            ///
            Intent configIntent = new Intent(this, LoginActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(this, 0, configIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.layout, configPendingIntent);
            appWidgetManager.updateAppWidget(allWidgetIds, remoteViews);
            ///



            ///



            ///


            appWidgetManager.updateAppWidget(widgetId, remoteViews);

            stopSelf();

        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
