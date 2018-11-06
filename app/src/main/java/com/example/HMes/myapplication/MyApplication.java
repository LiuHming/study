package com.example.HMes.myapplication;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {


    @Override
    public void onCreate(){
        super.onCreate();
        Bmob.initialize(this, "40fa99677a0f8232d69c5bd914e56eec");
    }

}
