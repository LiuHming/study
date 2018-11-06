package com.example.HMes.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.HMes.myapplication.R;

public class AppTitle extends LinearLayout {

    private ImageView mLefticon;
    private ImageView mMenuicon;

    public AppTitle(Context context){
        this(context,null);
    }

    public AppTitle (Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public AppTitle (Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.titleview, this);
        mLefticon = (ImageView) findViewById(R.id.lefticon);
        mMenuicon = (ImageView) findViewById(R.id.menuicon);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle);
        //type:0-只有title 1-左图标+title 2-全显示
        int titletype = typedArray.getInteger(R.styleable.AppTitle_titletype,0);
        String title = typedArray.getString(R.styleable.AppTitle_title);
        BitmapDrawable lefticon = (BitmapDrawable) typedArray.getDrawable(R.styleable.AppTitle_leftIcon);
        BitmapDrawable menuicon = (BitmapDrawable) typedArray.getDrawable(R.styleable.AppTitle_menuIcon);
        typedArray.recycle();

    }


}
