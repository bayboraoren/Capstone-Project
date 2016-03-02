package com.example.android.capstone;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

/**
 * Created by baybora on 2/29/16.
 */
public class BaseActivity extends AppCompatActivity {

    public static String LOG_TAG;
    public static String LAYOUT_TITLE;

    public Toolbar toolbar;

    public BaseActivity(String classSimpleName,String layoutTitle){
        LOG_TAG = classSimpleName;
        LAYOUT_TITLE = layoutTitle;
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
