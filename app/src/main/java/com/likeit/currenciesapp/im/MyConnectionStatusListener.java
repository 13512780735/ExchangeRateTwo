package com.likeit.currenciesapp.im;

import com.hwangjr.rxbus.RxBus;
import com.likeit.currenciesapp.rxbus.MessageEvent;
import io.rong.imlib.RongIMClient;

public class MyConnectionStatusListener implements RongIMClient.ConnectionStatusListener {

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {

        switch (connectionStatus) {
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                RxBus.get().post(new MessageEvent("401"));
                break;
        }
    }



}