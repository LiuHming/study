package com.example.administrator.myapplication.Activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.myapplication.Entity.MyUser;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class SignUpActivity extends BaseActivity {

    @BindView(R.id.us_name)
    EditText mNam;
    @BindView(R.id.us_pw)
    EditText mPsw;
    @BindView(R.id.cm_pw)
    EditText mCpw;
    @BindView(R.id.signup)
    Button mSignup;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_signup;
    }

    @Override
    protected void init() {
        super.init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_tb);
        initToolBar(toolbar,false);
    }

    @OnClick(R.id.signup)
    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                String name = mNam.getText().toString().trim();
                String pw = mPsw.getText().toString().trim();
                String pw1 = mCpw.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pw) && !TextUtils.isEmpty(pw1)) {
                    if(pw.equals(pw1)) {
                        MyUser bu = new MyUser();
                        bu.setUsername(name);
                        bu.setPassword(pw);
                        bu.setDesc(getString(R.string.df_js));
                        bu.setAge(0);
                        bu.setSex(true);
                        UserUtils.signup(SignUpActivity.this,bu);
                        finish();
                    }else {Toast.makeText(SignUpActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();}
                }else {
                    Toast.makeText(SignUpActivity.this,"请完善信息",Toast.LENGTH_SHORT).show();
                }
        }
    }


}
