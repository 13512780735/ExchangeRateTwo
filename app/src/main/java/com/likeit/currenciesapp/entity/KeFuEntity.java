package com.likeit.currenciesapp.entity;

import java.io.Serializable;


public class KeFuEntity implements Serializable {

    /**
     * rongcloud : 0953763813
     * truename : 林士超
     */

    private String rongcloud;
    private String truename;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRongcloud() {
        return rongcloud;
    }

    public void setRongcloud(String rongcloud) {
        this.rongcloud = rongcloud;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }
}
