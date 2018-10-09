package com.example.administrator.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Adapter.pageAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideAcitivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager vp_guide;
    private List<ImageView> mImglist;
    private LinearLayout dian_container;
    private TextView mSkip;
    private TextView btnStart;
    private int mGurrentIndex = 0;
    private int[] imgArry = {R.drawable.guide_1,R.drawable.guide_2,
            R.drawable.guide_3,R.drawable.guide_4};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_guide);
        initview();
    }

    private void initview() {
        vp_guide = (ViewPager)findViewById(R.id.vp_guide);
        mSkip = (TextView) findViewById(R.id.Skip);
        mSkip.setVisibility(View.VISIBLE);
        btnStart = (TextView) findViewById(R.id.btnStart);
        btnStart.setVisibility(View.GONE);
        mSkip.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        dian_container = (LinearLayout)findViewById(R.id.dian_container);
        mImglist = new ArrayList<>();
        for (int i = 0;i < imgArry.length;i++){
            ImageView mGuide = new ImageView(this);
            mGuide.setImageResource(imgArry[i]);
            mGuide.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImglist.add(mGuide);
            ImageView mdian_off = new ImageView(this);
            mdian_off.setImageResource(R.drawable.dian_off);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(78, 78);
            if (i > 0) {
                params.leftMargin = 10;//设置圆点边距
            }
            mdian_off.setLayoutParams(params);
            dian_container.addView(mdian_off);//将圆点添加到容器中
        }
        vp_guide.setAdapter(new pageAdapter(mImglist));
        //添加监听
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
//                // 滚动过程中
//                // 红色小圆点的移动距离=移动百分比*两个圆点的间距
//                // 更新小红点距离
//                int dis = (int) (mDianD * positionOffset) + position * mDianD;//
//                // 因为移动完一个界面后，百分比会归0，所以要加上移动过的单位position*mPointDis
//                //获取小圆点的布局属性，更新左边距
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mDian_on
//                        .getLayoutParams();
//                params.leftMargin = dis;// 修改左边距
//                // 重新设置布局参数
//                mDian_on.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

                if (position == imgArry.length - 1) {// 最后一个页面
                    mSkip.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);// 显示立即进入的按钮
                }else{
                    mSkip.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.GONE);// 显示立即进入的按钮
                }

                //根据监听的页面改变当前页对应的小圆点
                mGurrentIndex = position;
                for (int i = 0; i < dian_container.getChildCount(); i++) {
                    ImageView imageView = (ImageView) dian_container.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.dian_on);
                    } else {
                        imageView.setImageResource(R.drawable.dian_off);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Skip:
            case R.id.btnStart:
                Intent mIntent = new Intent();
                mIntent.setClass(this,MainActivity.class);
                this.startActivity(mIntent);
        }
    }
}
