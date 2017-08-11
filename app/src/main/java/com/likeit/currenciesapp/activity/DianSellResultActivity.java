package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.UserBankEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DianSellResultActivity extends RateInfoBaseActivity {

    @BindView(R.id.btn_radio_group)
    RadioGroup btnRadioGroup;

    @BindView(R.id.input_bank_account_et)
    EditText inputBankAccountEt;
    @BindView(R.id.input_bank_name_et)
    EditText inputBankNameEt;
    @BindView(R.id.input_bank_type_et)
    EditText inputBankTypeEt;
    @BindView(R.id.input_bank_city_et)
    EditText inputBankCityEt;
    @BindView(R.id.input_bank_zhi_et)
    EditText inputBankZhiEt;

    @BindView(R.id.item_comm_other_layout)
    LinearLayout itemCommOtherLayout;
    @BindView(R.id.item_comm_buy_layout)
    LinearLayout itemCommBuyLayout;

    @BindView(R.id.input_alipay_account_et)
    EditText inputAlipayAccountEt;
    @BindView(R.id.input_alipay_passwd_et)
    EditText inputAlipayPasswdEt;
    @BindView(R.id.input_alipay_name_et)
    EditText inputAlipayNameEt;
    @BindView(R.id.btn_alipay)
    RadioButton btnAlipay;
    @BindView(R.id.btn_taobao)
    RadioButton btnTaobao;
    @BindView(R.id.btn_wechat)
    RadioButton btnWechat;
    @BindView(R.id.btn_bank)
    RadioButton btnBank;


    private String pty = "0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian_sell_result);
        ButterKnife.bind(this);
        initTopBar("點數計息");
        initTopCommInfo();
        itemCommBuyLayout.setVisibility(View.VISIBLE);
        itemCommOtherLayout.setVisibility(View.GONE);
        LoaddingDismiss();


        if (modifyOrder) {
            pty = order_pty;
            if (TextUtils.equals(order_pty, "0")) {
                btnBank.setChecked(true);
            } else if (TextUtils.equals(order_pty, "1")) {
                btnAlipay.setChecked(true);
            } else if (TextUtils.equals(order_pty, "2")) {
                btnTaobao.setChecked(true);
            } else if (TextUtils.equals(order_pty, "3")) {
                btnWechat.setChecked(true);
            }
        }

        btnRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                inputBankTypeEt.setText("");
                inputBankAccountEt.setText("");
                inputBankNameEt.setText("");
                inputBankTypeEt.setText("");
                inputBankCityEt.setText("");
                inputBankZhiEt.setText("");

                inputAlipayPasswdEt.setText("");
                inputAlipayAccountEt.setText("");
                inputAlipayNameEt.setText("");

                itemCommBuyLayout.setVisibility(View.GONE);
                itemCommOtherLayout.setVisibility(View.VISIBLE);
                switch (checkedId) {

                    case R.id.btn_bank:
                        pty = "0";
                        itemCommBuyLayout.setVisibility(View.VISIBLE);
                        itemCommOtherLayout.setVisibility(View.GONE);
                        initBankInfo();
                        break;
                    case R.id.btn_alipay:
                        pty = "1";
                        inputAlipayPasswdEt.setHint("請輸入支付寶密碼,不提供密碼請輸入0");
                        inputAlipayAccountEt.setHint("請輸入支付寶賬號");
                        initPayInfo();
                        break;
                    case R.id.btn_taobao:
                        pty = "2";
                        inputAlipayPasswdEt.setHint("請輸入淘寶密碼,不提供密碼請輸入0");
                        inputAlipayAccountEt.setHint("請輸入淘寶賬號");
                        initPayInfo();
                        break;
                    case R.id.btn_wechat:
                        pty = "3";
                        inputAlipayPasswdEt.setHint("請輸入微信錢包密碼,不提供密碼請輸入0");
                        inputAlipayAccountEt.setHint("請輸入微信錢包賬號");
                        initPayInfo();
                        break;
                }
            }
        });

        initBankInfo();
    }

    private void initBankInfo() {
        if(modifyOrder){
            /**
             "bank2": "1",
             "bank3": "unmoderated5",
             "bank4": "咯嘛4",
             "bank5": "2",
             "bank6": "同3",
             */
            inputBankTypeEt.setText(bank_2);
            inputBankCityEt.setText(bank_5);
            inputBankZhiEt.setText(bank_6);
            inputBankAccountEt.setText(bank_4);
            inputBankNameEt.setText(bank_3);
        }else{
            HttpMethods.getInstance().getUserBank(new MySubscriber<UserBankEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<UserBankEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        if (!TextUtils.isEmpty(httpResult.getInfo().getBankname())) {
                            inputBankTypeEt.setText(httpResult.getInfo().getBankname());
                        }
                        if (!TextUtils.isEmpty(httpResult.getInfo().getCity())) {
                            inputBankCityEt.setText(httpResult.getInfo().getCity());
                        }
                        if (!TextUtils.isEmpty(httpResult.getInfo().getZhname())) {
                            inputBankZhiEt.setText(httpResult.getInfo().getZhname());
                        }
                        if (!TextUtils.isEmpty(httpResult.getInfo().getIdcard())) {
                            inputBankAccountEt.setText(httpResult.getInfo().getIdcard());
                        }
                        if (!TextUtils.isEmpty(httpResult.getInfo().getHuming())) {
                            inputBankNameEt.setText(httpResult.getInfo().getHuming());
                        }
                    }
                }

                @Override
                public void onHttpError(Throwable e) {
                }
            }, uKey, nowCoinId, "", "8");
        }
    }

    private void initPayInfo() {
        if(modifyOrder){
            /**
             "bank1": "充值测试支付宝账号",
             "bank2": "密码",
             "bank3": "名字",
             */
            if (order_pty.equals(pty)) {
                inputBankAccountEt.setText(bank_1);
                inputAlipayPasswdEt.setText(bank_2);
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
                            inputAlipayPasswdEt.setText(httpResult.getInfo().getPay_password());
                        }

                    }
                }

                @Override
                public void onHttpError(Throwable e) {
                }
            }, uKey, nowCoinId, pty, "8");
        }

    }

    private void initTopCommInfo() {
        tx11.setText("幣別:" + coin_Name);
//        tx12.setText("支付幣別:台幣");
        tx13.setText("類型:點數提領");

        tx21.setText("提領點數:" + inputValue);
        tx22.setText("兌換率:" + "1=" + (long) nowBl);
        tx23.setText("手續費:" + hand_);

        tx31.setText("實得人民幣:" + realGet + "   " + formula);
    }

    @OnClick({R.id.go_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_btn:
                final String bankAccount = inputBankAccountEt.getText().toString().trim();
                final String bankName = inputBankNameEt.getText().toString().trim();
                final String bankType = inputBankTypeEt.getText().toString().trim();
                final String bankCity = inputBankCityEt.getText().toString().trim();
                final String bankZhi = inputBankZhiEt.getText().toString().trim();

                final String alipayAccount = inputAlipayAccountEt.getText().toString().trim();
                final String alipayName = inputAlipayNameEt.getText().toString().trim();
                final String alipayPasswd = inputAlipayPasswdEt.getText().toString().trim();

                final String bz = inputPsEt.getText().toString().trim();


                if (!TextUtils.equals("0", pty)) {
                    if (TextUtils.isEmpty(alipayPasswd) || TextUtils.isEmpty(alipayAccount) || TextUtils.isEmpty(alipayName)) {
                        showToast("請填寫完整信息!");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(bankType) || TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName)
                            || TextUtils.isEmpty(bankCity) || TextUtils.isEmpty(bankZhi)) {
                        showToast("請填寫完整信息!");
                        return;
                    }
                }


                TipsDialog tipsDialog = new TipsDialog(context);
                tipsDialog.setLeftButt("確定");
                tipsDialog.setRightButt("取消");
                tipsDialog.setTips("請再次確認銀行或支付寶正確與否");

                if (TextUtils.equals("0", pty)) {
                    tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            if (modifyOrder) {
                                HttpMethods.getInstance().modify_order(myRateInfoSubscriber, uKey, orderId, "", bankType, bankName, bankAccount, bankCity, bankZhi, "", "", "", bz);
                            } else {
                                HttpMethods.getInstance().do_order(myRateInfoSubscriber, uKey, "8", nowCoinId, "1", inputValue, "",
                                        bz, orderBeforeId, "", bankType, bankName, bankAccount, bankCity, bankZhi, "", "", "");
                            }
                        }

                        @Override
                        public void onRightClick() {
                        }
                    });
                } else {
                    tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                        @Override
                        public void onLeftClick() {
                            HttpMethods.getInstance().do_order(
                                    new MySubscriber<EmptyEntity>(DianSellResultActivity.this) {
                                        @Override
                                        public void onHttpCompleted(HttpResult httpResult) {
                                            if (httpResult.isStatus()) {
//                                    showToast("訂單提交成功!");
                                                TipsDialog tipsDialog = new TipsDialog(context);

                                                tipsDialog.setRightButt("確定");
                                                tipsDialog.sigleButt();
                                                tipsDialog.setTips("提交成功");
                                                tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                                                    @Override
                                                    public void onRightClick() {
                                                        toFinish();
                                                    }

                                                    @Override
                                                    public void onLeftClick() {

                                                    }
                                                });
                                                tipsDialog.show();

                                            } else {
                                                showToast(httpResult.getMsg());
                                            }

                                        }

                                        @Override
                                        public void onHttpError(Throwable e) {

                                        }
                                    }, uKey, "8", nowCoinId, "1", inputValue, "",
                                    bz, orderBeforeId, alipayAccount, alipayPasswd, alipayName, "", "", "", "", "", pty);

                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                }


                tipsDialog.show();
                break;
        }
    }
}
