package com.likeit.currenciesapp.entity;

import java.io.Serializable;



public class LoginUserInfoEntity implements Serializable{

    /**
     * ukey : mEMmafY86FijrGhr59WPPtBI6V*zX57K
     * user : {"user_id":"2","truename":"黃昌元","getuicid":null,"idcard":"","tel":"","address":"","job":"","blanklist":"0","audit":"1","work":"1","del":"0","did":"0","up":"0.060","down":"0.000","s1":"0.060","s2":"0.160","s3":"0.002","s4":"0.010","s5":"0.000","s6":"0.000","user_name":"0938328990","addtime":"2015-05-07 15:23:38","logintime":"2016-12-31 11:59:51"}
     */

    private String ukey;
    private UserInfoEntity user;

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }

    public UserInfoEntity getUser() {
        return user;
    }

    public void setUser(UserInfoEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginUserInfoEntity{" +
                "ukey='" + ukey + '\'' +
                ", user=" + user +
                '}';
    }
}
