package com.likeit.currenciesapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.activity.AlipayResultActivity;
import com.likeit.currenciesapp.activity.BuyResultActivity;
import com.likeit.currenciesapp.activity.DianBuyResultActivity;
import com.likeit.currenciesapp.activity.DianSellResultActivity;
import com.likeit.currenciesapp.activity.OhterResultActivity;
import com.likeit.currenciesapp.activity.OrderListActivity;
import com.likeit.currenciesapp.activity.PreResultActivity;
import com.likeit.currenciesapp.activity.RateInputActivity;
import com.likeit.currenciesapp.activity.SellResultActivity;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.dialogs.OrderNoticDialog;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.OrderDetailInfoEntity;
import com.likeit.currenciesapp.entity.OrderInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.rxbus.RefreshEvent;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.PreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OrderViewHolder extends BaseViewHolder<OrderInfoEntity> {

    @BindView(R.id.coin_img_tv)
    ImageView coinImgTv;
    @BindView(R.id.coin_name_tv)
    TextView coinNameTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.rate_info_tv)
    TextView rateInfoTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.hand_money_tv)
    TextView handMoneyTv;
    @BindView(R.id.result_money_tv)
    TextView resultMoneyTv;
    @BindView(R.id.notice_hui_tv)
    TextView noticeHuiTv;
    @BindView(R.id.modify_tv)
    TextView modifyTv;
    @BindView(R.id.del_tv)
    TextView delTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.yugou_layout)
    LinearLayout yugouLayout;
    @BindView(R.id.days_tv)
    TextView daysTV;
    @BindView(R.id.date_tv)
    TextView dateTv;

    OrderListActivity baseActivity;
    private String alipayGive = "0";

    public void setBaseActivity(OrderListActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBind(final OrderInfoEntity orderInfoEntity, int position) {
        initStatus(orderInfoEntity.getZt());
        initCoinType(orderInfoEntity.getHbinfo().getId());
        if (TextUtils.equals("1", orderInfoEntity.getHkzt())) {
            noticeHuiTv.setText("已 經\n通 知");
            noticeHuiTv.setBackgroundResource(R.drawable.shape_round_order_unable_item_butt);
            noticeHuiTv.setEnabled(false);
            isCanDel = false;
        } else {
            noticeHuiTv.setText("通 知\n匯 款");
            noticeHuiTv.setBackgroundResource(R.drawable.shape_round_order_item_butt);
            noticeHuiTv.setEnabled(true);
        }

        alipayGive=orderInfoEntity.getZs_money();
        timeTv.setText(orderInfoEntity.getAddtime());
        rateInfoTv.setText("交易匯率(" + orderInfoEntity.getHv() + ")");
        handMoneyTv.setText("手續費:" + orderInfoEntity.getSmoney());
        if (TextUtils.equals("3", orderInfoEntity.getTy()) &&
                !TextUtils.isEmpty(alipayGive) && !TextUtils.equals("0", alipayGive)) {
            handMoneyTv.setText("手續費:" + orderInfoEntity.getSmoney() + "     赠送:" + alipayGive);
        }

        resultMoneyTv.setText("交易金額(NT):" + orderInfoEntity.getHmoney());
        moneyTv.setText(orderInfoEntity.getMoney());

        coinNameTv.setText(orderInfoEntity.getHbinfo().getName() + getOperType(orderInfoEntity.getTy()));

        noticeHuiTv.setTag(R.id.kk_order_id, orderInfoEntity.getId());
        modifyTv.setTag(R.id.kk_order_id, orderInfoEntity.getId());
        statusTv.setTag(R.id.kk_order_id, orderInfoEntity.getId());

        daysTV.setText(orderInfoEntity.getZyq_day());
        dateTv.setText(orderInfoEntity.getZyq_end_time());

        delTv.setTag(R.id.kk_order_id, orderInfoEntity.getId());
        if (!isCanDel) {
            delTv.setVisibility(View.INVISIBLE);
        } else {
            delTv.setVisibility(View.VISIBLE);
        }

        delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TipsDialog tipsDialog = new TipsDialog(context);
                tipsDialog.setTips("確定要刪除此項訂單嗎？");
                tipsDialog.setLeftButt("取消");
                tipsDialog.setRightButt("確定");
                tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
                    @Override
                    public void onRightClick() {
                        HttpMethods.getInstance().delOrder(new MySubscriber<EmptyEntity>(baseActivity) {

                            @Override
                            public void onHttpCompleted(HttpResult<EmptyEntity> httpResult) {
                                if (httpResult.isStatus()) {
                                    showToast("刪除成功");
                                    baseActivity.page = 1;
                                    baseActivity.refreshItems();
                                } else {
                                    showToast(httpResult.getMsg());
                                }
                            }

                            @Override
                            public void onHttpError(Throwable e) {

                            }
                        }, PreferencesUtil.getStringValue(PreferConfigs.uKey), (String) v.getTag(R.id.kk_order_id));
                    }

                    @Override
                    public void onLeftClick() {

                    }
                });
                tipsDialog.show();
            }
        });
    }


    private String getOperType(String ty) {
        yugouLayout.setVisibility(View.INVISIBLE);
        switch (ty) {
            case "1":
                return " (賣出)";
            case "2":
                return " (買進)";
            case "3":
                return " (充值)";
            case "4":
                return " (代付)";
            case "5":
                yugouLayout.setVisibility(View.VISIBLE);
                return " (預購)";
            case "6":
                return " (購買點數)";
            case "7":
                return " (代購)";
            case "8":
                return " (提領點數)";
        }
        return "";
    }

    private void initCoinType(String coinId) {
        switch (coinId) {
            case "2"://人民幣
                coinImgTv.setImageResource(R.mipmap.rmb3);
                break;
            case "3"://美元
                coinImgTv.setImageResource(R.mipmap.us3);
                break;
            case "4"://泰銖
                coinImgTv.setImageResource(R.mipmap.baht3);
                break;
            case "5"://日元
                coinImgTv.setImageResource(R.mipmap.yen3);
                break;
            case "6"://韓幣
                coinImgTv.setImageResource(R.mipmap.korean3);
                break;
            case "7"://港幣
                coinImgTv.setImageResource(R.mipmap.hk3);
                break;
        }
    }

    private boolean isCanDel = false;

    private void initStatus(String zt) {
        switch (zt) {
            case "0":
                statusTv.setText("受理中");
                isCanDel = true;
                break;
            case "1":
                statusTv.setText("已付款");
                isCanDel = false;
                break;
            case "2":
                statusTv.setText("已受理");
                isCanDel = false;
                break;
            case "3":
                isCanDel = false;
                statusTv.setText("已到期");
                break;
            case "4":
                isCanDel = false;
                statusTv.setText("已完成");
                break;
            case "98":
            case "99":
                isCanDel = true;
                statusTv.setText("已取消");
                break;
        }
    }


    @OnClick({R.id.notice_hui_tv, R.id.modify_tv, R.id.status_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notice_hui_tv:
                noticeHui((String) noticeHuiTv.getTag(R.id.kk_order_id));
                break;
            case R.id.modify_tv:
                modifyOrder((String) noticeHuiTv.getTag(R.id.kk_order_id));
                break;
            case R.id.status_tv:
                break;
        }
    }


    OrderDetailInfoEntity orderDetailInfoEntity;

    private void modifyOrder(String orderId) {
        HttpMethods.getInstance().getOrderDetail(new MySubscriber<OrderDetailInfoEntity>(baseActivity) {

            @Override
            public void onHttpCompleted(HttpResult<OrderDetailInfoEntity> httpResult) {
                if (httpResult.isStatus()) {
                    orderDetailInfoEntity = httpResult.getInfo();
                    Log.d("TAG","hbank2-->"+orderDetailInfoEntity.getHbank2()+"hbank3-->"+orderDetailInfoEntity.getHbank3()+"hbank4-->"+orderDetailInfoEntity.getHbank4());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RateInputActivity.COIN_TYPE, orderDetailInfoEntity.getHb2());
//                    bundle.putSerializable(RateInputActivity.RATE_INFO,orderDetailInfoEntity.getHv());

                    bundle.putDouble(RateInputActivity.NOW_Bl, Double.valueOf(orderDetailInfoEntity.getHv()));
                    bundle.putDouble(RateInputActivity.NOW_RATE, Double.valueOf(orderDetailInfoEntity.getHv()));
                    double d1 = Double.valueOf(orderDetailInfoEntity.getHmoney());
                    bundle.putLong(RateInputActivity.REAL_GET, (long) d1);
                    double d2 = Double.valueOf(orderDetailInfoEntity.getSmoney());
                    bundle.putLong(RateInputActivity.HAND, (long) d2);

                    bundle.putString(RateInputActivity.FORMULA, "");
                    bundle.putString(RateInputActivity.INPUT_VALUE, orderDetailInfoEntity.getMoney());
                    bundle.putString(RateInputActivity.COIN_NAME, initCoinName(orderDetailInfoEntity.getHb2()));
                    bundle.putString(RateInputActivity.COIN_ID, orderDetailInfoEntity.getHb2());
                    bundle.putString(RateInputActivity.PRE_INFO_ID, orderDetailInfoEntity.getId());
                    bundle.putBoolean(RateInputActivity.MODIFY_ORDER, true);
                    bundle.putString(RateInputActivity.ORDER_ID, orderDetailInfoEntity.getId());
                    bundle.putString(RateInputActivity.AlipayGive, alipayGive);
                    bundle.putString(RateInputActivity.ORDER_PTY, orderDetailInfoEntity.getPty());

                    bundle.putString(RateInputActivity.HBANK_1, orderDetailInfoEntity.getHbank1());
                    bundle.putString(RateInputActivity.HBANK_2, orderDetailInfoEntity.getHbank2());
                    bundle.putString(RateInputActivity.HBANK_3, orderDetailInfoEntity.getHbank3());
                    bundle.putString(RateInputActivity.HBANK_4, orderDetailInfoEntity.getHbank4());
                    bundle.putString(RateInputActivity.HBANK_5, orderDetailInfoEntity.getHbank5());
                    bundle.putString(RateInputActivity.HBANK_6, orderDetailInfoEntity.getHbank6());

                    bundle.putString(RateInputActivity.BANK_1, orderDetailInfoEntity.getBank1());
                    bundle.putString(RateInputActivity.BANK_2, orderDetailInfoEntity.getBank2());
                    bundle.putString(RateInputActivity.BANK_3, orderDetailInfoEntity.getBank3());
                    bundle.putString(RateInputActivity.BANK_4, orderDetailInfoEntity.getBank4());
                    bundle.putString(RateInputActivity.BANK_5, orderDetailInfoEntity.getBank5());
                    bundle.putString(RateInputActivity.BANK_6, orderDetailInfoEntity.getBank6());
                    bundle.putString(RateInputActivity.BANK_11, orderDetailInfoEntity.getBank11());
                    bundle.putString(RateInputActivity.BANK_12, orderDetailInfoEntity.getBank12());

                    switch (httpResult.getInfo().getTy()) {
                        case "1"://1，卖出
                            toActivity(SellResultActivity.class, bundle);
                            break;
                        case "2"://2，买进
                            toActivity(BuyResultActivity.class, bundle);
                            break;
                        case "3"://3，充值
                            toActivity(AlipayResultActivity.class, bundle);
                            break;
                        case "4"://4，代付
                            toActivity(OhterResultActivity.class, bundle);
                            break;
                        case "5"://5，预购
                            toActivity(PreResultActivity.class, bundle);
                            break;
                        case "6"://6，购买点数
                            toActivity(DianBuyResultActivity.class, bundle);
                            break;
                        case "7"://7，代购
                            break;
                        case "8"://8，提领点数
                            toActivity(DianSellResultActivity.class, bundle);
                            break;

                    }
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        }, PreferencesUtil.getStringValue(PreferConfigs.uKey), orderId);
    }


    private void noticeHui(final String orderId) {

        OrderNoticDialog noticDialog = new OrderNoticDialog(context);
        noticDialog.setOnClickListener(new OrderNoticDialog.OnClickListener() {
            @Override
            public void onRightClick(String name, String bank, String num) {
                HttpMethods.getInstance().do_huikuan(new MySubscriber<EmptyEntity>(baseActivity) {
                    @Override
                    public void onHttpCompleted(HttpResult<EmptyEntity> httpResult) {
                        if (httpResult.isStatus()) {
                            showToast("发送汇款通知成功");
                            RxBus.get().post(new RefreshEvent(RefreshEvent.GET_ORDER_LIST));
                        } else {
                            showToast(httpResult.getMsg());
                        }
                    }

                    @Override
                    public void onHttpError(Throwable e) {
                    }
                }, PreferencesUtil.getStringValue(PreferConfigs.uKey), orderId, name, bank, num);
            }

            @Override
            public void onLeftClick() {

            }
        });

        noticDialog.show();
    }

    private void toActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private String initCoinName(String coinId) {
        switch (coinId) {
            case "2":
                return "人民幣";
            case "3":
                return "美元";
            case "4":
                return "泰銖";
            case "5":
                return "日元";
            case "6":
                return "韓幣";
            case "7":
                return "港幣";
        }
        return "";
    }

}
