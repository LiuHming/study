package com.example.administrator.myapplication.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.Entity.MyUser;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.SPUtils;
import com.example.administrator.myapplication.Utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.Portrait)
    ImageView mPortrait;
    @BindView(R.id.login_name)
    EditText mLogin_name;
    @BindView(R.id.login_pw)
    EditText mLogin_pw;
    @BindView(R.id.rem_pw)
    CheckBox mrem_pw;
    @BindView(R.id.fg_pw)
    TextView mfg_pw;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.signup)
    Button mSignup;

    private boolean mrempw ;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        super.init();

        mrempw = SPUtils.getBoolean(this,"keeppass",false);
        mrem_pw.setChecked(mrempw);

        if(mrempw){
            String name = SPUtils.getString(this, "name", "");
            String password = SPUtils.getString(this, "password", "");
            mLogin_name.setText(name);
            mLogin_pw.setText(password);
        }
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
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
                    MyUser bu2 = new MyUser();
                    bu2.setUsername(name);
                    bu2.setPassword(password);
                    UserUtils.login(LoginActivity.this,bu2);
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }



}