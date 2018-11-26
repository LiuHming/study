package com.example.HMes.myapplication.bean;


import com.example.HMes.myapplication.db.NewFriend;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private int mAge;
    private boolean mSex;
    private String avatar;
    private String mDesc;

    public MyUser() {};

    public MyUser(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public boolean getSex() {
        return mSex;
    }

    public void setSex(boolean sex) {
        mSex = sex;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }
}
