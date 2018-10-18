package com.example.administrator.myapplication.Entity;

import cn.bmob.v3.BmobUser;

public class UserInfo extends BmobUser {

    private int mAge;
    private boolean mSax;
    private String Nick;

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    private String mDesc;

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public boolean isSax() {
        return mSax;
    }

    public void setSax(boolean sax) {
        mSax = sax;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }
}
