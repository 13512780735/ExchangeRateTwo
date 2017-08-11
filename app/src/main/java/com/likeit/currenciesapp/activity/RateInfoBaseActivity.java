package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.OperateTypes;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.OrderBeforetInfoEntity;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import butterknife.BindView;

public class RateInfoBaseActivity extends BaseActivity {
    @BindView(R.id.tx1_1)
    TextView tx11;
    @BindView(R.id.tx1_2)
    TextView tx12;
    @BindView(R.id.tx1_3)
    TextView tx13;
    @BindView(R.id.tx2_1)
    TextView tx21;
    @BindView(R.id.tx2_2)
    TextView tx22;
    @BindView(R.id.tx2_3)
    TextView tx23;
    @BindView(R.id.tx3_1)
    TextView tx31;

    @BindView(R.id.input_ps_et)
    EditText inputPsEt;
    @BindView(R.id.go_btn)
    TextView goBtn;

//    protected CoinTypes coin_type = CoinTypes.RMB;
    protected OperateTypes operateType = OperateTypes.SELL;
//    protected RateInfoEntity rateInfoEntity;
    protected double nowRate = 0;
    protected double nowBl = 0;
    long hand_ = 0;
    String coin_Name = "";
    String inputValue="";
    long realGet = 0;
    String formula = "";
    protected String nowCoinId="";
    protected OrderBeforetInfoEntity orderBeforetInfoEntity;
    protected String orderBeforeId="";
    protected boolean modifyOrder=false;
    protected String orderId="0";
    protected String hbank_1, hbank_2, hbank_3, hbank_4, hbank_5, hbank_6;
    protected String bank_1, bank_2, bank_3, bank_4, bank_5, bank_6, bank_11, bank_12;
    protected String order_pty="1";
    protected String alipayGive="0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() == null || getIntent().getExtras() == null) {
            toFinish();
            return;
        }
//        coin_type = (CoinTypes) getIntent().getExtras().getSerializable(RateInputActivity.COIN_TYPE);
        operateType = (OperateTypes) getIntent().getExtras().getSerializable(RateInputActivity.OPERATE_TYPE);
//        rateInfoEntity = (RateInfoEntity) getIntent().getExtras().getSerializable(RateInputActivity.RATE_INFO);
        nowBl = getIntent().getExtras().getDouble(RateInputActivity.NOW_Bl);
        nowRate = getIntent().getExtras().getDouble(RateInputActivity.NOW_RATE);
        coin_Name = getIntent().getExtras().getString(RateInputActivity.COIN_NAME);
        inputValue = getIntent().getExtras().getString(RateInputActivity.INPUT_VALUE);
        formula = getIntent().getExtras().getString(RateInputActivity.FORMULA);
        realGet = getIntent().getExtras().getLong(RateInputActivity.REAL_GET);
        formula = getIntent().getExtras().getString(RateInputActivity.FORMULA);
        hand_ = getIntent().getExtras().getLong(RateInputActivity.HAND);
        nowCoinId= getIntent().getExtras().getString(RateInputActivity.COIN_ID);
        modifyOrder=getIntent().getExtras().getBoolean(RateInputActivity.MODIFY_ORDER,false);
        orderId=getIntent().getExtras().getString(RateInputActivity.ORDER_ID,"0");
        alipayGive=getIntent().getExtras().getString(RateInputActivity.AlipayGive,"0");

        hbank_1 =getIntent().getExtras().getString(RateInputActivity.HBANK_1,"");
        hbank_2 =getIntent().getExtras().getString(RateInputActivity.HBANK_2,"");
        hbank_3 =getIntent().getExtras().getString(RateInputActivity.HBANK_4,"");
        hbank_4 =getIntent().getExtras().getString(RateInputActivity.HBANK_3,"");
        hbank_5 =getIntent().getExtras().getString(RateInputActivity.HBANK_5,"");
        hbank_6 =getIntent().getExtras().getString(RateInputActivity.HBANK_6,"");

        bank_1 =getIntent().getExtras().getString(RateInputActivity.BANK_1,"");
        bank_2 =getIntent().getExtras().getString(RateInputActivity.BANK_2,"");
        bank_3 =getIntent().getExtras().getString(RateInputActivity.BANK_3,"");
        bank_4 =getIntent().getExtras().getString(RateInputActivity.BANK_4,"");
        bank_5 =getIntent().getExtras().getString(RateInputActivity.BANK_5,"");
        bank_6 =getIntent().getExtras().getString(RateInputActivity.BANK_6,"");
        bank_11 =getIntent().getExtras().getString(RateInputActivity.BANK_11,"");
        bank_12 =getIntent().getExtras().getString(RateInputActivity.BANK_12,"");

        order_pty =getIntent().getExtras().getString(RateInputActivity.ORDER_PTY,"");

    }
    MySubscriber myRateInfoSubscriber=new MySubscriber<EmptyEntity>(this) {
        @Override
        public void onHttpCompleted(HttpResult httpResult) {
            if (httpResult.isStatus()) {
                submitTipsShow();
            } else {
                showToast(httpResult.getMsg());
            }
        }

        @Override
        public void onHttpError(Throwable e) {

        }
    };

    protected void submitTipsShow(){
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
    }
}
