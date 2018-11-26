package com.example.HMes.myapplication.Model.i;

import com.example.HMes.myapplication.bean.MyUser;

import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * @author :smile
 * @project:QueryUserListener
 * @date :2016-02-01-16:23
 */
public abstract class QueryUserListener extends BmobListener1<MyUser> {

    public abstract void done(MyUser s, BmobException e);

    @Override
    protected void postDone(MyUser o, BmobException e) {
        done(o, e);
    }
}
