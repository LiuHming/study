package com.example.HMes.myapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.HMes.myapplication.View.AppTitle;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        ButterKnife.bind(this);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        init();
    }

    public abstract int getContentViewResId();

    protected void init() {
    }
    public void initAppTitle(AppTitle apptitle, boolean showHomeAsUp) {
        initAppTitle(apptitle, showHomeAsUp, false);
    }

    public void initAppTitle(AppTitle apptitle,  boolean showHomeAsUp, boolean isShowRight) {
        initAppTitle(apptitle, showHomeAsUp, isShowRight, 0);
    }

    public void initAppTitle(AppTitle apptitle, boolean showHomeAsUp, boolean isShowRight, int rightType) {
//        toolbar.setTitle(name);

    }


}
