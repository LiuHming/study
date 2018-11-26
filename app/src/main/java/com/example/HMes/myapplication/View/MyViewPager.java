package com.example.HMes.myapplication.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

    private int mViewPagerX;
    private float xIntercept;
    private float xLast = 0;
    private float yIntercept;

    public MyViewPager(@NonNull Context context) {
        super(context,null);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        int[] location = new  int[2] ;
        //this.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        this.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        mViewPagerX = location[0];
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        boolean intercept = false;
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float xDelta = x - xIntercept;
                float yDelta = y - yIntercept;
                if(mViewPagerX > 0){
                    intercept = false;
                }
                if(this.getChildCount() == 0 && xDelta < 0){
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        xLast = x;
        xIntercept = x;
        yIntercept = y;
        return intercept;
    }
}
