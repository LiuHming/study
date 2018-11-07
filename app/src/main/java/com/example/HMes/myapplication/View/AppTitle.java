package com.example.HMes.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HMes.myapplication.R;

public class AppTitle extends LinearLayout {

    private ImageView mLefticon;
    private ImageView mMenuicon;
    private TextView mTitle;
    private int mTitleType;//0-只显示title 1-显示返回和title  2-全显示

    public AppTitle(Context context){
        this(context,null);
    }

    public AppTitle (Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public AppTitle (Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initview(context,attrs);
        initdata();

    }

    private void initview(Context context,AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.titleview, this);
        mLefticon = (ImageView) findViewById(R.id.lefticon);
        mMenuicon = (ImageView) findViewById(R.id.menuicon);
        mTitle = (TextView) findViewById(R.id.title);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppTitle);
        int titletype = typedArray.getInteger(R.styleable.AppTitle_titletype,0);
        String title = typedArray.getString(R.styleable.AppTitle_title);
        BitmapDrawable lefticon = (BitmapDrawable) typedArray.getDrawable(R.styleable.AppTitle_leftIcon);
        BitmapDrawable menuicon = (BitmapDrawable) typedArray.getDrawable(R.styleable.AppTitle_menuIcon);
        typedArray.recycle();
        mTitleType = titletype;
        mLefticon.setImageDrawable(lefticon);
        mMenuicon.setImageDrawable(menuicon);
        mTitle.setText(title);
    }

    private void initdata() {
        switch (mTitleType){
            case 0:
                mLefticon.setVisibility(GONE);
                mMenuicon.setVisibility(GONE);
                break;
            case 1:
                mLefticon.setVisibility(VISIBLE);
                mMenuicon.setVisibility(GONE);
                break;
            case 2:
                mLefticon.setVisibility(VISIBLE);
                mMenuicon.setVisibility(VISIBLE);
                break;
                default:
                    mLefticon.setVisibility(GONE);
                    mMenuicon.setVisibility(GONE);
                    break;
        }
    }




}
