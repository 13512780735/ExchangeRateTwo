package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.CoinTypes;
import com.likeit.currenciesapp.configs.OperateTypes;
import com.likeit.currenciesapp.dialogs.PreDayDialog;
import com.likeit.currenciesapp.entity.PreRateInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.ArithTool;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RateInputActivity extends BaseActivity {


    @BindView(R.id.type_operate_name)
    TextView typeOperateName;
    @BindView(R.id.type_operate_value)
    TextView typeOperateValue;
    @BindView(R.id.result_type_name)
    TextView resultTypeName;
    @BindView(R.id.rate_type_name)
    TextView rateTypeName;
    @BindView(R.id.please_input_tv)
    TextView pleaseInputTv;
    @BindView(R.id.input_value_et)
    EditText inputValueEt;
    @BindView(R.id.get_tv)
    TextView getTv;
    @BindView(R.id.real_get_tv)
    TextView realGetTv;
    @BindView(R.id.real_get_name_tv)
    TextView realGetNameTv;
    @BindView(R.id.hand_money_tv)
    TextView handMoneyTv;
    @BindView(R.id.give_money_tv)
    TextView giveMoneyTv;
    @BindView(R.id.note_tv)
    TextView noteTv;
    @BindView(R.id.now_rate_tv)
    TextView nowRateTv;
    @BindView(R.id.go_btn)
    TextView goBtn;
    @BindView(R.id.days_tv)
    TextView daysTv;
    @BindView(R.id.pre_rate_layout)
    LinearLayout preRateLayout;

    public final static String COIN_TYPE = "coin_type";
    public final static String OPERATE_TYPE = "operate_type";
    public final static String COIN_ID = "coin_id";
    public final static String RATE_INFO = "rate_info";
    public final static String NOW_Bl = "nowBl";
    public final static String NOW_RATE = "nowRate";
    public final static String REAL_GET = "real_get";
    public final static String INPUT_VALUE = "inputValue";
    public final static String FORMULA = "formula";
    public final static String COIN_NAME = "coin_Name";
    public final static String HAND = "hand";
    public final static String PRE_INFO_ID = "pre_info_id";
    public final static String MODIFY_ORDER = "modify_order";
    public final static String ORDER_ID = "order_id";
    public final static String AlipayGive = "alipaygive";

    public final static String HBANK_1 = "HBANK_1";
    public final static String HBANK_2 = "HBANK_2";
    public final static String HBANK_3 = "HBANK_3";
    public final static String HBANK_4 = "HBANK_4";
    public final static String HBANK_5 = "HBANK_5";
    public final static String HBANK_6 = "HBANK_6";

    public final static String BANK_1 = "BANK_1";
    public final static String BANK_2 = "BANK_2";
    public final static String BANK_3 = "BANK_3";
    public final static String BANK_4 = "BANK_4";
    public final static String BANK_5 = "BANK_5";
    public final static String BANK_6 = "BANK_6";
    public final static String BANK_11 = "BANK_11";
    public final static String BANK_12 = "BANK_12";
    public final static String ORDER_PTY = "ORDER_PTY";


    private CoinTypes coin_type = CoinTypes.RMB;
    private OperateTypes operateType = OperateTypes.SELL;
    private RateInfoEntity rateInfoEntity;
    private double nowRate = 0;
    private double nowBl = 0;
    private int alipay_mini=0;
    private int alipay_give=0;
    private boolean isGive=false;
    private String nowCoinId;


    @OnClick({R.id.top_bar_back_img, R.id.go_btn,R.id.select_pre_day_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                toFinish();
                break;
            case R.id.select_pre_day_tv:
                preRateInfo();
                break;
            case R.id.go_btn:
                String input_v = inputValueEt.getText().toString().trim();
                if (TextUtils.isEmpty(input_v)) {
                    showToast("请输入金额!");
                    return;
                }


                Bundle bundle = new Bundle();
                bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
                bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.ALIPAY);
                bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);

                bundle.putDouble(RateInputActivity.NOW_Bl, nowBl);
                bundle.putDouble(RateInputActivity.NOW_RATE, nowRate);
                bundle.putLong(RateInputActivity.REAL_GET, result_value);
                bundle.putLong(RateInputActivity.HAND, tmp_hand_money_Vale);

                bundle.putString(RateInputActivity.FORMULA, realGetTv.getText().toString().trim());
                bundle.putString(RateInputActivity.INPUT_VALUE, input_v);
                bundle.putString(RateInputActivity.COIN_NAME, coin_Name);
                bundle.putString(RateInputActivity.COIN_ID, nowCoinId);

                bundle.putBoolean(RateInputActivity.MODIFY_ORDER, false);
                bundle.putString(RateInputActivity.ORDER_ID, "0");
                bundle.putString(RateInputActivity.AlipayGive, String.valueOf(alipay_give));
                Log.d("TAG",bundle.toString());



                switch (operateType) {
                    case BUY:
                        toActivity(BuyResultActivity.class, bundle);
                        break;
                    case SELL:
                        toActivity(SellResultActivity.class, bundle);
                        break;
                    case ALIPAY:
                        toActivity(AlipayResultActivity.class, bundle);
                        break;
                    case OTHER:
                        toActivity(OhterResultActivity.class, bundle);
                        break;
                    case PRE:
                        if(selectPreInfoEntity==null){
                            showToast("請選擇存放天數");
                            return;
                        }

                        if(ArithTool.sub(selectPreInfoEntity.getMinmoney(),String.valueOf(inputNum))>0){
                            showToast("輸入金額不能小於"+selectPreInfoEntity.getMinmoney());
                            return;
                        }
                        bundle.putString(PRE_INFO_ID,selectPreInfoEntity.getId());
                        toActivity(PreResultActivity.class, bundle);
                        break;

                }
                toFinish();


                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_input);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            coin_type = (CoinTypes) getIntent().getExtras().getSerializable(COIN_TYPE);
            operateType = (OperateTypes) getIntent().getExtras().getSerializable(OPERATE_TYPE);
            rateInfoEntity = (RateInfoEntity) getIntent().getExtras().getSerializable(RATE_INFO);
            nowCoinId = getIntent().getExtras().getString(COIN_ID);
            Logger.d("nowCoinId :" + nowCoinId);
            init();
        } else {
            toFinish();
        }
    }

    String coin_Name = "";

    private void init() {
        switch (coin_type) {
            case YEN://日元
                coin_Name = "日元";
                break;
            case US:
                coin_Name = "美元";
                break;
            case HK:
                coin_Name = "港幣";
                break;
            case RMB:
                coin_Name = "人民幣";
                break;
            case KOREAN://韓幣
                coin_Name = "韓幣";
                break;
            case BAHT://泰銖
                coin_Name = "泰銖";
                break;
        }
        typeOperateValue.setText(coin_Name);
        pleaseInputTv.setText("請輸入" + coin_Name + ":");

        switch (operateType) {
            case BUY:
                initTopBar("買進貨幣");
                typeOperateName.setText("買進幣別:");
                resultTypeName.setText("支付幣別:");
                rateTypeName.setText("買進");
                noteTv.setText(getBuySellNotic());
                nowRate = rateInfoEntity.getBuy();
                break;
            case SELL:
                initTopBar("賣出貨幣");
                typeOperateName.setText("賣出幣別:");
                resultTypeName.setText("支付幣別:");
                rateTypeName.setText("賣出");
//                 notic="注:"+coin_Name+"匯款不滿"+rateInfoEntity.getSxf().getNomore();
                noteTv.setText(getBuySellNotic());
                nowRate = rateInfoEntity.getSold();
                break;
            case ALIPAY:
                initTopBar("充值");
                typeOperateName.setText("幣別:");
                resultTypeName.setText("支付幣別:");
                rateTypeName.setText("充值");
                noteTv.setText(getAlipayOhterNotic());
                nowRate = rateInfoEntity.getChongzhi();
                try{
                    alipay_give=Integer.parseInt(rateInfoEntity.getZsds().getZs_money());
                    alipay_mini=Integer.parseInt(rateInfoEntity.getZsds().getMin_money());
                    isGive=TextUtils.equals("1",rateInfoEntity.getZsds().getIs_zs());
                }catch (Exception e){
                    alipay_give=0;
                    alipay_mini=0;
                }
                break;
            case OTHER:
                initTopBar("代付");
                typeOperateName.setText("幣別:");
                resultTypeName.setText("支付幣別:");
                rateTypeName.setText("淘寶代付");
                noteTv.setText(getAlipayOhterNotic());
                nowRate = rateInfoEntity.getDaifu();
                break;
            case PRE:
                preRateLayout.setVisibility(View.VISIBLE);
                initTopBar("預購");
                typeOperateName.setText("買進幣別:");
                resultTypeName.setText("支付幣別:");
                rateTypeName.setText("預購");
                noteTv.setText(getPreNotic());
                nowRate = rateInfoEntity.getYugou();
                break;
        }
        nowRateTv.setText(String.valueOf(nowRate));
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
                        handMoneyTv.setText("");
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
                    if (operateType == OperateTypes.BUY || operateType == OperateTypes.SELL) {
                        initBuySellValue(inputNum);
                    } else if(operateType==OperateTypes.ALIPAY || operateType==OperateTypes.OTHER) {
                        initAlipayOtherValue(inputNum);
                    }else if(operateType== OperateTypes.PRE){
                        initPreValue(inputNum);
                    }

                } catch (Exception e) {
                    showToast("請輸入合法數字");
                    if (operateType == OperateTypes.BUY || operateType == OperateTypes.SELL) {
                        initBuySellValue(0d);
                    } else  if(operateType==OperateTypes.ALIPAY || operateType==OperateTypes.OTHER){
                        initAlipayOtherValue(0d);
                    }else if(operateType== OperateTypes.PRE){
                        initPreValue(0d);
                    }
                    s.clear();
                }
            }
        });

    }

    private PreRateInfoEntity selectPreInfoEntity;
    private void preRateInfo() {
        HttpMethods.getInstance().getPreRateInfo(new MySubscriber<ArrayList<PreRateInfoEntity>>(this){

            @Override
            public void onHttpCompleted(HttpResult<ArrayList<PreRateInfoEntity>> httpResult) {
                if(httpResult.isStatus()){
                    if(httpResult.getInfo()!=null&& httpResult.getInfo().size()>0){
                        PreDayDialog preDayDialog=new PreDayDialog(RateInputActivity.this,httpResult.getInfo());
                        preDayDialog.setPreDaySelectCallBackListener(new PreDayDialog.PreDaySelectCallBackListener() {
                            @Override
                            public void onCancle() {

                            }

                            @Override
                            public void onSubmit(PreRateInfoEntity entity) {
                                selectPreInfoEntity=entity;
                                nowRate=selectPreInfoEntity.getZvs();
                                nowRateTv.setText(String.valueOf(nowRate));
                                daysTv.setText(entity.getDays()+"天(不能小於"+entity.getMinmoney()+")");
                                if(inputNum>0){
                                    initPreValue(inputNum);
                                }
                            }
                        });
                        preDayDialog.show();
                    }
                }else{
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        },uKey);
    }

    private String getBuySellNotic() {
        StringBuilder notic = new StringBuilder();
        notic.append("注:");
        notic.append(coin_Name);
        notic.append("匯款不滿");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元,");
        notic.append("需加收比例");
        notic.append(rateInfoEntity.getSxf().getBl());
        notic.append("%手續費上限");
        notic.append(rateInfoEntity.getSxf().getMax());
        notic.append("元,交易");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元以上不收手續費");

        nowBl = ArithTool.div(rateInfoEntity.getSxf().getBl(), 100d);
        return notic.toString();
    }


    private String getPreNotic() {
        StringBuilder notic = new StringBuilder();
        notic.append("注:");
        notic.append(coin_Name);
        notic.append("匯款不滿");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元,");
        notic.append("需加收比例");
        notic.append(rateInfoEntity.getSxf().getBl());
        notic.append("%手續費上限");
        notic.append(rateInfoEntity.getSxf().getMax());
        notic.append("元,交易");
        notic.append(rateInfoEntity.getSxf().getNomore());
        notic.append("元以上不收手續費");
        notic.append("中遠期到期日必須是交易雙方的工作日，如遇節假日則順延至下一個工作日。 ★預購可以提前解約，不收取任何費用，依下單日匯率結算。");

        nowBl = ArithTool.div(rateInfoEntity.getSxf().getBl(), 100d);
        return notic.toString();
    }

    private String getAlipayOhterNotic() {
        StringBuilder notic = new StringBuilder();
        notic.append("注:");
        notic.append("支付寶充值");
        notic.append(rateInfoEntity.getSxf_alipay().getNomore());
        notic.append("元以下");
        notic.append("需加收");
        notic.append(rateInfoEntity.getSxf_alipay().getMax());
        notic.append("元");
        notic.append(coin_Name);
        notic.append("手續費");
        return notic.toString();
    }

    long result_value = 0;
    private void initAlipayOtherValue(Double inputNum) {
        this.inputNum=inputNum;
        if (ArithTool.sub(inputNum, rateInfoEntity.getSxf_alipay().getNomore()) >= 0) {
            tmp_hand_money_Vale = 0;
            handMoneyTv.setText("0手續費");
            if(isGive && inputNum>=alipay_mini){
                giveMoneyTv.setVisibility(View.VISIBLE);
                double tmp_a=ArithTool.mul(ArithTool.sub(inputNum,alipay_give), nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(ArithTool.sub(inputNum,alipay_give), nowRate);
                giveMoneyTv.setText("赠送:"+alipay_give+"(人民幣)");
                realGetTv.setText(inputNum+"-"+alipay_give + "*" + nowRate + "=" + result_value);

            }else{
                giveMoneyTv.setVisibility(View.GONE);
                double tmp_a=ArithTool.mul(inputNum, nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(inputNum, nowRate);
                realGetTv.setText(inputNum + "*" + nowRate + "=" + result_value);
            }
        } else {
            tmp_hand_money_Vale = Long.valueOf(rateInfoEntity.getSxf_alipay().getMax().trim());
            handMoneyTv.setText(tmp_hand_money_Vale + "(" + coin_Name + ")");
            if(isGive && inputNum>=alipay_mini){
                giveMoneyTv.setVisibility(View.VISIBLE);
                giveMoneyTv.setText("赠送:"+alipay_give+"(人民幣)");
                double tmp_a=ArithTool.mul(ArithTool.add(ArithTool.sub(inputNum,alipay_give), tmp_hand_money_Vale), nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(ArithTool.add(ArithTool.sub(inputNum,alipay_give), tmp_hand_money_Vale), nowRate);
                realGetTv.setText(("(" + inputNum + "+" + tmp_hand_money_Vale + "-"+alipay_give+ ")" + "*" + nowRate + "=" + result_value));
            }else{
                giveMoneyTv.setVisibility(View.GONE);
                double tmp_a=ArithTool.mul(ArithTool.add(inputNum, tmp_hand_money_Vale), nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(ArithTool.add(inputNum, tmp_hand_money_Vale), nowRate);
                realGetTv.setText(("(" + inputNum + "+" + tmp_hand_money_Vale +")" + "*" + nowRate + "=" + result_value));
            }
        }

        getTv.setText(String.valueOf(result_value));
    }

    long tmp_hand_money_Vale = 0;

    private void initBuySellValue(Double inputNum) {
        this.inputNum=inputNum;
        if (ArithTool.sub(inputNum, rateInfoEntity.getSxf().getNomore()) >= 0) {
            tmp_hand_money_Vale = 0;
            handMoneyTv.setText("0手續費");
            double tmp_a= ArithTool.mul(inputNum, nowRate);
            long tmp_b= (long) tmp_a;
            result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//            result_value = (long) ArithTool.mul(inputNum, nowRate);
            realGetTv.setText(inputNum + "*" + nowRate + "=" + result_value);
        } else {
            tmp_hand_money_Vale = (long) ArithTool.mul(inputNum, nowBl);


            if((ArithTool.mul(inputNum, nowBl)-tmp_hand_money_Vale)>0){
                tmp_hand_money_Vale++;
            }

            if (ArithTool.sub(String.valueOf(tmp_hand_money_Vale), rateInfoEntity.getSxf().getMax()) >= 0) {
                tmp_hand_money_Vale = Long.valueOf(rateInfoEntity.getSxf().getMax());
            }

            handMoneyTv.setText(tmp_hand_money_Vale + "(" + coin_Name + ")");

            if (operateType == OperateTypes.BUY || operateType == OperateTypes.PRE) {
                double tmp_a= ArithTool.mul(ArithTool.add(inputNum, tmp_hand_money_Vale), nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(ArithTool.add(inputNum, tmp_hand_money_Vale), nowRate);
                realGetTv.setText(("(" + inputNum + "+" + tmp_hand_money_Vale + ")" + "*" + nowRate + "=" + result_value));
            } else {
                realGetNameTv.setText("實得台幣金額:");
                double tmp_a= ArithTool.mul(ArithTool.sub(inputNum, tmp_hand_money_Vale), nowRate);
                long tmp_b= (long) tmp_a;
                result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//                result_value = (long) ArithTool.mul(ArithTool.sub(inputNum, tmp_hand_money_Vale), nowRate);
                realGetTv.setText(("(" + inputNum + "-" + tmp_hand_money_Vale + ")" + "*" + nowRate + "=" + result_value));
            }
        }

        getTv.setText(String.valueOf(result_value));

    }


    private double inputNum=0d;
    private void initPreValue(Double inputNum) {
        this.inputNum=inputNum;
        handMoneyTv.setText("");
        tmp_hand_money_Vale = 0;
        double tmp_a= ArithTool.mul(inputNum, nowRate);
        long tmp_b= (long) tmp_a;
        result_value= tmp_a==tmp_b?tmp_b: (long) (tmp_a + 1);
//        result_value = (long) ArithTool.mul(inputNum, nowRate);
        realGetTv.setText(inputNum + "*" + nowRate + "=" + result_value);
        getTv.setText(String.valueOf(result_value));
    }
}
