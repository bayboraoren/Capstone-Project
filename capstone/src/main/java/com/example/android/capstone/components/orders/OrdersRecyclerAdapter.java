package com.example.android.capstone.components.orders;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
import com.example.android.firebase.domain.OrdersDomain;
import com.example.android.firebase.entity.OrderEntityHelper;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder>{

    public static final String LOG_TAG = OrdersRecyclerAdapter.class.getSimpleName();
    public Activity mActivity;
    public List<OrdersDomain> mOrdersDomains=new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mOrderImageBase64;
        private TextView mOrderName;
        private TextView mOrderDistanceKM;
        private Activity mActivity;
        private OrdersDomain mOrdersDomain;


        public ViewHolder(View view, final Activity activity) {
            super(view);

            mOrderImageBase64 = (ImageView)view.findViewById(R.id.order_imagebase64);
            mOrderName = (TextView)view.findViewById(R.id.order_name);
            mOrderDistanceKM = (TextView)view.findViewById(R.id.order_distance_km);

            mActivity = activity;
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            Log.i(LOG_TAG, mOrdersDomain.getName() + " order clicked...");
            Intent intent = new Intent(mActivity, com.example.android.capstone.RouteActivity.class);

            //order deliver start time
            mOrdersDomain.setOrderStartDeliverTime(new Date().getTime());

            intent.putExtra(OrderEntityHelper.DOMAIN_NAME,mOrdersDomain);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        }

    }

    public OrdersRecyclerAdapter(Activity activity) {
        mActivity = activity;
    }

    public void add(OrdersDomain ordersDomain){
        mOrdersDomains.add(ordersDomain);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OrdersRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v,mActivity);
        return vh;

    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final OrdersDomain ordersDomain = mOrdersDomains.get(position);

        holder.mOrderName.setText(ordersDomain.getName());
        holder.mOrderImageBase64.setImageBitmap(Utils.convertBase64ToImage(ordersDomain.getImageBase64()));
        holder.mOrdersDomain = ordersDomain;

        Location myLocation = Utils.getLastKnownLocation(mActivity);

        LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        LatLng toLatLng = new LatLng(Double.parseDouble(ordersDomain.getLatitude()), Double.parseDouble(ordersDomain.getLongitude()));

        GoogleDirection.withServerKey(mActivity.getResources().getString(R.string.google_maps_direction_key))
                .from(myLatLng)
                .to(toLatLng)
                .transportMode(TransportMode.WALKING)
                        //.avoid(AvoidType.FERRIES)
                        //.avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {

                        List<Route> routes = direction.getRouteList();
                        if(routes.size()>0) {

                            Route route = routes.get(0);
                            if (route != null) {
                                String distanceKM = route.getLegList().get(0).getDistance().getText();
                                holder.mOrderDistanceKM.setText(distanceKM);
                                ordersDomain.setDistanceKM(distanceKM);
                            } else {
                                String what = mActivity.getResources().getString(R.string.what);
                                holder.mOrderDistanceKM.setText(what);
                                ordersDomain.setDistanceKM(what);
                            }

                            mOrdersDomains.set(position,ordersDomain);

                        }

                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mOrdersDomains.size();
    }



}

