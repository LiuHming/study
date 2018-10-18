package com.example.administrator.myapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        ButterKnife.bind(this);
        init();
    }

    public abstract int getContentViewResId();

    protected void init() {
    }



}
