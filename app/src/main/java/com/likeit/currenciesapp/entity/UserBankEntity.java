package com.likeit.currenciesapp.entity;

public class UserBankEntity {


    /**
     {
     "bankname": "test",银行名
     "yhdh": "test",银行代码
     "huming": "test",户名
     "idcard": "test",卡号
     "city": null,省市
     "zhname": null,支行
     "pay_idcard": "44",支付宝账号，淘宝账号，微信账号
     "pay_password": "44",密码
     "pay_name": "44"，姓名
     }
     */

    private String bankname;
    private String yhdh;
    private String huming;
    private String idcard;
    private String city;
    private String zhname;
    private String pay_idcard;
    private String pay_password;
    private String pay_name;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getYhdh() {
        return yhdh;
    }

    public void setYhdh(String yhdh) {
        this.yhdh = yhdh;
    }

    public String getHuming() {
        return huming;
    }

    public void setHuming(String huming) {
        this.huming = huming;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZhname() {
        return zhname;
    }

    public void setZhname(String zhname) {
        this.zhname = zhname;
    }

    public String getPay_idcard() {
        return pay_idcard;
    }

    public void setPay_idcard(String pay_idcard) {
        this.pay_idcard = pay_idcard;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }
}
