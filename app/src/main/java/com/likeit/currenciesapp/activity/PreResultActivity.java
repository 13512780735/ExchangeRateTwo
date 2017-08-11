package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.OrderBeforetInfoEntity;
import com.likeit.currenciesapp.entity.UserBankEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreResultActivity extends RateInfoBaseActivity {


    @BindView(R.id.btn_radio_group)
    RadioGroup btnRadioGroup;
    @BindView(R.id.input_bank_account_et)
    EditText inputBankAccountEt;
    @BindView(R.id.input_bank_passwd_et)
    EditText inputBankPasswdEt;
    @BindView(R.id.input_bank_name_et)
    EditText inputBankNameEt;
    @BindView(R.id.bank_type_tv)
    TextView bankTypeTv;
    @BindView(R.id.bank_account_tv)
    TextView bankAccountTv;
    @BindView(R.id.bank_name_tv)
    TextView bankNameTv;
    @BindView(R.id.btn_alipay)
    RadioButton btnAlipay;
    @BindView(R.id.btn_taobao)
    RadioButton btnTaobao;
    @BindView(R.id.btn_wechat)
    RadioButton btnWechat;

    private String pty="1";
    private String dt1="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_result);
        ButterKnife.bind(this);
        initTopBar("預購");
        initTopCommInfo();

        dt1=getIntent().getExtras().getString(RateInputActivity.PRE_INFO_ID,"");

        if(modifyOrder){

            Logger.d("modifyOrder...");
            bankTypeTv.append(hbank_2+hbank_6);
            bankNameTv.append(hbank_3);
            bankAccountTv.append(hbank_4);

            pty=order_pty;
            if (TextUtils.equals(order_pty, "1")) {
                btnAlipay.setChecked(true);
            } else if (TextUtils.equals(order_pty, "2")) {
                btnTaobao.setChecked(true);
            } else if (TextUtils.equals(order_pty, "3")) {
                btnWechat.setChecked(true);
            }
        }else{
            HttpMethods.getInstance().order_before(new MySubscriber<OrderBeforetInfoEntity>(this) {
                @Override
                public void onHttpCompleted(HttpResult<OrderBeforetInfoEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        orderBeforetInfoEntity = httpResult.getInfo();
                        orderBeforeId=orderBeforetInfoEntity.getId();
                        bankTypeTv.append(orderBeforetInfoEntity.getBankname() + orderBeforetInfoEntity.getZhname());
                        bankNameTv.append(orderBeforetInfoEntity.getHuming());
                        bankAccountTv.append(orderBeforetInfoEntity.getIdcard());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }
            }, uKey, nowCoinId);
        }

        btnRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                inputBankPasswdEt.setText("");
                inputBankAccountEt.setText("");
                inputBankNameEt.setText("");
                switch (checkedId){
                    case R.id.btn_alipay:
                        pty="1";
                        inputBankPasswdEt.setHint("請輸入支付寶密碼,不提供密碼請輸入0");
                        inputBankAccountEt.setHint("請輸入支付寶帳號");
                        break;
                    case R.id.btn_taobao:
                        pty="2";
                        inputBankPasswdEt.setHint("請輸入淘寶密碼,不提供密碼請輸入0");
                        inputBankAccountEt.setHint("請輸入淘寶帳號");
                        break;
                    case R.id.btn_wechat:
                        pty="3";
                        inputBankPasswdEt.setHint("請輸入微信錢包密碼,不提供密碼請輸入0");
                        inputBankAccountEt.setHint("請輸入微信ID");
                        break;
                }
                initPayInfo();
            }
        });
        initPayInfo();

    }

    private void initTopCommInfo() {
        tx11.setText("幣別:" + coin_Name);
        tx12.setText("支付幣別:台幣");
        tx13.setText("類型:預購");

        tx21.setText("金額:" + inputValue);
        tx22.setText("匯率:" + nowRate);
        tx23.setText("手續費:" + hand_);

        tx31.setText("實付新台幣金額:" + realGet + "   " + formula);
    }

    @OnClick({R.id.go_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_btn:
                final String bankAccount = inputBankAccountEt.getText().toString().trim();
                final String bankName = inputBankNameEt.getText().toString().trim();
                final String bz = inputPsEt.getText().toString().trim();
                final String passwd = inputBankPasswdEt.getText().toString().trim();


                if (TextUtils.isEmpty(passwd) || TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName)
                        ) {
                    showToast("請填寫完整信息!");
                    return;
                }

                TipsDialog tipsDialog = new TipsDialog(context);
                tipsDialog.setLeftButt("確定");
                tipsDialog.setRightButt("取消");
                tipsDialog.setTips("請再次確認銀行或支付寶正確與否");
                tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        if(modifyOrder){
                            HttpMethods.getInstance().modify_order(myRateInfoSubscriber, uKey, orderId, bankAccount, passwd, bankName, "", "", "", "", "", pty, bz);
                        }else{
                            HttpMethods.getInstance().do_order(myRateInfoSubscriber, uKey, "5", nowCoinId, "1", inputValue, "",
                                    bz, orderBeforeId, bankAccount, passwd, bankName, "", "", "", "", "",pty,dt1);
                        }
                    }

                    @Override
                    public void onRightClick() {

                    }
                });

                tipsDialog.show();
                break;
        }
    }


    private void initPayInfo() {
        if(modifyOrder){
            /**
             "bank1": "充值测试支付宝账号",
             "bank2": "密码",
             "bank3": "名字",
             */
            if(order_pty.equals(pty)){
                inputBankAccountEt.setText(bank_1);
                inputBankPasswdEt.setText(bank_2);
                inputBankNameEt.setText(bank_3);
            }

        }else{
        HttpMethods.getInstance().getUserBank(new MySubscriber<UserBankEntity>(this) {

            @Override
            public void onHttpCompleted(HttpResult<UserBankEntity> httpResult) {
                if (httpResult.isStatus()) {
                    if (!TextUtils.isEmpty(httpResult.getInfo().getPay_idcard())) {
                        inputBankAccountEt.setText(httpResult.getInfo().getPay_idcard());
                    }
                    if (!TextUtils.isEmpty(httpResult.getInfo().getPay_name())) {
                        inputBankNameEt.setText(httpResult.getInfo().getPay_name());
                    }

                    if (!TextUtils.isEmpty(httpResult.getInfo().getPay_password())) {
                        inputBankPasswdEt.setText(httpResult.getInfo().getPay_password());
                    }

                }
            }

            @Override
            public void onHttpError(Throwable e) {
            }
        }, uKey, nowCoinId, pty,"5");
        }
    }
}
