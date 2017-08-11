package com.likeit.currenciesapp.activity;


import android.os.Bundle;
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

public class ModifyPasswdActivity extends BaseActivity {


    @BindView(R.id.now_passwd_et)
    EditText nowPasswdEt;
    @BindView(R.id.new_passwd_et)
    EditText newPasswdEt;
    @BindView(R.id.re_new_passwd_et)
    EditText reNewPasswdEt;
    @BindView(R.id.get_passwd_btn)
    TextView getPasswdBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passwd);
        ButterKnife.bind(this);
        initTopBar("修改密碼");
    }

    @OnClick({R.id.get_passwd_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_passwd_btn:
                getPasswd();
                break;
        }
    }


    private void getPasswd() {
        String nowPasswd = nowPasswdEt.getText().toString().trim();
        String newpasswd = newPasswdEt.getText().toString().trim();
        String renewpasswd = reNewPasswdEt.getText().toString().trim();

        if (TextUtils.isEmpty(newpasswd) || TextUtils.isEmpty(nowPasswd) || TextUtils.isEmpty(renewpasswd)) {
            showToast("請填寫完整信息");
            return;
        }

        if (!TextUtils.equals(newpasswd, renewpasswd)) {
            showToast("兩次密碼不一致!");
            return;
        }

        HttpMethods.getInstance().modifyPasswd(new MySubscriber<EmptyEntity>(this) {
            @Override
            public void onHttpCompleted(HttpResult httpResult) {
                if (httpResult.isStatus()) {
                    showToast("修改密碼成功");
                    toFinish();
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }

        },uKey,"", nowPasswd, newpasswd);
    }


}
