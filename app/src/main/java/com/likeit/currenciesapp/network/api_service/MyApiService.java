package com.likeit.currenciesapp.network.api_service;

import com.likeit.currenciesapp.BuildConfig;
import com.likeit.currenciesapp.entity.DianHLEntity;
import com.likeit.currenciesapp.entity.DianInfoEntity;
import com.likeit.currenciesapp.entity.FriendAlipayInfoEntity;
import com.likeit.currenciesapp.entity.HomeInfoEntity;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.entity.LoginUserInfoEntity;
import com.likeit.currenciesapp.entity.NoticeInfoEntity;
import com.likeit.currenciesapp.entity.OrderBeforetInfoEntity;
import com.likeit.currenciesapp.entity.OrderDetailInfoEntity;
import com.likeit.currenciesapp.entity.OrderInfoEntity;
import com.likeit.currenciesapp.entity.PreRateInfoEntity;
import com.likeit.currenciesapp.entity.RateInfoEntity;
import com.likeit.currenciesapp.entity.UserBankEntity;
import com.likeit.currenciesapp.entity.UserInfoByNameEntity;
import com.likeit.currenciesapp.entity.UserInfoEntity;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface MyApiService {
//    String BASE_URL = BuildConfig.DEBUG ? "http://dtb.wbteam.cn/api.php/":"http://2467.cc/api.php/";
//    String IMG_BASE_URL =BuildConfig.DEBUG ? "http://dtb.wbteam.cn/":"http://2467.cc/";

    String BASE_URL = "http://2467.cc/api.php/";
    String IMG_BASE_URL ="http://2467.cc/";

    @FormUrlEncoded
    @POST("?m=login&a=send_sms")
    Observable<HttpResult> getPhoneCheck(@Field("mobile") String mobile, @Field("type") int type);

    @FormUrlEncoded
    @POST("?m=login&a=reg")
    Observable<HttpResult<EmptyEntity>> registeUser(@Field("getuicid") String getuicid,
                                                    @Field("mobile") String mobile,
                                                    @Field("password") String password,
                                                    @Field("truename") String truename,
                                                    @Field("telcode") String telcode);

    @FormUrlEncoded
    @POST("?m=index&a=get_notice")
    Observable<HttpResult<ArrayList<NoticeInfoEntity>>> getNoticList(@Field("ukey") String ukey,
                                                                     @Field("page") int page,
                                                                     @Field("pgsize") int pgsize);

    @FormUrlEncoded
    @POST("?m=order&a=get_list")
    Observable<HttpResult<ArrayList<OrderInfoEntity>>> getOrderList(@Field("ukey") String ukey,
                                                                    @Field("page") int page,
                                                                    @Field("pgsize") int pgsize);

    @FormUrlEncoded
    @POST("?m=order&a=del_order")
    Observable<HttpResult> delOrder(@Field("ukey") String ukey,
                                    @Field("id") String id);

    @FormUrlEncoded
    @POST("?m=order&a=get_dian_lv")
    Observable<HttpResult<DianHLEntity>> getDianHL(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=login&a=get_pwd")
    Observable<HttpResult> resetPasswd(@Field("user_name") String user_name,
                                       @Field("code") String code,
                                       @Field("pwd") String pwd);

    @FormUrlEncoded
    @POST("?m=user&a=edit")
    Observable<HttpResult> modifyPasswd(
            @Field("ukey") String ukey,
            @Field("user_name") String user_name,
            @Field("oldpwd") String oldpwd,
            @Field("pwd") String pwd);


    @FormUrlEncoded
    @POST("?m=login&a=login")
    Observable<HttpResult<LoginUserInfoEntity>> login(@Field("username") String username,
                                                      @Field("password") String password,
                                                      @Field("getuicid") String getuicid);

    @FormUrlEncoded
    @POST("?m=index&a=index")
    Observable<HttpResult<HomeInfoEntity>> homeInfo(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=user&a=search_user")
    Observable<HttpResult<ArrayList<KeFuEntity>>> searchUsers(@Field("ukey") String ukey,
                                                       @Field("pgsize") String pgsize,
                                                       @Field("page") int page,
                                                       @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("?m=index&a=get_huilv")
    Observable<HttpResult<RateInfoEntity>> rateInfo(@Field("ukey") String ukey, @Field("huilvid") String huilvid);

    @FormUrlEncoded
    @POST("?m=order&a=get_zfb")
    Observable<HttpResult<FriendAlipayInfoEntity>> getFriendAlipayInfo(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=order&a=order_before")
    Observable<HttpResult<OrderBeforetInfoEntity>> order_before(@Field("ukey") String ukey,
                                                                @Field("type") String type,
                                                                @Field("style") String style
    );

    @FormUrlEncoded
    @POST("?m=user&a=get_info")
    Observable<HttpResult<UserInfoEntity>> getUserInfo(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=order&a=get_userbank")
    Observable<HttpResult<UserBankEntity>> getUserBank(@Field("ukey") String ukey,
                                                       @Field("type") String type,
                                                       @Field("pty") String pty,
                                                       @Field("type2") String type2);

    @FormUrlEncoded
    @POST("?m=user&a=get_info_by_rongcloudid")
    Observable<HttpResult<UserInfoByNameEntity>> getUserInfoByUid(@Field("ukey") String ukey,@Field("rongcloud_id") String userName);

    @FormUrlEncoded
    @POST("?m=user&a=up_headimg_base64")
    Observable<HttpResult<UserInfoEntity>> uploadFileBase64(@Field("ukey") String ukey, @Field("pic") String pic);

    @FormUrlEncoded
    @POST("?m=order&a=do_order")
    Observable<HttpResult> do_order(@Field("ukey") String ukey,
                                    @Field("type") String type,
                                    @Field("htype1") String htype1,
                                    @Field("htype2") String htype2,
                                    @Field("money") String money,
                                    @Field("tel") String tel,
                                    @Field("bz") String bz,
                                    @Field("hbankid") String hbankid,
                                    @Field("bank1") String bank1,
                                    @Field("bank2") String bank2,
                                    @Field("bank3") String bank3,
                                    @Field("bank4") String bank4,
                                    @Field("bank5") String bank5,
                                    @Field("bank6") String bank6,
                                    @Field("bank11") String bank11,
                                    @Field("bank12") String bank12,
                                    @Field("pty") String pty,
                                    @Field("dt1") String dt1
    );

    @FormUrlEncoded
    @POST("?m=order&a=edit_order")
    Observable<HttpResult> modify_order(@Field("ukey") String ukey,
                                        @Field("id") String id,
                                        @Field("bank1") String bank1,
                                        @Field("bank2") String bank2,
                                        @Field("bank3") String bank3,
                                        @Field("bank4") String bank4,
                                        @Field("bank5") String bank5,
                                        @Field("bank6") String bank6,
                                        @Field("bank11") String bank11,
                                        @Field("bank12") String bank12,
                                        @Field("pty") String pty,
                                        @Field("bz") String bz
    );

    @Multipart
    @POST("?m=user&a=up_headimg")
    Observable<HttpResult> uploadFile(@Part("ukey") String ukey,
                                      @Part("description") RequestBody description,
                                      @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("?m=order&a=order_detail")
    Observable<HttpResult<OrderDetailInfoEntity>> getOrderDetail(@Field("ukey") String ukey,
                                                                 @Field("id") String id);

    @FormUrlEncoded
    @POST("?m=order&a=do_huikuan")
    Observable<HttpResult> do_huikuan(@Field("ukey") String ukey,
                                      @Field("id") String id,
                                      @Field("huming") String huming,
                                      @Field("bankname") String bankname,
                                      @Field("bankcode5") String bankcode5
    );

    @FormUrlEncoded
    @POST("?m=order&a=get_dian")
    Observable<HttpResult<DianInfoEntity>> getDian(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=order&a=get_yugou")
    Observable<HttpResult<ArrayList<PreRateInfoEntity>>> getPreRateInfo(@Field("ukey") String ukey);

    @FormUrlEncoded
    @POST("?m=user&a=kefu")
    Observable<HttpResult<ArrayList<KeFuEntity>>> getKeFu(@Field("ukey") String ukey);

}
