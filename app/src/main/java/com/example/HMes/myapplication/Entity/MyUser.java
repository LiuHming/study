package com.example.HMes.myapplication.Entity;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser {

    private int mAge;
    private boolean mSex;
    private String Nick;
    private String mDesc;

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public boolean isSex() {
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
