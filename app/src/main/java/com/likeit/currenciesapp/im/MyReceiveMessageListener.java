package com.likeit.currenciesapp.im;

import com.hwangjr.rxbus.RxBus;
import com.likeit.currenciesapp.rxbus.RefreshEvent;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class MyReceiveMessageListener implements  RongIMClient.OnReceiveMessageListener{

    @Override
    public boolean onReceived(Message message, int i) {
//        Logger.d("收到消息");
        RxBus.get().post(new RefreshEvent(RefreshEvent.GET_NEW_MSG));
        return false;
    }
}
