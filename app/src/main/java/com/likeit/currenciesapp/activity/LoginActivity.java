package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.BuildConfig;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.entity.LoginUserInfoEntity;
import com.likeit.currenciesapp.entity.UserInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.runtimepermissions.PermissionsManager;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.checkbox_cb)
    CheckBox checkboxCb;
    @BindView(R.id.test_login_btn)
    TextView testLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        checkboxCb.setChecked(PreferencesUtil.getBooleanValue(PreferConfigs.isRememberLogin,true));
        checkboxCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtil.putBooleanValue(PreferConfigs.isRememberLogin, isChecked);
            }
        });

        if(BuildConfig.DEBUG){
            testLoginBtn.setVisibility(View.VISIBLE);
        }

        if (PreferencesUtil.getBooleanValue(PreferConfigs.isRememberLogin,true) && PreferencesUtil.getBooleanValue(PreferConfigs.isLogin)) {
//            toActivity(MainActivity.class);
//            toFinish();


            final String passwd_ =  PreferencesUtil.getStringValue(PreferConfigs.passwd);
            final String name_ =  PreferencesUtil.getStringValue(PreferConfigs.phone);


            Logger.d("name_ :" + name_ + "  passwd_ :" + passwd_);
            HttpMethods.getInstance().login(new MySubscriber<LoginUserInfoEntity>(LoginActivity.this) {

                @Override
                public void onHttpCompleted(HttpResult<LoginUserInfoEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        loginHanlder(httpResult);
                    } else {
                        showToast(httpResult.getMsg());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }

            }, name_, passwd_, getuicid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(PreferencesUtil.getStringValue(PreferConfigs.phone))) {
            nameEt.setText(PreferencesUtil.getStringValue(PreferConfigs.phone));
        }
        if (PreferencesUtil.getBooleanValue(PreferConfigs.isRememberLogin,true) && !TextUtils.isEmpty(PreferencesUtil.getStringValue(PreferConfigs.passwd))) {
            passwdEt.setText(PreferencesUtil.getStringValue(PreferConfigs.passwd));
        }
    }

    @OnClick({R.id.login_btn, R.id.register_tv, R.id.forget_tv,R.id.test_login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.register_tv:
                toActivity(RegisterActivity.class);
                break;
            case R.id.forget_tv:
                toActivity(ForgetPasswdActivity.class);
                break;
            case R.id.test_login_btn:
                login_test();
                break;
        }
    }

    private void login_test() {

//        final String name_ = "0973739012";
//        final String passwd_ = "0973739012";

//        final String name_ = "0953763813";
//        final String passwd_ = "0953763813";

//        final String name_ = "0938623938";
//        final String passwd_ = "0938623938";

        final String name_ = "18520398487";
        final String passwd_ = "123456";


        if (TextUtils.isEmpty(name_) || TextUtils.isEmpty(passwd_)) {
            showToast("請輸入賬號或密碼!");
            return;
        }

        PreferencesUtil.putStringValue(PreferConfigs.passwd, passwd_);
        PreferencesUtil.putStringValue(PreferConfigs.phone, name_);


        Logger.d("name_ :" + name_ + "  passwd_ :" + passwd_);
        HttpMethods.getInstance().login(new MySubscriber<LoginUserInfoEntity>(LoginActivity.this) {

            @Override
            public void onHttpCompleted(HttpResult<LoginUserInfoEntity> httpResult) {
                if (httpResult.isStatus()) {
                    loginHanlder(httpResult);
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }

        }, name_, passwd_, getuicid);
    }

    private void login() {
        final String name_ = nameEt.getText().toString().trim();
        final String passwd_ = passwdEt.getText().toString().trim();

//        final String name_ = "0973739012";
//        final String passwd_ = "0973739012";

//        final String name_ = "0953763813";
//        final String passwd_ = "0953763813";
//
//        final String name_ = "0938623938";
//        final String passwd_ = "0938623938";

        if (TextUtils.isEmpty(name_) || TextUtils.isEmpty(passwd_)) {
            showToast("請輸入賬號或密碼!");
            return;
        }

        PreferencesUtil.putStringValue(PreferConfigs.passwd, passwd_);
        PreferencesUtil.putStringValue(PreferConfigs.phone, name_);


        Logger.d("name_ :" + name_ + "  passwd_ :" + passwd_);
        HttpMethods.getInstance().login(new MySubscriber<LoginUserInfoEntity>(LoginActivity.this) {

            @Override
            public void onHttpCompleted(HttpResult<LoginUserInfoEntity> httpResult) {
                if (httpResult.isStatus()) {
                   loginHanlder(httpResult);
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }

        }, name_, passwd_, getuicid);
    }

    private void loginHanlder(HttpResult<LoginUserInfoEntity> httpResult) {
        UserInfoEntity userInfoEntity=httpResult.getInfo().getUser();
        PreferencesUtil.putStringValue(PreferConfigs.uKey, httpResult.getInfo().getUkey());
        PreferencesUtil.putStringValue(PreferConfigs.rongcloud_token, userInfoEntity.getRongcloud_token());
        PreferencesUtil.putBooleanValue(PreferConfigs.isKeFu, userInfoEntity.getIs_kefu() > 0);
        PreferencesUtil.putBooleanValue(PreferConfigs.isLogin, true);
//        UserInfo userInfo=new UserInfo(
//                userInfoEntity.getUser_id(),
//                userInfoEntity.getUser_name(),
//                Uri.parse(MyApiService.IMG_BASE_URL+userInfoEntity.getPic()));
//        RongIM.getInstance().refreshUserInfoCache(userInfo);
        toActivityFinish(MainActivity.class);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
