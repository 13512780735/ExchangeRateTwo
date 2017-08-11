package com.likeit.currenciesapp.entity;


import java.io.Serializable;

public class OrderInfoEntity implements Serializable{


    /**
     * id : 3683
     * orderid : 7367_1483636548
     * addtime : 2017-01-06 01:15
     * ty : 1
     * hb1 : 1
     * hb2 : 2
     * hv : 4.5900
     * money : 1000.00
     * hmoney : 4544.0000
     * smoney : 10.0000
     * hkzt : 0
     * zt : 99
     * hbinfo : {"id":"2","name":"人民幣"}
     */

    private String id;
    private String orderid;
    private String addtime;
    private String ty;
    private String hb1;
    private String hb2;
    private String hv;
    private String money;
    private String hmoney;
    private String smoney;
    private String hkzt;
    private String zt;
    private HbinfoBean hbinfo;
    private String zyq_day;
    private String zyq_end_time;
    private String zs_money;

    public String getZs_money() {
        return zs_money;
    }

    public void setZs_money(String zs_money) {
        this.zs_money = zs_money;
    }

    public String getZyq_day() {
        return zyq_day;
    }

    public void setZyq_day(String zyq_day) {
        this.zyq_day = zyq_day;
    }

    public String getZyq_end_time() {
        return zyq_end_time;
    }

    public void setZyq_end_time(String zyq_end_time) {
        this.zyq_end_time = zyq_end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String getHb1() {
        return hb1;
    }

    public void setHb1(String hb1) {
        this.hb1 = hb1;
    }

    public String getHb2() {
        return hb2;
    }

    public void setHb2(String hb2) {
        this.hb2 = hb2;
    }

    public String getHv() {
        return hv;
    }

    public void setHv(String hv) {
        this.hv = hv;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getHmoney() {
        return hmoney;
    }

    public void setHmoney(String hmoney) {
        this.hmoney = hmoney;
    }

    public String getSmoney() {
        return smoney;
    }

    public void setSmoney(String smoney) {
        this.smoney = smoney;
    }

    public String getHkzt() {
        return hkzt;
    }

    public void setHkzt(String hkzt) {
        this.hkzt = hkzt;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public HbinfoBean getHbinfo() {
        return hbinfo;
    }

    public void setHbinfo(HbinfoBean hbinfo) {
        this.hbinfo = hbinfo;
    }

    public static class HbinfoBean implements Serializable{
        /**
         * id : 2
         * name : 人民幣
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
