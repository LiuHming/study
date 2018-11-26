package com.example.HMes.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.HMes.myapplication.Model.UserModel;
import com.example.HMes.myapplication.R;
import com.example.HMes.myapplication.Utils.SPUtils;
import com.example.HMes.myapplication.Utils.StaticClass;
import com.example.HMes.myapplication.bean.MyUser;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideAcitivity.class));
                    } else {
                        MyUser userInfo = UserModel.getInstance().getCurrentUser();
                        if(userInfo != null){
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
    }

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = SPUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            SPUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次运行
            return true;
        }else {
            return false;
        }

    }
}
