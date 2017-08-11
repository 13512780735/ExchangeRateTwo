package com.likeit.currenciesapp.entity;

import java.io.Serializable;


public class RateInfoEntity implements Serializable{


    /**
     * buy : 4.69
     * chongzhi : 4.69
     * daifu : 4.82
     * daigou : 4.86
     * yugou : 4.69
     * sold : 4.59
     * qm : 4.698
     * sxf : {"nomore":"30000","bl":"1","max":"50"}
     * sxf_alipay : {"nomore":"1000","bl":0,"max":"10"}
     */

    private double buy;
    private double chongzhi;
    private double daifu;
    private double daigou;
    private double yugou;
    private double sold;
    private double qm;
    private SxfBean sxf;
    private SxfAlipayBean sxf_alipay;
    private Zsds zsds;

    public Zsds getZsds() {
        return zsds;
    }

    public void setZsds(Zsds zsds) {
        this.zsds = zsds;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getChongzhi() {
        return chongzhi;
    }

    public void setChongzhi(double chongzhi) {
        this.chongzhi = chongzhi;
    }

    public double getDaifu() {
        return daifu;
    }

    public void setDaifu(double daifu) {
        this.daifu = daifu;
    }

    public double getDaigou() {
        return daigou;
    }

    public void setDaigou(double daigou) {
        this.daigou = daigou;
    }

    public double getYugou() {
        return yugou;
    }

    public void setYugou(double yugou) {
        this.yugou = yugou;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public double getQm() {
        return qm;
    }

    public void setQm(double qm) {
        this.qm = qm;
    }

    public SxfBean getSxf() {
        return sxf;
    }

    public void setSxf(SxfBean sxf) {
        this.sxf = sxf;
    }

    public SxfAlipayBean getSxf_alipay() {
        return sxf_alipay;
    }

    public void setSxf_alipay(SxfAlipayBean sxf_alipay) {
        this.sxf_alipay = sxf_alipay;
    }

    public static class SxfBean implements Serializable{
        /**
         * nomore : 30000
         * bl : 1
         * max : 50
         */

        private String nomore;
        private String bl;
        private String max;

        public String getNomore() {
            return nomore;
        }

        public void setNomore(String nomore) {
            this.nomore = nomore;
        }

        public String getBl() {
            return bl;
        }

        public void setBl(String bl) {
            this.bl = bl;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }
    }

    public static class SxfAlipayBean implements Serializable{
        /**
         * nomore : 1000
         * bl : 0
         * max : 10
         */

        private String nomore;
        private int bl;
        private String max;

        public String getNomore() {
            return nomore;
        }

        public void setNomore(String nomore) {
            this.nomore = nomore;
        }

        public int getBl() {
            return bl;
        }

        public void setBl(int bl) {
            this.bl = bl;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }
    }

    public static class Zsds implements Serializable{

        private String is_zs;
        private String min_money;
        private String zs_money;

        public String getIs_zs() {
            return is_zs;
        }

        public void setIs_zs(String is_zs) {
            this.is_zs = is_zs;
        }

        public String getMin_money() {
            return min_money;
        }

        public void setMin_money(String min_money) {
            this.min_money = min_money;
        }

        public String getZs_money() {
            return zs_money;
        }

        public void setZs_money(String zs_money) {
            this.zs_money = zs_money;
        }
    }
}
