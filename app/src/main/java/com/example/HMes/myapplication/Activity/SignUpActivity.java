package com.example.HMes.myapplication.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.example.HMes.myapplication.Event.FinishEvent;
import com.example.HMes.myapplication.Model.BaseModel;
import com.example.HMes.myapplication.Model.UserModel;
import com.example.HMes.myapplication.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


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
    }

    @OnClick(R.id.signup)
    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                String name = mNam.getText().toString().trim();
                String pw = mPsw.getText().toString().trim();
                String pw1 = mCpw.getText().toString().trim();
                UserModel.getInstance().register(name,pw,pw1, new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            EventBus.getDefault().post(new FinishEvent());
                            startActivity(MainActivity.class, null, true);
                        } else {
                            if (e.getErrorCode() == BaseModel.CODE_NOT_EQUAL) {
                                mCpw.setText("");
                            }
                            ToastUtils.showShort(e.getMessage());
                        }
                    }
                });
        }
    }


}
