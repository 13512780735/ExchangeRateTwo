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


public class DianBuyInputRateActivity extends BaseActivity {


    @BindView(R.id.type_operate_name)
    TextView typeOperateName;
    @BindView(R.id.type_operate_value)
    TextView typeOperateValue;
    @BindView(R.id.result_type_name)
    TextView resultTypeName;
    @BindView(R.id.now_rate_tv)
    TextView nowRateTv;
    @BindView(R.id.year_rate_tv)
    TextView yearRateTv;
    @BindView(R.id.coin_name_tv)
    TextView coinNameTv;
    @BindView(R.id.bli_tv)
    TextView bliTv;
    @BindView(R.id.input_value_et)
    EditText inputValueEt;
    @BindView(R.id.get_tv)
    TextView getTv;
    @BindView(R.id.real_get_tv)
    TextView realGetTv;
    private DianInfoEntity dianInfoEntity;
    private double rmb_buy_rate = 0;
    private double dianhl = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getExtras() == null) {
            toFinish();
            return;
        }
        dianInfoEntity = (DianInfoEntity) getIntent().getExtras().getSerializable("Dian");
        setContentView(R.layout.activity_dian_buy_input_rate);
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
                    initBuyValue(inputNum);
                } catch (Exception e) {
                    showToast("請輸入合法數字");
                    initBuyValue(0d);
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
                        rmb_buy_rate=httpResult.getInfo().getBuy_huilv();
                        nowRateTv.setText(rmb_buy_rate+"");
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
    }


    private long result_get_value = 0;

    private void initBuyValue(Double inputNum) {
        double tmp_a= ArithTool.mul(ArithTool.mul(inputNum, dianhl), rmb_buy_rate);
        long tmp_b= (long) tmp_a;
        result_get_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//        result_get_value = (long) ArithTool.mul(ArithTool.mul(inputNum, dianhl), rmb_buy_rate);
        realGetTv.setText(inputNum + "*" + dianhl + "*" + rmb_buy_rate + "=" + result_get_value);
        getTv.setText("" + result_get_value);
    }

    @OnClick(R.id.go_btn)
    public void onClick() {

        Bundle bundle=new Bundle();
        bundle.putSerializable(RateInputActivity.COIN_TYPE, CoinTypes.RMB);
        bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.ALIPAY);
        RateInfoEntity rateInfoEntity=new RateInfoEntity();
        rateInfoEntity.setBuy(rmb_buy_rate);
        bundle.putSerializable(RateInputActivity.RATE_INFO,rateInfoEntity);

        bundle.putDouble(RateInputActivity.NOW_Bl,dianhl);
        bundle.putDouble(RateInputActivity.NOW_RATE,rmb_buy_rate);
        bundle.putLong(RateInputActivity.REAL_GET,result_get_value);
        bundle.putLong(RateInputActivity.HAND,0);

        bundle.putString(RateInputActivity.FORMULA,realGetTv.getText().toString().trim());
        bundle.putString(RateInputActivity.INPUT_VALUE,inputValueEt.getText().toString().trim());
        bundle.putString(RateInputActivity.COIN_NAME,"人民幣");
        bundle.putString(RateInputActivity.COIN_ID, "2");
        toActivity(DianBuyResultActivity.class,bundle);
        toFinish();
    }

}
