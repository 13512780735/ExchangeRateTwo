package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.FriendAlipayInfoEntity;
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

public class OhterResultActivity extends RateInfoBaseActivity {


    @BindView(R.id.alipay_accout_tv)
    TextView alipayAccoutTv;
    @BindView(R.id.alipay_name_tv)
    TextView alipayNameTv;
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
        setContentView(R.layout.activity_ohter_result);
        ButterKnife.bind(this);
        initTopBar("代付");
        initTopCommInfo();

        if(modifyOrder){
            Logger.d("modifyOrder...");
            bankTypeTv.append(hbank_2+hbank_6);
            bankNameTv.append(hbank_3);
            bankAccountTv.append(hbank_4);

            /**
             "bank11": "代付支付宝账号",
             "bank12": "代付户名",
             */
            inputBankAccountEt.setText(bank_11);
            inputBankNameEt.setText(bank_12);

            /**
             "bank1": "b0931862373b@yahoo.com.tw",
             "bank2": "(支)刘宇城",
             */
            alipayAccoutTv.append(bank_1);
            alipayNameTv.append(bank_2);
        }else{
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
            }, uKey, nowCoinId);

            HttpMethods.getInstance().getUserBank(new MySubscriber<UserBankEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<UserBankEntity> httpResult) {
                    if(httpResult.isStatus()){

                        if(!TextUtils.isEmpty(httpResult.getInfo().getPay_idcard())){
                            inputBankAccountEt.setText(httpResult.getInfo().getPay_idcard());
                        }
                        if(!TextUtils.isEmpty(httpResult.getInfo().getPay_name())){
                            inputBankNameEt.setText(httpResult.getInfo().getPay_name());
                        }
                    }
                }

                @Override
                public void onHttpError(Throwable e) {
                }
            },uKey,nowCoinId,"1","4");


            HttpMethods.getInstance().getFriendAlipayInfo(new MySubscriber<FriendAlipayInfoEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<FriendAlipayInfoEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        alipayAccoutTv.append( httpResult.getInfo().getIdcard());
                        alipayNameTv.append( httpResult.getInfo().getHuming());
                    } else {
                        showToast(httpResult.getMsg());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }
            }, uKey);
        }
    }

    private void initTopCommInfo() {
        tx11.setText("幣別:" + coin_Name);
        tx12.setText("支付幣別:台幣");
        tx13.setText("類型:代付");

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
                if (TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName)) {
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
                            HttpMethods.getInstance().modify_order(myRateInfoSubscriber, uKey, orderId, alipayAccoutTv.getText().toString(),
                                    alipayNameTv.getText().toString(), "", "", "", "", bankAccount, bankName, "", bz);
                        } else {
                            HttpMethods.getInstance().do_order(myRateInfoSubscriber, uKey, "4", nowCoinId, "1", inputValue, "", bz, orderBeforeId, alipayAccoutTv.getText().toString(),
                                    alipayNameTv.getText().toString(), "", "", "", "", bankAccount, bankName);
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
