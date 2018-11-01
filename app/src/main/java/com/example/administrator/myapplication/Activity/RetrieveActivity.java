package com.example.administrator.myapplication.Activity;


import android.support.v7.widget.Toolbar;

import com.example.administrator.myapplication.R;

public class RetrieveActivity extends BaseActivity{

    @Override
    public int getContentViewResId() {
        return R.layout.activity_retrieve;
    }

    @Override
    protected void init() {
        super.init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.retrieve_tb);
        initToolBar(toolbar,true);
    }

}
