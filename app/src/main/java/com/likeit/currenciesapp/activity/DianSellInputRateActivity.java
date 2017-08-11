package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.CoinTypes;
import com.likeit.currenciesapp.configs.OperateTypes;
import com.likeit.currenciesapp.entity.DianHLEntity;
import com.likeit.currenciesapp.entity.DianInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.ArithTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DianSellInputRateActivity extends BaseActivity {

    @BindView(R.id.now_rate_tv)
    TextView nowRateTv;
    @BindView(R.id.hand_money_tv)
    TextView handMoneyTv;
    @BindView(R.id.year_rate_tv)
    TextView yearRateTv;
    @BindView(R.id.bli_tv)
    TextView bliTv;
    @BindView(R.id.input_value_et)
    EditText inputValueEt;
    @BindView(R.id.get_tv)
    TextView getTv;
    @BindView(R.id.real_get_tv)
    TextView realGetTv;
    @BindView(R.id.note_tv)
    TextView noteTv;


    private DianInfoEntity dianInfoEntity;
    private RateInfoEntity rateInfoEntity;
    private double rmb_buy_rate = 0;
    private double nowBl=0;
    private double dianhl = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getExtras() == null) {
            toFinish();
            return;
        }
        dianInfoEntity = (DianInfoEntity) getIntent().getExtras().getSerializable("Dian");
        setContentView(R.layout.activity_dian_sell_input_rate);
        ButterKnife.bind(this);
        initTopBar("點數計息");
        initInfo();
    }




    private void initInfo() {
        nowRateTv.setText(rmb_buy_rate + "");
        yearRateTv.setText(dianInfoEntity.getNian());
        inputValueEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (s.length() == 0) {
                        realGetTv.setText("");
                        getTv.setText("");
                        return;
                    }

                    String temp = s.toString();
                    int posDot = temp.indexOf(".");
                    if (posDot > 0){
                        if (temp.length() - posDot - 1 > 2) {
                            s.delete(posDot + 3, posDot + 4);
                        }
                    }

                    Double inputNum = Double.valueOf(s.toString().trim());
                    initSellValue(inputNum);
                } catch (Exception e) {
                    showToast("請輸入合法數字");
                    initSellValue(0d);
                    s.clear();
                }
            }
        });

        HttpMethods.getInstance().getDianHuiLv(new MySubscriber<DianHLEntity>(this) {

            @Override
            public void onHttpCompleted(HttpResult<DianHLEntity> httpResult) {
                if (httpResult.isStatus()) {
                    try {
                        dianhl = Double.valueOf(httpResult.getInfo().getLv());
                        rmb_buy_rate = httpResult.getInfo().getBuy_huilv();
                        nowRateTv.setText(String.valueOf(rmb_buy_rate));
                    } catch (Exception e) {
                        dianhl = 0;
                    }
                    bliTv.setText("1:" + httpResult.getInfo().getLv());
                } else {
                    bliTv.setText("未知");
                }
            }

            @Override
            public void onHttpError(Throwable e) {
                bliTv.setText("未知");
            }
        }, uKey);

        HttpMethods.getInstance().rateInfo(new MySubscriber<RateInfoEntity>(this) {

            @Override
            public void onHttpCompleted(HttpResult<RateInfoEntity> rateInfoEntityHttpResult) {
                if (rateInfoEntityHttpResult.isStatus()) {
                    rateInfoEntity = rateInfoEntityHttpResult.getInfo();
                    noteTv.setText(getBuySellNotic());
                } else {
                    showToast(rateInfoEntityHttpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        }, uKey, "2");
    }

    private String getBuySellNotic() {
        StringBuilder notic = new StringBuilder();
        notic.append("注:人民幣匯款不滿");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元,");
        notic.append("需加收比例");
        notic.append(rateInfoEntity.getSxf().getBl());
        notic.append("%手續費上限");
        notic.append(rateInfoEntity.getSxf().getMax());
        notic.append("元,交易");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元以上不收手續費");

        nowBl=ArithTool.div(rateInfoEntity.getSxf().getBl(),100d);
        return notic.toString();
    }

    private long result_get_value = 0;


    long tmp_hand_money_Vale=0;
    private void initSellValue(Double inputNum) {
        if (ArithTool.sub(inputNum, rateInfoEntity.getSxf().getNomore()) >=0) {
            tmp_hand_money_Vale=0;
            handMoneyTv.setText("0");
            double tmp_a= ArithTool.mul(inputNum, dianhl);
            long tmp_b= (long) tmp_a;
            result_get_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//            result_get_value= (long) ArithTool.mul(inputNum, dianhl);
            realGetTv.setText(inputNum + "*" + dianhl + "=" + result_get_value);
        } else {
            tmp_hand_money_Vale = (long) ArithTool.mul(inputNum, nowBl);
            if (ArithTool.sub(String.valueOf(tmp_hand_money_Vale), rateInfoEntity.getSxf().getMax()) >= 0) {
                tmp_hand_money_Vale = Long.valueOf(rateInfoEntity.getSxf().getMax());
            }

            handMoneyTv.setText(tmp_hand_money_Vale+"");

            double tmp_a= ArithTool.mul(ArithTool.sub(inputNum,tmp_hand_money_Vale),dianhl);
            long tmp_b= (long) tmp_a;
            result_get_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//            result_get_value= (long) ArithTool.mul(ArithTool.sub(inputNum,tmp_hand_money_Vale),dianhl);
            realGetTv.setText(("(" + inputNum + "-" + tmp_hand_money_Vale + ")" + "*" + dianhl + "=" + result_get_value));

        }

        getTv.setText(String.valueOf(result_get_value));

    }






    @OnClick(R.id.go_btn)
    public void onClick() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(RateInputActivity.COIN_TYPE, CoinTypes.RMB);
        bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.ALIPAY);
        RateInfoEntity rateInfoEntity = new RateInfoEntity();
        rateInfoEntity.setBuy(rmb_buy_rate);
        bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);

        bundle.putDouble(RateInputActivity.NOW_Bl, dianhl);
        bundle.putDouble(RateInputActivity.NOW_RATE, rmb_buy_rate);
        bundle.putLong(RateInputActivity.REAL_GET, result_get_value);
        bundle.putLong(RateInputActivity.HAND, 0);

        bundle.putString(RateInputActivity.FORMULA, realGetTv.getText().toString().trim());
        bundle.putString(RateInputActivity.INPUT_VALUE, inputValueEt.getText().toString().trim());
        bundle.putString(RateInputActivity.COIN_NAME, "人民幣");
        bundle.putString(RateInputActivity.COIN_ID, "2");
        toActivity(DianSellResultActivity.class, bundle);
        toFinish();
    }

}
