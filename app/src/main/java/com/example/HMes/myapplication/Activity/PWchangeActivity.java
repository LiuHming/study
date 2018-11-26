package com.example.HMes.myapplication.Activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Model.UserModel;
import com.example.HMes.myapplication.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PWchangeActivity extends BaseActivity {

    @BindView(R.id.old_pw)
    EditText mOldpw;
    @BindView(R.id.new_pw)
    EditText mNewpw;
    @BindView(R.id.new_pw_confirm)
    EditText mConfirmpw;
    @BindView(R.id.changepw)
    Button mChangepw;
    @BindView(R.id.lefticon)
    ImageView mGoback;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_changpw;
    }

    @OnClick ({R.id.changepw,R.id.lefticon})
    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.changepw:
            String oldpw = mOldpw.getText().toString().trim();
            String pw = mNewpw.getText().toString().trim();
            String pw1 = mConfirmpw.getText().toString().trim();
            if (!TextUtils.isEmpty(oldpw) && !TextUtils.isEmpty(pw) && !TextUtils.isEmpty(pw1)) {
                if (5 < pw.length() && pw.length() < 21) {
                    if (pw.equals(pw1)) {
                        UserModel.getInstance().changepw(oldpw,pw);
                        UserModel.getInstance().logout();
                        startActivity(LoginActivity.class,null,true);
                        finish();
                    } else {
                        ToastUtils.showShort("两次输入密码不一致");
                    }
                } else {
                    ToastUtils.showShort("密码需在6-20位之间");
                }
            } else {
                ToastUtils.showShort("请完善信息");
            }
            break;
            case R.id.lefticon:
                finish();
                break;
        }
    }
}
