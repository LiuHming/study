package com.example.administrator.myapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.MainActivity;
import com.example.administrator.myapplication.Entity.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class UserUtils {
    public static void login(final Context context, MyUser myUser){
        myUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Intent mLogin = new Intent();
                    mLogin.setClass(context,MainActivity.class);
                    context.startActivity(mLogin);
                }else{
                    Toast.makeText(context,"用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    LUtils.e(e.getMessage());
                }
            }
        });
    }

    public static void signup(final Context context, MyUser myUser){
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "注册成功:" + s.toString(), Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(context, "注册失败，请检查网络连接" , Toast.LENGTH_SHORT);
                    LUtils.e(e.toString());
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
                    Toast.makeText(context,"更新用户信息成功",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(context,"更新用户信息失败:"+e.getMessage(),Toast.LENGTH_SHORT);
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
                    Toast.makeText(context, "更新用户信息失败:" + e.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public static void logout(){
        MyUser.logOut();   //清除缓存用户对象
        MyUser currentUser = MyUser.getCurrentUser(MyUser.class); // 现在的currentUser是null了
    }


}
