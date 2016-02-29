package com.example.android.sunshine.capstone;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

/**
 * Created by baybora on 2/29/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static String LOG_TAG = BaseActivity.class.getSimpleName();

    public Toolbar toolbar;

    public BaseActivity(String classSimpleName){
        LOG_TAG = classSimpleName;
    }

    public void initLayout(int layout,String layoutTitle,boolean hasParent,String homeActionContentDescription){
        setContentView(layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(layoutTitle);
        setSupportActionBar(toolbar);

        if(hasParent){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeActionContentDescription("Go To " + homeActionContentDescription + " Screen");
        }

    }

    public void initBindView(){
        ButterKnife.bind(this);
    }



}
