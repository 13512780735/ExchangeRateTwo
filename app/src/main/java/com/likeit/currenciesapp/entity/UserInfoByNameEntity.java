package com.likeit.currenciesapp.entity;


import java.io.Serializable;

public class UserInfoByNameEntity implements Serializable{

    /**
     * rongcloud : 0938328990
     * truename : 黃昌元
     * pic : /img/pic/2/2_headimg.png
     */

    private String rongcloud;
    private String truename;
    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    @Override
    public String toString() {
        return "UserInfoByNameEntity{" +
                "rongcloud='" + rongcloud + '\'' +
                ", truename='" + truename + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
