package com.likeit.currenciesapp.service;

import android.content.Context;

import com.hwangjr.rxbus.RxBus;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.rxbus.MessageEvent;
import com.likeit.currenciesapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;

/**
 * http://2467.cc/api.php/?m=login&a=pushtest&cid=c2bae224193ce2a3582a52204a0cf976&type=99
 */
public class MyGeIntentService extends GTIntentService {

    public MyGeIntentService(){

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
       String msgId= msg.getMessageId();
        Logger.d("msgId :"+msgId);
        //接收透传数据（Payload）：就是Jpush里面的自定义消息
        String type=new String(msg.getPayload());
        Logger.d("msg = "+type);
        RxBus.get().post(new MessageEvent(type));
//        PreferencesUtil.putStringValue(PreferConfigs.PushNoticMsgType,type);
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Logger.d("onReceiveClientId -> " + "clientid = " + clientid);
        PreferencesUtil.putStringValue(PreferConfigs.GeTuiId,clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Logger.d("onReceiveOnlineState   online :"+online);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Logger.d("onReceiveCommandResult :"+cmdMessage.toString());

    }
}
