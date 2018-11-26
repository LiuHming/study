package com.example.HMes.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Event.RefreshEvent;
import com.example.HMes.myapplication.Fragment.TabFragment;
import com.example.HMes.myapplication.Model.UserModel;
import com.example.HMes.myapplication.R;
import com.example.HMes.myapplication.Utils.IMMLeaks;
import com.example.HMes.myapplication.View.AppTitle;
import com.example.HMes.myapplication.View.CommonPopupWindow;
import com.example.HMes.myapplication.View.MyViewPager;
import com.example.HMes.myapplication.View.ShadeView;
import com.example.HMes.myapplication.View.SlideView;
import com.example.HMes.myapplication.bean.MyUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.main_jiemian)
    SlideView mMainSmenu;
    @BindView(R.id.main_title)
    AppTitle apptitle;
    @BindView(R.id.lefticon)
    ImageView lefticon;
    @BindView(R.id.menuicon)
    ImageView menuicon;
    @BindView(R.id.viewpager)
    MyViewPager viewPager;
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
    private CommonPopupWindow popupWindow;
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
        final MyUser user = BmobUser.getCurrentUser(MyUser.class);
        mUsname.setText(user.getUsername());
        //TODO 连接：3.1、登录成功、注册成功或处于登录状态重新打开应用后执行连接IM服务器的操作
        //判断用户是否登录，并且连接状态不是已连接，则进行连接操作
        if (!TextUtils.isEmpty(user.getObjectId()) &&
                BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String uid, BmobException e) {
                    if (e == null) {
                        //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                        //TODO 会话：2.7、更新用户资料，用于在会话页面、聊天页面以及个人信息页面显示
                        BmobIM.getInstance().
                                updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                                        user.getUsername(), user.getAvatar()));
                        EventBus.getDefault().post(new RefreshEvent());
                    } else {
                        ToastUtils.showShort(e.getMessage());
                    }
                }
            });
            //TODO 连接：3.3、监听连接状态，可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
            BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                @Override
                public void onChange(ConnectionStatus status) {
                    ToastUtils.showShort(status.getMsg());
                    LogUtils.i(BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        }
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    private void initView() {
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

    @OnClick({R.id.config,R.id.lefticon,R.id.menuicon,R.id.logout})
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
            case R.id.menuicon:
                if (popupWindow != null && popupWindow.isShowing()) return;
                popupWindow = new CommonPopupWindow.Builder(this)
                        .cancelTouchout(true)
                        .view(R.layout.popup_menu)
                        .isFocusable(true)
                        .build();
                popupWindow.showAsDropDown(apptitle,mMainSmenu.mScreenWidth-popupWindow.getWidth()-15,1);
                break;
            case R.id.logout:
                UserModel.getInstance().logout();
                startActivity(LoginActivity.class,null,true);
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

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.3、通知有在线消息接收
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.4、通知有离线消息接收
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    //TODO 消息接收：8.5、通知有自定义消息接收
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        checkRedPoint();
    }

    /**
     *
     */
    private void checkRedPoint() {
//        //TODO 会话：4.4、获取全部会话的未读消息数量
//        int count = (int) BmobIM.getInstance().getAllUnReadCount();
//        if (count > 0) {
//            iv_conversation_tips.setVisibility(View.VISIBLE);
//        } else {
//            iv_conversation_tips.setVisibility(View.GONE);
//        }
//        //TODO 好友管理：是否有好友添加的请求
//        if (NewFriendManager.getInstance(this).hasNewFriendInvitation()) {
//            iv_contact_tips.setVisibility(View.VISIBLE);
//        } else {
//            iv_contact_tips.setVisibility(View.GONE);
//        }
    }

}
