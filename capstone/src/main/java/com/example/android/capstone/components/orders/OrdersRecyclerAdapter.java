package com.example.android.capstone.components.orders;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.capstone.R;
import com.example.android.capstone.util.Utils;
import com.example.android.util.domain.OrdersDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baybora on 3/2/16.
 */
public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder>{

    public static final String LOG_TAG = OrdersRecyclerAdapter.class.getSimpleName();
    public Activity mActivity;
    private List<OrdersDomain> mOrdersDomains=new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mOrderImageBase64;
        private TextView mOrderName;
        private TextView mOrderDistanceKM;
        private Activity mActivity;


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
            Log.i(LOG_TAG, "");

            Intent intent = new Intent(mActivity, com.example.android.capstone.RouteActivity.class);
            mActivity.startActivity(intent);
            mActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            Log.d("", getAdapterPosition() + "");

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        OrdersDomain ordersDomain = mOrdersDomains.get(position);

        holder.mOrderDistanceKM.setText("");
        holder.mOrderName.setText(ordersDomain.getName());
        holder.mOrderImageBase64.setImageBitmap(Utils.convertImageToBase64(ordersDomain.getImageBase64()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mOrdersDomains.size();
    }
}

