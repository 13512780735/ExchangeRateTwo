package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone_check_et)
    EditText phoneCheckEt;
    @BindView(R.id.get_check_tv)
    TextView getCheckTv;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.re_passwd_et)
    EditText rePasswdEt;
    @BindView(R.id.checkbox_cb)
    CheckBox checkboxCb;
    @BindView(R.id.registed_btn)
    TextView registedBtn;
    @BindView(R.id.name_et)
    EditText nameEt;

    private final static int TIME = 101;
    @BindView(R.id.protocol_tv)
    TextView protocolTv;
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initTopBar("註冊");
    }

    @OnClick({R.id.get_check_tv, R.id.registed_btn,R.id.protocol_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.protocol_tv:
                Bundle bundle=new Bundle();
                bundle.putString(WebActivity.WEB_TITLE,"服務條款");
                bundle.putString(WebActivity.WEB_URL,"http://dtb.wbteam.cn/api.php?m=login&a=reg_detail");
                toActivity(WebActivity.class,bundle);
                break;
            case R.id.get_check_tv:
                getCheckPhone();
                break;
            case R.id.registed_btn:
                String name = nameEt.getText().toString().trim();
                String phoneCheck = phoneCheckEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();
                String passwd = passwdEt.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneCheck) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(passwd)) {
                    showToast("請填寫完整信息");
                    return;
                }

                if (!checkboxCb.isChecked()) {
                    showToast("請同意條款");
                    return;
                }


                HttpMethods.getInstance().registerUser(new MySubscriber<EmptyEntity>(this) {
                    @Override
                    public void onHttpCompleted(HttpResult httpResult) {
                        if (httpResult.isStatus()) {
                            showToast("註冊成功");
                            toFinish();
                        } else {
                            showToast(httpResult.getMsg());
                        }
                    }

                    @Override
                    public void onHttpError(Throwable e) {

                    }

                }, getuicid, phone, passwd, name, phoneCheck);
                break;
        }
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
        }, phoneNum, 1);
    }
}
