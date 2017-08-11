package com.likeit.currenciesapp.entity;

import java.io.Serializable;
import java.util.List;

public class HomeInfoEntity implements Serializable{


    private String pmd;
    private List<AdsBean> ads;
    private List<BarrayBean> barray;

    public String getPmd() {
        return pmd;
    }

    public void setPmd(String pmd) {
        this.pmd = pmd;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public List<BarrayBean> getBarray() {
        return barray;
    }

    public void setBarray(List<BarrayBean> barray) {
        this.barray = barray;
    }

    public static class AdsBean implements Serializable{
        /**
         * id : 23
         * cntitle : app顶部广告
         * img : /pub/sys/ggj/attached/image/20161219/20161219214555_12737.jpg
         * url :
         */

        private String id;
        private String cntitle;
        private String img;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCntitle() {
            return cntitle;
        }

        public void setCntitle(String cntitle) {
            this.cntitle = cntitle;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class BarrayBean implements Serializable{
        /**
         * id : 2
         * name : 人民幣
         * pic : /pub/sys/ggj/attached/image/20161219/20161219223839_21551.png
         * is_kaipan : true
         */

        private String id;
        private String name;
        private String pic;
        private boolean is_kaipan;

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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public boolean isIs_kaipan() {
            return is_kaipan;
        }

        public void setIs_kaipan(boolean is_kaipan) {
            this.is_kaipan = is_kaipan;
        }
    }
}
