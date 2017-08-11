package com.likeit.currenciesapp.entity;


import java.io.Serializable;

public class DianInfoEntity implements Serializable {


    /**
     * nian : 5%
     * dianshu : 0點
     * y_dianshu : 0點
     * total : 0點
     */

    private String nian;
    private String dianshu;
    private String y_dianshu;
    private String total;

    public String getNian() {
        return nian;
    }

    public void setNian(String nian) {
        this.nian = nian;
    }

    public String getDianshu() {
        return dianshu;
    }

    public void setDianshu(String dianshu) {
        this.dianshu = dianshu;
    }

    public String getY_dianshu() {
        return y_dianshu;
    }

    public void setY_dianshu(String y_dianshu) {
        this.y_dianshu = y_dianshu;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
