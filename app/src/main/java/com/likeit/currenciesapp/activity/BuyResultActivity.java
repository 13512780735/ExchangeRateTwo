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
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyResultActivity extends RateInfoBaseActivity {
    @BindView(R.id.input_bank_type_et)
    EditText inputBankTypeEt;
//    @BindView(R.id.input_bank_dai_et)
//    EditText inputBankDaiEt;
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
    @BindView(R.id.input_bank_city_et)
    EditText inputBankCityEt;
    @BindView(R.id.input_bank_zhi_et)
    EditText inputBankZhiEt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_result);
        ButterKnife.bind(this);
        initTopBar("買進貨幣");
        initTopCommInfo();

        if(modifyOrder){
            Logger.d("modifyOrder...");
            bankTypeTv.append(hbank_2+hbank_6);
            bankNameTv.append(hbank_3);
            bankAccountTv.append(hbank_4);


            /**
             "bank2": "买进",
             "bank3": "joyo",
             "bank4": "摸着",
             "bank5": "买进省",
             "bank6": "我",
             */
            inputBankTypeEt.setText(bank_2);
            inputBankCityEt.setText(bank_3);
            inputBankZhiEt.setText(bank_4);
            inputBankAccountEt.setText(bank_5);
            inputBankNameEt.setText(bank_6);

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

            HttpMethods.getInstance().getUserBank(new MySubscriber<UserBankEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<UserBankEntity> httpResult) {
                    if(httpResult.isStatus()){
                        if(!TextUtils.isEmpty(httpResult.getInfo().getBankname())){
                            inputBankTypeEt.setText(httpResult.getInfo().getBankname());
                        }
                        if(!TextUtils.isEmpty(httpResult.getInfo().getCity())){
                            inputBankCityEt.setText(httpResult.getInfo().getCity());
                        }
                        if(!TextUtils.isEmpty(httpResult.getInfo().getZhname())){
                            inputBankZhiEt.setText(httpResult.getInfo().getZhname());
                        }
                        if(!TextUtils.isEmpty(httpResult.getInfo().getIdcard())){
                            inputBankAccountEt.setText(httpResult.getInfo().getIdcard());
                        }
                        if(!TextUtils.isEmpty(httpResult.getInfo().getHuming())){
                            inputBankNameEt.setText(httpResult.getInfo().getHuming());
                        }
                    }
                }

                @Override
                public void onHttpError(Throwable e) {
                }
            },uKey,nowCoinId,"","2");
        }



    }

    private void initTopCommInfo() {
        tx11.setText("買進幣別:" + coin_Name);
        tx12.setText("支付幣別:台幣");
        tx13.setText("類型:買進");

        tx21.setText("金額:" + inputValue);
        tx22.setText("匯率:" + nowRate);
        tx23.setText("手續費:" + hand_);

        tx31.setText("實付新台幣金額:" + realGet + "   " + formula);
    }

    @OnClick({R.id.go_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_btn:
                final String bankType = inputBankTypeEt.getText().toString().trim();
                final String bankAccount = inputBankAccountEt.getText().toString().trim();
                final String bankName = inputBankNameEt.getText().toString().trim();
                final String bz = inputPsEt.getText().toString().trim();
//                final String bankDai = inputBankDaiEt.getText().toString().trim();
                final String zhi=inputBankZhiEt.getText().toString().trim();
                final String city=inputBankCityEt.getText().toString().trim();

                if (TextUtils.isEmpty(bankType) || TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName)
                        ||TextUtils.isEmpty(zhi)||TextUtils.isEmpty(city)) {
                    showToast("請填寫完整信息!");
                    return;
                }

                TipsDialog tipsDialog=new TipsDialog(context);
                tipsDialog.setLeftButt("確定");
                tipsDialog.setRightButt("取消");
                tipsDialog.setTips("請再次確認銀行或支付寶正確與否");
                tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        if(modifyOrder){
                            HttpMethods.getInstance().modify_order(myRateInfoSubscriber,uKey,orderId, "", bankType, bankName, bankAccount, city, zhi,"","","",bz);
                        }else{
                            HttpMethods.getInstance().do_order(myRateInfoSubscriber, uKey, "2", nowCoinId, "1", inputValue, "", bz, orderBeforeId, "", bankType, bankName, bankAccount, city, zhi,"","");
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
