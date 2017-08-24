package com.likeit.currenciesapp.configs;

/**
 * Created by wenfengcai on 2016/12/29.
 *
 * @Autor CaiWF
 * @TODO
 */

public class APIComm {
    public final static String BaseUrl="http://dtb.wbteam.cn/api.php";

    public final static String LoginUrl=BaseUrl+"?m=login&a=login";
    public final static String RegisterUrl=BaseUrl+"?m=login&a=reg";


    public final static String PhoneCheckUrl=BaseUrl+"?m=login&a=send_sms";
    public final static String LoginDetailUrl="http://dtb.wbteam.cn/api.php?m=login&a=reg_detail";
    public final static String HomeUrl=BaseUrl+"?m=index&a=index";
}
