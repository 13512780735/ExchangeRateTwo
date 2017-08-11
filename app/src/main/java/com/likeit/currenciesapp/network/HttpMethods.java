package com.likeit.currenciesapp.network;


import com.likeit.currenciesapp.entity.NoticeInfoEntity;
import com.likeit.currenciesapp.entity.OrderBeforetInfoEntity;
import com.likeit.currenciesapp.entity.OrderInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.entity.LoginUserInfoEntity;
import com.likeit.currenciesapp.network.entity.BaseHttpMethods;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethods extends BaseHttpMethods {
//    private static final int DEFAULT_TIMEOUT = 5;
//    private Retrofit retrofit;
//    private MyApiService myApiService;
//
//    //构造方法私有
//    private HttpMethods() {
//        //手动创建一个OkHttpClient并设置超时时间
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//
//        retrofit = new Retrofit.Builder()
//                .client(builder.build())
//                //modify by zqikai 20160317 for 对http请求结果进行统一的预处理 GosnResponseBodyConvert
////                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ResponseConvertFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(MyApiService.BASE_URL)
//                .build();
//
//        myApiService = retrofit.create(MyApiService.class);
//    }

    private HttpMethods() {
        super();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void uploadFileBase64(MySubscriber subscriber, String uKey, String pic) {
        toSubscribe(myApiService.uploadFileBase64(uKey, pic), subscriber);
    }

    public void uploadFile(MySubscriber subscriber, String uKey, RequestBody description, MultipartBody.Part file) {
        toSubscribe(myApiService.uploadFile(uKey, description, file), subscriber);
    }

    public void getUserInfo(MySubscriber subscriber, String uKey) {
        toSubscribe(myApiService.getUserInfo(uKey), subscriber);
    }

    public void getPreRateInfo(MySubscriber subscriber, String uKey) {
        toSubscribe(myApiService.getPreRateInfo(uKey), subscriber);
    }

    public void getUserInfoByUid(MySubscriber subscriber, String uKey, String uid) {
        toSubscribe(myApiService.getUserInfoByUid(uKey,uid), subscriber);
    }


    public void getOrderDetail(MySubscriber subscriber, String uKey, String id) {
        toSubscribe(myApiService.getOrderDetail(uKey, id), subscriber);
    }

    public void do_huikuan(MySubscriber subscriber, String uKey, String id, String huming, String bankname, String bankcode5) {
        toSubscribe(myApiService.do_huikuan(uKey, id, huming, bankname, bankcode5), subscriber);
    }

    public void getDian(MySubscriber subscriber, String uKey) {
        toSubscribe(myApiService.getDian(uKey), subscriber);
    }

    public void searchUsers(MySubscriber subscriber, String uKey,String pgsize,int page,String keyword) {
        toSubscribe(myApiService.searchUsers(uKey,pgsize,page,keyword), subscriber);
    }

    public void getDianHuiLv(MySubscriber subscriber, String uKey) {
        toSubscribe(myApiService.getDianHL(uKey), subscriber);
    }

    public void getKeFu(MySubscriber subscriber, String uKey) {
        toSubscribe(myApiService.getKeFu(uKey), subscriber);
    }

    public void modifyPasswd(MySubscriber subscriber, String ukey, String user_name, String oldpwd, String pwd) {
        toSubscribe(myApiService.modifyPasswd(ukey, user_name, oldpwd, pwd), subscriber);
    }

    public void resetPasswd(MySubscriber subscriber, String user_name, String code, String pwd) {
        toSubscribe(myApiService.resetPasswd(user_name, code, pwd), subscriber);
    }

    public void getNoticList(MySubscriber<ArrayList<NoticeInfoEntity>> subscriber, String ukey, int page, int pgsize) {

        Observable observable = myApiService.getNoticList(ukey, page, pgsize);
        toSubscribe(observable, subscriber);
    }


    public void getUserBank(MySubscriber subscriber, String ukey, String type, String pty,String type2) {
        toSubscribe( myApiService.getUserBank(ukey, type, pty,type2), subscriber);
    }

    public void getOrderList(MySubscriber<ArrayList<OrderInfoEntity>> subscriber, String ukey, int page, int pgsize) {

        Observable observable = myApiService.getOrderList(ukey, page, pgsize);
        toSubscribe(observable, subscriber);
    }


    public void getPhoneCheck(MySubscriber subscriber, String mobile, int type) {

        Observable observable = myApiService.getPhoneCheck(mobile, type);
        toSubscribe(observable, subscriber);
    }

    public void getFriendAlipayInfo(MySubscriber subscriber, String uKey) {

        Observable observable = myApiService.getFriendAlipayInfo(uKey);
        toSubscribe(observable, subscriber);
    }

    public void do_order(MySubscriber subscriber, String ukey,
                         String type,
                         String htype1,
                         String htype2,
                         String money,
                         String tel,
                         String bz,
                         String hbankid,
                         String bank1,
                         String bank2,
                         String bank3,
                         String bank4,
                         String bank5,
                         String bank6,
                         String bank11,
                         String bank12) {
        do_order(subscriber, ukey, type, htype1, htype2, money, tel, bz, hbankid, bank1, bank2, bank3, bank4, bank5, bank6,
                bank11, bank12, "");
    }

    public void do_order(MySubscriber subscriber, String ukey,
                         String type,
                         String htype1,
                         String htype2,
                         String money,
                         String tel,
                         String bz,
                         String hbankid,
                         String bank1,
                         String bank2,
                         String bank3,
                         String bank4,
                         String bank5,
                         String bank6,
                         String bank11,
                         String bank12,
                         String pty) {
        do_order(subscriber, ukey, type, htype1, htype2, money, tel, bz, hbankid, bank1, bank2, bank3, bank4, bank5, bank6,
                bank11, bank12, pty, "");
    }

    public void do_order(MySubscriber subscriber, String ukey,
                         String type,
                         String htype1,
                         String htype2,
                         String money,
                         String tel,
                         String bz,
                         String hbankid,
                         String bank1,
                         String bank2,
                         String bank3,
                         String bank4,
                         String bank5,
                         String bank6,
                         String bank11,
                         String bank12,
                         String pty,
                         String dt1) {
        Observable<HttpResult> observable = myApiService.do_order(ukey, type, htype1, htype2, money, tel, bz
                , hbankid, bank1, bank2, bank3, bank4, bank5, bank6, bank11, bank12, pty, dt1);
        toSubscribe(observable, subscriber);
    }

    public void modify_order(MySubscriber subscriber, String ukey,
                             String id,
                             String bank1,
                             String bank2,
                             String bank3,
                             String bank4,
                             String bank5,
                             String bank6,
                             String bank11,
                             String bank12,
                             String pty,
                             String bz) {
        Observable<HttpResult> observable = myApiService.modify_order(ukey, id,
                 bank1, bank2, bank3, bank4, bank5, bank6, bank11, bank12, pty, bz );
        toSubscribe(observable, subscriber);
    }

    public void order_before(Subscriber<HttpResult<OrderBeforetInfoEntity>> subscriber, String ukey, String type) {
        order_before(subscriber, ukey, type, "other");
    }

    public void order_before(Subscriber<HttpResult<OrderBeforetInfoEntity>> subscriber, String ukey, String type, String style) {
        Logger.d("order_before type :" + type);
        Observable<HttpResult<OrderBeforetInfoEntity>> observable = myApiService.order_before(ukey, type, style);
        toSubscribe(observable, subscriber);
    }

    public void rateInfo(Subscriber<HttpResult<RateInfoEntity>> subscriber, String ukey, String huilvid) {
        Observable<HttpResult<RateInfoEntity>> observable = myApiService.rateInfo(ukey, huilvid);
        toSubscribe(observable, subscriber);
    }

    public void homeInfo(Subscriber subscriber, String ukey) {
        Observable observable = myApiService.homeInfo(ukey);
        toSubscribe(observable, subscriber);
    }

    public void delOrder(Subscriber subscriber, String ukey, String id) {
        Observable observable = myApiService.delOrder(ukey, id);
        toSubscribe(observable, subscriber);
    }

    public void login(Subscriber<HttpResult<LoginUserInfoEntity>> subscriber, String username, String password, String getuicid) {

        Observable<HttpResult<LoginUserInfoEntity>> observable = myApiService.login(username, password, getuicid);
        toSubscribe(observable, subscriber);
    }

    public void registerUser(MySubscriber<EmptyEntity> subscriber, String getuicid, String mobile, String password, String truename, String telcode) {
        Observable<HttpResult<EmptyEntity>> observable = myApiService.registeUser(getuicid, mobile, password, truename, telcode);
        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


}
