package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.OrderBeforetInfoEntity;
import com.likeit.currenciesapp.entity.UserBankEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellResultActivity extends RateInfoBaseActivity {
    @BindView(R.id.input_bank_type_et)
    EditText inputBankTypeEt;
    @BindView(R.id.input_bank_dai_et)
    EditText inputBankDaiEt;
    @BindView(R.id.input_bank_account_et)
    EditText inputBankAccountEt;
    @BindView(R.id.input_bank_name_et)
    EditText inputBankNameEt;
    @BindView(R.id.bank_type_tv)
    TextView bankTypeTv;
    @BindView(R.id.bank_account_tv)
    TextView bankAccountTv;
    @BindView(R.id.bank_name_tv)
    TextView bankNameTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_result);
        ButterKnife.bind(this);
        initTopBar("賣出貨幣");
        initTopCommInfo();


        /**
         "hbank2": "013國泰世華",//匯入公司台灣銀行行别
         "hbank3": "010506228901",//匯入公司台灣銀行帳號
         "hbank4": "蔡亞辰",//匯入公司台灣銀行戶名
         */
        if (modifyOrder) {
            Logger.d("modifyOrder...");
            bankTypeTv.append(hbank_2+hbank_6);
            bankNameTv.append(hbank_3);
            bankAccountTv.append(hbank_4);


            inputBankTypeEt.setText(bank_2);
            inputBankDaiEt.setText(bank_1);
            inputBankAccountEt.setText(bank_4);
            inputBankNameEt.setText(bank_3);
        } else {
            HttpMethods.getInstance().order_before(new MySubscriber<OrderBeforetInfoEntity>(this) {
                @Override
                public void onHttpCompleted(HttpResult<OrderBeforetInfoEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        orderBeforetInfoEntity = httpResult.getInfo();
                        orderBeforeId = orderBeforetInfoEntity.getId();
                        bankTypeTv.append(orderBeforetInfoEntity.getBankname() + orderBeforetInfoEntity.getZhname());
                        bankNameTv.append(orderBeforetInfoEntity.getHuming());
                        bankAccountTv.append(orderBeforetInfoEntity.getIdcard());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }
            }, uKey, nowCoinId, "sold");


            HttpMethods.getInstance().getUserBank(new MySubscriber<UserBankEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<UserBankEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        if (!TextUtils.isEmpty(httpResult.getInfo().getBankname())) {
                            inputBankTypeEt.setText(httpResult.getInfo().getBankname());
                        }
                        if (!TextUtils.isEmpty(httpResult.getInfo().getYhdh())) {
                            inputBankDaiEt.setText(httpResult.getInfo().getYhdh());
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
            }, uKey, nowCoinId, "", "1");
        }

    }


    private void initTopCommInfo() {
        tx11.setText("賣出幣別:" + coin_Name);
        tx12.setText("匯入幣別:台幣");
        tx13.setText("類型:賣出");

        tx21.setText("金額:" + inputValue);
        tx22.setText("匯率:" + nowRate);
        tx23.setText("手續費:" + hand_);

        tx31.setText("實得新台幣金額:" + realGet + "   " + formula);
    }

    @OnClick({R.id.go_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_btn:
                final String bankType = inputBankTypeEt.getText().toString().trim();
                final String bankAccount = inputBankAccountEt.getText().toString().trim();
                final String bankName = inputBankNameEt.getText().toString().trim();
                final String bz = inputPsEt.getText().toString().trim();
                final String bankDai = inputBankDaiEt.getText().toString().trim();

                if (TextUtils.isEmpty(bankType) || TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName)) {
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
                        if (modifyOrder) {
                            HttpMethods.getInstance().modify_order(myRateInfoSubscriber, uKey, orderId, bankDai, bankType, bankName, bankAccount, "", "", "", "", "", bz);
                        } else {
                            HttpMethods.getInstance().do_order(myRateInfoSubscriber, uKey, "1", nowCoinId, "1", inputValue, "", bz, orderBeforeId, bankDai, bankType, bankName, bankAccount, "", "", "", "");
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
}
