package com.example.HMes.myapplication.bean;

import cn.bmob.v3.BmobObject;

/**好友表
 * @author smile
 * @project Friend
 * @date 2016-04-26
 */
//TODO 好友管理：9.1、创建好友表
public class Friend extends BmobObject {

    private MyUser user;
    private MyUser friendUser;

    private transient String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(MyUser friendUser) {
        this.friendUser = friendUser;
    }
}
