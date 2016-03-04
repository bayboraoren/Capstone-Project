package com.example.android.capstone.components.orders;

/**
 * Created by baybora on 3/3/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.example.android.capstone.R;
import com.example.android.capstone.util.Utils;
import com.example.android.firebase.entity.LocationEntity;
import com.example.android.firebase.entity.OrderEntity;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrdersSearchResultsCursorAdapter extends OrdersRecyclerViewCursorAdapter<OrdersSearchResultsCursorAdapter.SearchResultViewHolder>
        implements View.OnClickListener {

    public static final String LOG_TAG = OrdersSearchResultsCursorAdapter.class.getSimpleName();

    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    public Activity mActivity;

    public OrdersSearchResultsCursorAdapter(final Activity activity) {
        super();
        mActivity = activity;
        this.layoutInflater = LayoutInflater.from(activity);

    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.order_item, parent, false);
        view.setOnClickListener(this);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchResultViewHolder holder, final Cursor cursor) {
        holder.bindData(cursor, mActivity);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClicked(Cursor cursor) {
                Log.i("", "");
            }
        });
    }

     /*
     * View.OnClickListener
     */

    @Override
    public void onClick(final View view) {
        if (this.onItemClickListener != null) {

            final RecyclerView recyclerView = (RecyclerView) view.getParent();
            final int position = recyclerView.getChildLayoutPosition(view);

            if (position != RecyclerView.NO_POSITION) {

                final Cursor cursor = this.getItem(position);
                this.onItemClickListener.onItemClicked(cursor);

                OrderEntity orderEntity = OrderEntityHelper.convertToOrderEntity(cursor);
                Log.i(LOG_TAG, orderEntity.getName() + mActivity.getResources().getString(R.string.order_clicked));
                Intent intent = new Intent(mActivity, com.example.android.capstone.RouteActivity.class);

                //set selected order
                OrderEntityHelper.setSelectedOrder(orderEntity);

                //update widget
                Utils.updateWidget(mActivity);

                //order deliver start time
                orderEntity.setOrderStartDeliverTime(new Date().getTime());
                intent.putExtra(OrderEntityHelper.DOMAIN_NAME, orderEntity);
                mActivity.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


            }
        }
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.order_name)
        TextView orderName;

        @Bind(R.id.order_imagebase64)
        ImageView orderImageBase64;

        @Bind(R.id.order_distance_km)
        TextView orderDistanceKM;

        private View mItemView;

        public SearchResultViewHolder(final View itemView) {
            super(itemView);
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Cursor cursor, final Activity mActivity) {

            final OrderEntity orderEntity = OrderEntityHelper.convertToOrderEntity(cursor);

            this.orderName.setText(orderEntity.getName());
            this.orderImageBase64.setImageBitmap(Utils.convertBase64ToImage(orderEntity.getImageBase64()));
            this.orderDistanceKM.setText(orderEntity.getDistanceKM());

            //when order selected change background color
            if(null!=OrderEntityHelper.getSelectedOrder() && orderEntity.get_id()==OrderEntityHelper.getSelectedOrder().get_id()) {
                this.mItemView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.order_item_background_selected));
            }else{
                this.mItemView.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.order_item_background));
            }


            if (orderEntity.getLocationEntity() != null) {
                orderDistanceKM.setText(orderEntity.getDistanceKM());
            } else {

                Location myLocation = Utils.getLastKnownLocation(mActivity);

                long locationEntityId = cursor.getLong(cursor.getColumnIndex(OrderEntityHelper.LOCATION));
                LocationEntity locationEntity = LocationEntity.load(LocationEntity.class, locationEntityId);

                LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng toLatLng = new LatLng(Double.parseDouble(locationEntity.getLatitude()), Double.parseDouble(locationEntity.getLongitude()));

                GoogleDirection.withServerKey(mActivity.getResources().getString(R.string.google_maps_direction_key))
                        .from(myLatLng)
                        .to(toLatLng)
                        .transportMode(TransportMode.DRIVING)
                        .execute(new DirectionCallback() {
                            @Override
                            public void onDirectionSuccess(Direction direction) {

                                List<Route> routes = direction.getRouteList();
                                if (routes.size() > 0) {

                                    Route route = routes.get(0);

                                    if (route != null) {

                                        String distanceKM = route.getLegList().get(0).getDistance().getText();
                                        orderDistanceKM.setText(distanceKM);

                                    } else {

                                        String what = mActivity.getResources().getString(R.string.what);
                                        orderDistanceKM.setText(what);

                                    }


                                }

                            }

                            @Override
                            public void onDirectionFailure(Throwable t) {

                            }
                        });


            }
        }
    }


    public interface OnItemClickListener {
        void onItemClicked(Cursor cursor);
    }


}
