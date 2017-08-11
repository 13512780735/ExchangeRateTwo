package com.likeit.currenciesapp.rxbus;


public class RefreshEvent {
    public final static int GET_NEW_MSG=0x110;
    public final static int GET_USER_INFO=0x111;
    public final static int GET_ORDER_LIST=0x112;

    public RefreshEvent(){

    }

    public RefreshEvent(int type){
        this.type=type;
    }

    private int type=0;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
