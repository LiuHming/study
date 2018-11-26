package com.example.HMes.myapplication.Activity;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Model.UserModel;
import com.example.HMes.myapplication.R;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.Portrait)
    ImageView mPortrait;
    @BindView(R.id.login_name)
    EditText mLogin_name;
    @BindView(R.id.login_pw)
    EditText mLogin_pw;
    @BindView(R.id.fg_pw)
    TextView mfg_pw;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.signup)
    Button mSignup;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();
    }

    @OnClick({R.id.signup,R.id.fg_pw,R.id.login})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.signup:
                Intent mSignup = new Intent();
                mSignup.setClass(LoginActivity.this,SignUpActivity.class);
                startActivity(mSignup);
                break;
            case R.id.fg_pw:
                Intent mRetrieve = new Intent();
                mRetrieve.setClass(LoginActivity.this,RetrieveActivity.class);
                startActivity(mRetrieve);
                break;
            case R.id.login:
                //输入框
                String name = mLogin_name.getText().toString().trim();
                String password = mLogin_pw.getText().toString().trim();
                //是否为空
                UserModel.getInstance().login(name , password , new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            //登录成功
                            startActivity(MainActivity.class, null, true);
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                    }
                });
                break;
        }
    }

    @OnCheckedChanged(R.id.see_pw)
    public void onCheckChanged (CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            //选择状态 显示明文--设置为可见的密码
            mLogin_pw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mLogin_pw.setSelection(mLogin_pw.getText().length());
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            mLogin_pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mLogin_pw.setSelection(mLogin_pw.getText().length());
        }
    }


}
