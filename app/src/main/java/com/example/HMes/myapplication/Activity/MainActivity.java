package com.example.HMes.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Entity.MyUser;
import com.example.HMes.myapplication.Fragment.TabFragment;
import com.example.HMes.myapplication.R;
import com.example.HMes.myapplication.Utils.UserUtils;
import com.example.HMes.myapplication.View.AppTitle;
import com.example.HMes.myapplication.View.ShadeView;
import com.example.HMes.myapplication.View.SlideView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_jiemian)
    SlideView mMainSmenu;
    @BindView(R.id.main_title)
    AppTitle apptitle;
    @BindView(R.id.lefticon)
    ImageView lefticon;
    @BindView(R.id.menuicon)
    ImageView menuicon;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.duihua)
    ShadeView duihua;
    @BindView(R.id.lianxiren)
    ShadeView lianxiren;
    @BindView(R.id.guangchang)
    ShadeView guangchang;
    @BindView(R.id.logout)
    Button logout;
    @BindView(R.id.config)
    Button main_config;
    @BindView(R.id.us_ming)
    TextView mUsname;
//    @BindViews({R.id.duihua,R.id.lianxiren,R.id.guangchang})
//    public List<ShadeView> tabIndicators;

    private List<Fragment> tabFragments;
    private List<ShadeView> tabIndicators;
    private FragmentPagerAdapter adapter;
    private long back_pressed;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContentViewResId();
        initData();
        initView();
        viewPager.setAdapter(adapter);
    }
    private void initData() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        mUsname.setText(userInfo.getUsername());
        tabFragments = new ArrayList<>();
        tabIndicators = new ArrayList<>();
        TabFragment weiXinFragment = TabFragment.newInstance(this,"对话");
        TabFragment contactsFragment = TabFragment.newInstance(this,"联系人");
        TabFragment discoverFragment = TabFragment.newInstance(this,"广场");
        tabFragments.add(weiXinFragment);
        tabFragments.add(contactsFragment);
        tabFragments.add(discoverFragment);
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabFragments.size();
            }
            @Override
            public Fragment getItem(int arg0) {
                return tabFragments.get(arg0);
            }
        };
    }
    private void initView() {
        viewPager.addOnPageChangeListener(this);
        tabIndicators.add(duihua);
        tabIndicators.add(lianxiren);
        tabIndicators.add(guangchang);
        duihua.setIconAlpha(0);
        mMainSmenu.setFull(this, -1, R.color.white, 0, 0, 0 );
    }
    /**
     * 重置Tab状态
     */
    private void resetTabsStatus() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            tabIndicators.get(i).setIconAlpha(1);
        }
    }
    /**
     * 如果是直接点击图标来跳转页面的话,position值为0到3,positionOffset一直为0.0
     * 如果是通过滑动来跳转页面的话
     * 假如是从第一页滑动到第二页
     * 在这个过程中,positionOffset从接近0逐渐增大到接近1.0,滑动完成后又恢复到0.0,而position只有在滑动完成后才从0变为1
     * 假如是从第二页滑动到第一页
     * 在这个过程中,positionOffset从接近1.0逐渐减小到0.0,而position一直是0
     *
     * @param position 当前页面索引
     * @param positionOffset 偏移量
     * @param positionOffsetPixels 偏移量
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ShadeView leftTab = tabIndicators.get(position);
            ShadeView rightTab = tabIndicators.get(position + 1);
            leftTab.setIconAlpha(positionOffset);
            rightTab.setIconAlpha(1 - positionOffset);
        }
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @OnClick({R.id.duihua,R.id.lianxiren,R.id.guangchang})
    public void onClick(View v) {
        resetTabsStatus();
        switch (v.getId()) {
            case R.id.duihua:
                tabIndicators.get(0).setIconAlpha(0);
                viewPager.setCurrentItem(0, false);
                break;
            case R.id.lianxiren:
                tabIndicators.get(1).setIconAlpha(0);
                viewPager.setCurrentItem(1, false);
                break;
            case R.id.guangchang:
                tabIndicators.get(2).setIconAlpha(0);
                viewPager.setCurrentItem(2, false);
                break;
        }
    }

    @OnClick({R.id.config,R.id.lefticon,R.id.logout})
    public void Onclick (View v){
        switch (v.getId()){
            case R.id.config:
                Intent mintent = new Intent(MainActivity.this,PWchangeActivity.class);
                startActivity(mintent);
                break;
            case R.id.lefticon:
                if (mMainSmenu.isMenuShowing()) {
                    mMainSmenu.hideMenu();
                } else {
                    mMainSmenu.showMenu();
                }
                break;
            case R.id.logout:
                UserUtils.logout(MainActivity.this);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mMainSmenu.isMenuShowing()) {
            mMainSmenu.hideMenu();
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                ToastUtils.showShort("再点一次退出应用");
            }
            back_pressed = System.currentTimeMillis();
        }
    }

}
