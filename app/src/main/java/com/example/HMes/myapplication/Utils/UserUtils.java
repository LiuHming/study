package com.example.HMes.myapplication.Utils;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Activity.LoginActivity;
import com.example.HMes.myapplication.Activity.MainActivity;
import com.example.HMes.myapplication.bean.MyUser;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

public class UserUtils {
    public static void login(final Context context, MyUser myUser){
        myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if(e==null){
                    Intent mLogin = new Intent();
                    mLogin.setClass(context,MainActivity.class);
                    context.startActivity(mLogin);
                }else{
                    ToastUtils.showShort("用户名或密码不正确");
                    LogUtils.e(e.getMessage());
                }
            }
        });
    }

    public static void signup(final Context context, MyUser myUser){
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    ToastUtils.showShort( "注册成功:" + s.toString());
                } else {
                    ToastUtils.showShort( "注册失败，请检查网络连接");
                    LogUtils.e(e.toString());
                }
            }
        });
    }

    public static void changepw(final Context context, String oldpw , String newpw){
        MyUser.updateCurrentUserPassword(oldpw, newpw, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtils.showShort("密码修改成功，可以用新密码进行登录啦");
                    logout(context);
                }else{
                    ToastUtils.showShort("旧密码不匹配，请重试" );
                    LogUtils.e("失败:" + e.getMessage());
                }
            }

        });
    }

    public static void update(final Context context, MyUser newmyUser){
        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        newmyUser.update(myUser.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    ToastUtils.showShort("更新成功");
                }else{
                    ToastUtils.showShort("更新失败");
                    LogUtils.d("更新用户信息失败"+e.getMessage());
                }
            }
        });

    }

    public static void query(final Context context, String name){
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username", name);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> object, BmobException e) {
                if (e == null) {
//                    toast("查询用户成功:"+object.size());
                } else {
                    ToastUtils.showShort( "查询用户信息失败:" + e.getMessage());
                }
            }
        });
    }

    public static void logout(Context context){
        //TODO 连接：3.2、退出登录需要断开与IM服务器的连接
        BmobIM.getInstance().disConnect();
        MyUser.logOut();   //清除缓存用户对象
        MyUser currentUser = MyUser.getCurrentUser(MyUser.class); // 现在的currentUser是null了
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
    }

}
