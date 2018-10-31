package com.example.administrator.myapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;

import java.lang.reflect.Method;

public class MainActivity extends BaseActivity{

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.qunliao:
                    case R.id.jiahaoyou:
                    case R.id.fankui:
                        break;
                }
                return false;
            }
        });
    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //使菜单上图标可见
        if (menu != null && menu instanceof MenuBuilder) {
            //编sdk版本24的情况 可以直接使用setOptionalIconsVisible
            if (Build.VERSION.SDK_INT > 23) {
                MenuBuilder builder = (MenuBuilder) menu;
                builder.setOptionalIconsVisible(true);
            } else {
                //sdk版本24的以下，需要通过反射去执行该方法
                try {
                    MenuBuilder builder = (MenuBuilder) menu;
                    Method m = builder.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } return super.onPrepareOptionsMenu(menu);
    }

}
