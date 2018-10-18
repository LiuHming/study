package com.example.administrator.myapplication.Activity;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

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
    EditText mfg_pw;
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

    @OnClick(){

    }



}
