package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswdActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.phone_check_et)
    EditText phoneCheckEt;
    @BindView(R.id.get_check_tv)
    TextView getCheckTv;
    @BindView(R.id.new_passwd_et)
    EditText newPasswdEt;
    @BindView(R.id.re_new_passwd_et)
    EditText reNewPasswdEt;
    @BindView(R.id.get_passwd_btn)
    TextView getPasswdBtn;

    private final static int TIME = 101;
    private int time_tatol = 60;

    Handler myHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    if (!isFinishing()) {
                        time_tatol--;
                        getCheckTv.setText(time_tatol + "秒后刷新");
                        if (time_tatol <= 0) {
                            getCheckTv.setText("獲取驗證碼");
                            getCheckTv.setEnabled(true);
                            getCheckTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_right_round_phone_check_));
                        }
                        myHanlder.sendEmptyMessageDelayed(TIME, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwd);
        ButterKnife.bind(this);
        initTopBar("取回密碼");
    }

    @OnClick({R.id.get_check_tv, R.id.get_passwd_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_check_tv:
                getCheckPhone();
                break;
            case R.id.get_passwd_btn:
                getPasswd();
                break;
        }
    }


    private void getPasswd() {
        String phoneCheck = phoneCheckEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String newpasswd = newPasswdEt.getText().toString().trim();
        String renewpasswd = reNewPasswdEt.getText().toString().trim();

        if(TextUtils.isEmpty(newpasswd)||TextUtils.isEmpty(phoneCheck)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(renewpasswd)){
            showToast("請填寫完整信息");
            return;
        }

        if(!TextUtils.equals(newpasswd,renewpasswd)){
            showToast("兩次密碼不一致!");
            return;
        }

        HttpMethods.getInstance().resetPasswd(new MySubscriber<EmptyEntity>(this) {
            @Override
            public void onHttpCompleted(HttpResult httpResult) {
                if (httpResult.isStatus()) {
                    showToast("重置密碼成功");
                    toFinish();
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }

        }, phone, phoneCheck, newpasswd);
    }


    private void getCheckPhone() {
        String phoneNum = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            showToast("請輸入手機號碼");
            return;
        }


        HttpMethods.getInstance().getPhoneCheck(new MySubscriber<EmptyEntity>(this) {
            @Override
            public void onHttpCompleted(HttpResult<EmptyEntity> httpResult) {
                if (httpResult.isStatus()) {
                    showToast("驗證碼發送成功，稍後請留言你的短信!");
                    time_tatol = 60;
                    getCheckTv.setText(time_tatol + "秒后刷新");
                    getCheckTv.setEnabled(false);
                    getCheckTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_right_round_phone_unable_check_));
                    myHanlder.sendEmptyMessageDelayed(TIME, 1000);
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        }, phoneNum, 2);
    }

    
}
