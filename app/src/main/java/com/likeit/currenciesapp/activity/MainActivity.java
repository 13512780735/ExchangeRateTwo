package com.likeit.currenciesapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.app.MyApplication;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.CoinTypes;
import com.likeit.currenciesapp.configs.OperateTypes;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.dialogs.DianDialog;
import com.likeit.currenciesapp.dialogs.HomeRateDialog;
import com.likeit.currenciesapp.entity.DianInfoEntity;
import com.likeit.currenciesapp.entity.HomeInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.entity.UserInfoByNameEntity;
import com.likeit.currenciesapp.listeners.RateShowClickListener;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.api_service.MyApiService;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.rxbus.CloseAppEvent;
import com.likeit.currenciesapp.rxbus.MessageEvent;
import com.likeit.currenciesapp.rxbus.RefreshEvent;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;
import com.pk4pk.imagecycleviewlib.ADInfo;
import com.pk4pk.imagecycleviewlib.ImageCycleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class MainActivity extends BaseActivity {
    private final static int IMLOGIN = 101;
    private final static int IMLOGIN_SUCCESS = 102;
    private final static int IMLOGIN_FAIL = 103;

    @BindView(R.id.message_iv)
    ImageView messageIv;
    @BindView(R.id.notice_msg_red_tv)
    TextView noticeMsgRedTv;
    @BindView(R.id.order_msg_red_tv)
    TextView orderMsgRedTv;
    @BindView(R.id.service_msg_red_tv)
    TextView serviceMsgRedTv;
    @BindView(R.id.service_tv)
    TextView serviceTv;
    @BindView(R.id.user_iv)
    ImageView userIv;
    @BindView(R.id.dian_l)
    LinearLayout dianL;
    @BindView(R.id.user_l)
    LinearLayout userL;
    @BindView(R.id.service_l)
    LinearLayout serviceL;
    @BindView(R.id.order_search_l)
    LinearLayout orderSearchL;
    @BindView(R.id.us_img)
    ImageView usImg;
    @BindView(R.id.us_layout)
    LinearLayout usLayout;
    @BindView(R.id.yen_img)
    ImageView yenImg;
    @BindView(R.id.service_img)
    ImageView serviceImg;
    @BindView(R.id.yen_layout)
    LinearLayout yenLayout;
    @BindView(R.id.korean_img)
    ImageView koreanImg;
    @BindView(R.id.korean_layout)
    LinearLayout koreanLayout;
    @BindView(R.id.rmb_img)
    ImageView rmbImg;
    @BindView(R.id.rmb_layout)
    LinearLayout rmbLayout;
    @BindView(R.id.baht_img)
    ImageView bahtImg;
    @BindView(R.id.baht_layout)
    LinearLayout bahtLayout;
    @BindView(R.id.hk_img)
    ImageView hkImg;
    @BindView(R.id.hk_layout)
    LinearLayout hkLayout;
    @BindView(R.id.notice_msg_tv)
    TextView noticeMsgTv;
    @BindView(R.id.notice_msg_layout)
    LinearLayout noticeMsgLayout;
    @BindView(R.id.image_cycle_view)
    ImageCycleView imageCycleView;

    private HomeInfoEntity homeInfoEntity;
    private HomeRateDialog homeRateDialog;
    private HomeInfoEntity.BarrayBean barrayBean;
//    private CallReceiver callReceiver;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IMLOGIN://登录IM
                    connectIM();
                    break;
                case IMLOGIN_SUCCESS://登陆成功
                    serviceImg.setImageDrawable(getResources().getDrawable(R.mipmap.service));
                    serviceTv.setText("app客服");
                    break;
//                case IMLOGIN_FAIL://登陆失败
//                    serviceImg.setImageDrawable(getResources().getDrawable(R.mipmap.service_off_line_home));
//                    serviceTv.setText("loading...");
//                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeInfo();
        MyApplication.isNotice = true;
        serviceImg.setImageDrawable(getResources().getDrawable(R.mipmap.service_off_line_home));
        serviceTv.setText("loading...");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void closeApp(CloseAppEvent event) {
        toFinish();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void getImEvent(RefreshEvent event) {
        if (event.getType() == RefreshEvent.GET_NEW_MSG) {
            serviceMsgRedTv.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void handlerMessage(MessageEvent event) {
        switch (event.getType()) {//0系统，1订单，2公告 3刷新用户信息)
            case "0":
                serviceMsgRedTv.setVisibility(View.VISIBLE);
                break;
            case "1":
                orderMsgRedTv.setVisibility(View.VISIBLE);
                break;
            case "2":
                noticeMsgRedTv.setVisibility(View.VISIBLE);
                break;
            case "3":
                Logger.d("刷新用户系统");
                HttpMethods.getInstance().getUserInfoByUid(new MySubscriber<UserInfoByNameEntity>(null) {

                    @Override
                    public void onHttpCompleted(HttpResult<UserInfoByNameEntity> httpResult) {
                        if (httpResult.isStatus()) {
                            Logger.d("MyUserInfoProvider  UserInfoByNameEntity  : " + httpResult.getInfo().toString());

                            UserInfo userInfo = new UserInfo(
                                    httpResult.getInfo().getRongcloud()
                                    , httpResult.getInfo().getTruename()
                                    , Uri.parse(MyApiService.IMG_BASE_URL + httpResult.getInfo().getPic()));

//                            RxBus.get().post(new RefreshEvent(RefreshEvent.GET_USER_INFO));
                            RongIM.getInstance().refreshUserInfoCache(userInfo);

                        }
                    }

                    @Override
                    public void onHttpError(Throwable e) {

                    }
                }, uKey, event.getUserId());
                break;
            case "99":
                showToast("你的賬號已被系統強制下線");
                toOut();
                break;
            case "404":
//                Toast.makeText(context, "当前网络状态不好，请检查网络设置", Toast.LENGTH_SHORT).show();
//                serviceImg.setImageDrawable(getResources().getDrawable(R.mipmap.service_off_line_home));
//                serviceTv.setText("loading...");
//                myHandler.sendEmptyMessage(IMLOGIN);
                break;
            case "401":
                Toast.makeText(context, "賬號已經在其他設備登陸", Toast.LENGTH_SHORT).show();
                toOut();
                break;
        }
    }

    private void toOut() {
        PreferencesUtil.putBooleanValue(PreferConfigs.isLogin, false);
        Im_Logout();
        context.startActivity(new Intent(context, LoginActivity.class));
        toFinish();
    }

    private void homeInfo() {
        HttpMethods.getInstance().homeInfo(new MySubscriber<HomeInfoEntity>(this) {
            @Override
            public void onHttpCompleted(HttpResult<HomeInfoEntity> httpResult) {
                if (httpResult.getCode() == 0) {
                    myHandler.sendEmptyMessage(IMLOGIN);
                    homeInfoEntity = httpResult.getInfo();
                    noticeMsgTv.setText(homeInfoEntity.getPmd());
                    Log.d("TAG222",homeInfoEntity.getPmd());
                    noticeMsgTv.setSelected(true);
                    initRateInfo();
                    initImageCycle();
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }

        }, uKey);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(callReceiver);
    }

    private void initImageCycle() {
        if (homeInfoEntity != null && homeInfoEntity.getAds() != null && homeInfoEntity.getAds().size() > 0) {
            ArrayList<ADInfo> adDatas = new ArrayList<>();//广告信息
            for (int i = 0; i < homeInfoEntity.getAds().size(); i++) {
                ADInfo info = new ADInfo();
                info.setUrl(MyApiService.IMG_BASE_URL + homeInfoEntity.getAds().get(i).getImg());
                info.setContent(homeInfoEntity.getAds().get(i).getId());//跳转规则
                adDatas.add(info);
            }
            imageCycleView.setImageResources(adDatas, mAdCycleViewListener);
            imageCycleView.startImageCycle();//启动广告轮播动画
        }
    }

    //广告栏图片加载和点击事件
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {

        }

        @Override
        public void displayImage(String imageURL, ImageView imageView) {
            imageView.setTag(null);
            Glide.with(MainActivity.this).load(imageURL).into(imageView);
        }
    };

    @OnClick({R.id.notice_msg_layout, R.id.message_iv, R.id.user_iv, R.id.dian_l, R.id.user_l, R.id.service_l, R.id.order_search_l, R.id.us_layout, R.id.yen_layout, R.id.korean_layout, R.id.rmb_layout, R.id.baht_layout, R.id.hk_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_iv:
                noticeMsgRedTv.setVisibility(View.INVISIBLE);
                toActivity(NoticeListActivity.class);
                break;
            case R.id.user_iv:
            case R.id.user_l:
                toActivity(UserInfoActivity.class);
                break;
            case R.id.dian_l:
                getDianInfo();
                break;
            case R.id.service_l:
                // if (MyApplication.isLogin) {
                serviceMsgRedTv.setVisibility(View.INVISIBLE);
                if (PreferencesUtil.getBooleanValue(PreferConfigs.isKeFu)) {
                    toActivity(MyConversationListFragment.class);
                } else {
//                    toActivity(KeFuListActivity.class);
//                    toActivity(MyEaseContactListActivity.class);
                    toActivity(MyConversationListActivity2.class);
                }
//                } else {
//                    showToast("IM正在登陸中,請稍後再試!");
//                }


                break;
            case R.id.order_search_l:
                orderMsgRedTv.setVisibility(View.INVISIBLE);
                toActivity(OrderListActivity.class);
                break;
            case R.id.us_layout:
                coin_type = CoinTypes.US;
                rateOnClick((HomeInfoEntity.BarrayBean) usImg.getTag(R.id.rate_item_obj));
                break;
            case R.id.yen_layout:
                coin_type = CoinTypes.YEN;
                rateOnClick((HomeInfoEntity.BarrayBean) yenImg.getTag(R.id.rate_item_obj));
                break;
            case R.id.korean_layout:
                coin_type = CoinTypes.KOREAN;
                rateOnClick((HomeInfoEntity.BarrayBean) koreanImg.getTag(R.id.rate_item_obj));
                break;
            case R.id.rmb_layout:
                coin_type = CoinTypes.RMB;
                rateOnClick((HomeInfoEntity.BarrayBean) rmbImg.getTag(R.id.rate_item_obj), true);
                break;
            case R.id.baht_layout:
                coin_type = CoinTypes.BAHT;
                rateOnClick((HomeInfoEntity.BarrayBean) bahtImg.getTag(R.id.rate_item_obj));
                break;
            case R.id.hk_layout:
                coin_type = CoinTypes.HK;
                rateOnClick((HomeInfoEntity.BarrayBean) hkImg.getTag(R.id.rate_item_obj));
                break;
            case R.id.notice_msg_layout:
                break;
        }
    }


    private RateInfoEntity rateInfoEntity;

    private void rateOnClick(HomeInfoEntity.BarrayBean barrayBean) {
        rateOnClick(barrayBean, false);
    }

    private void rateOnClick(final HomeInfoEntity.BarrayBean barrayBean, final boolean isRMB) {
        if (barrayBean != null && barrayBean.isIs_kaipan()) {
            LoaddingShow();

            HttpMethods.getInstance().rateInfo(new MySubscriber<RateInfoEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<RateInfoEntity> rateInfoEntityHttpResult) {
                    if (rateInfoEntityHttpResult.isStatus()) {
                        rateInfoEntity = rateInfoEntityHttpResult.getInfo();
                        showDialog(isRMB, barrayBean, rateInfoEntity);
                    } else {
                        showToast(rateInfoEntityHttpResult.getMsg());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }
            }, uKey, barrayBean.getId());

        } else {
            showToast("還沒開盤!");
        }

    }

    private void initRateInfo() {
        for (int i = 0; i < homeInfoEntity.getBarray().size(); i++) {
            switch (homeInfoEntity.getBarray().get(i).getId()) {
                case "2"://人民幣
                    rmbImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        rmbImg.setImageResource(R.mipmap.rmb2);
                    } else {
                        rmbImg.setImageResource(R.mipmap.rmb);
                    }
                    break;
                case "3"://美元
                    usImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        usImg.setImageResource(R.mipmap.us2);
                    } else {
                        usImg.setImageResource(R.mipmap.us);

                    }
                    break;
                case "4"://泰銖
                    bahtImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        bahtImg.setImageResource(R.mipmap.baht2);
                    } else {
                        bahtImg.setImageResource(R.mipmap.baht);

                    }
                    break;
                case "5"://日元
                    yenImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        yenImg.setImageResource(R.mipmap.yen2);
                    } else {
                        yenImg.setImageResource(R.mipmap.yen);

                    }
                    break;
                case "6"://韓幣
                    koreanImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        koreanImg.setImageResource(R.mipmap.korean2);
                    } else {
                        koreanImg.setImageResource(R.mipmap.korean);

                    }
                    break;
                case "7"://港幣
                    hkImg.setTag(R.id.rate_item_obj, homeInfoEntity.getBarray().get(i));
                    if (!homeInfoEntity.getBarray().get(i).isIs_kaipan()) {
                        hkImg.setImageResource(R.mipmap.hk2);
                    } else {
                        hkImg.setImageResource(R.mipmap.hk);

                    }
                    break;
            }
        }

    }


    private void showDialog(boolean isRMB, HomeInfoEntity.BarrayBean barrayBean, RateInfoEntity rateInfoEntity) {
        this.barrayBean = barrayBean;
        homeRateDialog = new HomeRateDialog(MainActivity.this, isRMB, barrayBean, rateInfoEntity);
        homeRateDialog.setRateShowClickListener(myRateShowClickListener);
        homeRateDialog.show();
        homeRateDialog.startTime();
        startRefreshRate();

    }

    private void startRefreshRate() {

    }

    private long lastTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - lastTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            } else {
               RongIM.getInstance().disconnect();
                toFinish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private CoinTypes coin_type = CoinTypes.RMB;
    RateShowClickListener myRateShowClickListener = new RateShowClickListener() {
        @Override
        public void sell() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
            bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.SELL);
            bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);
            bundle.putString(RateInputActivity.COIN_ID, barrayBean.getId());
            toActivity(RateInputActivity.class, bundle);
        }

        @Override
        public void buy() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
            bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.BUY);
            bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);
            bundle.putString(RateInputActivity.COIN_ID, barrayBean.getId());
            toActivity(RateInputActivity.class, bundle);
        }

        @Override
        public void alipay() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
            bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.ALIPAY);
            bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);
            bundle.putString(RateInputActivity.COIN_ID, barrayBean.getId());
            toActivity(RateInputActivity.class, bundle);
        }

        @Override
        public void otherPay() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
            bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.OTHER);
            bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);
            bundle.putString(RateInputActivity.COIN_ID, barrayBean.getId());
            toActivity(RateInputActivity.class, bundle);
        }

        @Override
        public void pre() {
            Bundle bundle = new Bundle();
            bundle.putSerializable(RateInputActivity.COIN_TYPE, coin_type);
            bundle.putSerializable(RateInputActivity.OPERATE_TYPE, OperateTypes.PRE);
            bundle.putSerializable(RateInputActivity.RATE_INFO, rateInfoEntity);
            bundle.putString(RateInputActivity.COIN_ID, barrayBean.getId());
            toActivity(RateInputActivity.class, bundle);
        }

        @Override
        public void refreshTime() {
            HttpMethods.getInstance().rateInfo(new MySubscriber<RateInfoEntity>(MainActivity.this) {
                @Override
                public void onHttpCompleted(HttpResult<RateInfoEntity> rateInfoEntityHttpResult) {
                    if (rateInfoEntityHttpResult.isStatus()) {
                        if (homeRateDialog != null && homeRateDialog.isShowing()) {
                            rateInfoEntity = rateInfoEntityHttpResult.getInfo();
                            homeRateDialog.initRateTv(rateInfoEntity);
                        }
                    } else {
                        showToast(rateInfoEntityHttpResult.getMsg());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }

            }, uKey, barrayBean.getId());
        }


    };

    public void getDianInfo() {
        HttpMethods.getInstance().getDian(new MySubscriber<DianInfoEntity>(this) {

            @Override
            public void onHttpCompleted(HttpResult<DianInfoEntity> httpResult) {
                if (httpResult.isStatus()) {
                    final DianInfoEntity dianInfoEntity = httpResult.getInfo();
                    DianDialog dianDialog = new DianDialog(context);
                    dianDialog.initDianInfo(dianInfoEntity);
                    dianDialog.setOnClickListener(new DianDialog.OnClickListener() {
                        @Override
                        public void onBuy() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Dian", dianInfoEntity);
                            toActivity(DianBuyInputRateActivity.class, bundle);
                        }

                        @Override
                        public void onGet() {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Dian", dianInfoEntity);
                            toActivity(DianSellInputRateActivity.class, bundle);
                        }
                    });
                    dianDialog.show();
                }
            }

            @Override
            public void onHttpError(Throwable e) {
            }
        }, uKey);
    }


    private void connectIM() {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {
            Logger.d("Token :" + PreferencesUtil.getStringValue(PreferConfigs.rongcloud_token));
            if (TextUtils.isEmpty(PreferencesUtil.getStringValue(PreferConfigs.rongcloud_token))) {
                showToast("token 为空");
                return;
            }
            RongIM.connect(PreferencesUtil.getStringValue(PreferConfigs.rongcloud_token), new RongIMClient.ConnectCallback() {

                @Override
                public void onTokenIncorrect() {
                    Logger.d("Token 错误");
                }

                @Override
                public void onSuccess(String userid) {
                    Logger.d("登錄成功 , userid :" + userid);
//                    MyApplication.isLogin = true;
                    myHandler.sendEmptyMessage(IMLOGIN_SUCCESS);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Logger.d("IM登录失败");
//                    MyApplication.isLogin = false;
//                    myHandler.sendEmptyMessage(IMLOGIN);
//                    myHandler.sendEmptyMessage(IMLOGIN_FAIL);
                }
            });
        }
    }
}
