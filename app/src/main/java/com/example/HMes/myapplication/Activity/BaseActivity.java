package com.example.HMes.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.HMes.myapplication.View.AppTitle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    public void startActivity(Class<? extends Activity> target, Bundle bundle, boolean finish) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null)
            intent.putExtra(getPackageName(), bundle);
        startActivity(intent);
        if (finish)
            finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Boolean empty){

    }

}
