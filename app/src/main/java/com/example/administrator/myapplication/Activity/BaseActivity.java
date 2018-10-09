package com.example.administrator.myapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.myapplication.MyApplication;

public class BaseActivity extends AppCompatActivity {

    public MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        initData();
    }

    public void setContentView() {
    }

    private void initData() {
    }



}
