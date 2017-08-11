package com.likeit.currenciesapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.entity.HomeInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.listeners.RateShowClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeRateDialog extends Dialog {
    @BindView(R.id.rate_img_)
    ImageView rateImg;
    @BindView(R.id.rate_name_tv)
    TextView rateNameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.sale_rate_tv)
    TextView saleRateTv;
    @BindView(R.id.sale_layout)
    LinearLayout saleLayout;
    @BindView(R.id.buy_rate_tv)
    TextView buyRateTv;
    @BindView(R.id.buy_layout)
    LinearLayout buyLayout;
    @BindView(R.id.aliapy_rate_tv)
    TextView aliapyRateTv;
    @BindView(R.id.alipay_layout)
    LinearLayout alipayLayout;
    @BindView(R.id.other_rate_tv)
    TextView otherRateTv;
    @BindView(R.id.other_pay_layout)
    LinearLayout otherPayLayout;
    @BindView(R.id.cancle_tv)
    TextView cancleTv;
    @BindView(R.id.pre_rate_tv)
    TextView preRateTv;
    @BindView(R.id.pre_rate_layout)
    LinearLayout preRateLayout;


    private HomeInfoEntity.BarrayBean barrayBean;
    private RateInfoEntity rateInfoEntity;
    private RateShowClickListener rateShowClickListener;
    private final static int TIME = 101;
    private int time_tatol = 180;
    Handler myHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    if (HomeRateDialog.this.isShowing()) {
                        time_tatol--;
                        timeTv.setText(time_tatol + "秒后刷新");
                        if (time_tatol <= 0) {
                            time_tatol = 180;
                            rateShowClickListener.refreshTime();
                        }
                        myHanlder.sendEmptyMessageDelayed(TIME, 1000);
                    }
                    break;
            }
        }
    };

    public HomeRateDialog(@NonNull Context context, boolean isRMB, HomeInfoEntity.BarrayBean barrayBean, RateInfoEntity rateInfoEntity) {
        super(context, R.style.dialogStyle);
        this.barrayBean = barrayBean;
        this.rateInfoEntity = rateInfoEntity;
        setCancelable(false);
        initView(isRMB);
    }

    public void setRateShowClickListener(RateShowClickListener rateShowClickListener) {
        this.rateShowClickListener = rateShowClickListener;
    }

    private void initView(boolean isRMB) {
        setContentView(R.layout.dialog_home_rate);
        ButterKnife.bind(this);
        if (isRMB) {
            alipayLayout.setVisibility(View.VISIBLE);
            otherPayLayout.setVisibility(View.VISIBLE);
            preRateLayout.setVisibility(View.VISIBLE);
        }

        initRateTv(rateInfoEntity);
        switch (barrayBean.getId()) {
            case "2"://人民幣
                rateImg.setImageResource(R.mipmap.rmb3);
                break;
            case "3"://美元
                rateImg.setImageResource(R.mipmap.us3);
                break;
            case "4"://泰銖
                rateImg.setImageResource(R.mipmap.baht3);
                break;
            case "5"://日元
                rateImg.setImageResource(R.mipmap.yen3);
                break;
            case "6"://韓幣
                rateImg.setImageResource(R.mipmap.korean3);
                break;
            case "7"://港幣
                rateImg.setImageResource(R.mipmap.hk3);
                break;
        }

    }

    public void startTime() {
        myHanlder.sendEmptyMessageDelayed(TIME, 1000);
    }

    public void initRateTv(RateInfoEntity rateInfoEntity) {
        rateNameTv.setText(barrayBean.getName());
        saleRateTv.setText(rateInfoEntity.getSold() + "");
        buyRateTv.setText(rateInfoEntity.getBuy() + "");
        aliapyRateTv.setText(rateInfoEntity.getChongzhi() + "");
        otherRateTv.setText(rateInfoEntity.getDaifu() + "");
        preRateTv.setText(rateInfoEntity.getYugou()+"");
    }


    @OnClick({R.id.sale_layout, R.id.buy_layout, R.id.alipay_layout, R.id.other_pay_layout, R.id.cancle_tv,
            R.id.pre_rate_layout})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.sale_layout:
                rateShowClickListener.sell();
                break;
            case R.id.buy_layout:
                rateShowClickListener.buy();
                break;
            case R.id.alipay_layout:
                rateShowClickListener.alipay();
                break;
            case R.id.other_pay_layout:
                rateShowClickListener.otherPay();
                break;
            case R.id.pre_rate_layout:
                rateShowClickListener.pre();
                break;
            case R.id.cancle_tv:
                break;
        }
    }


}
