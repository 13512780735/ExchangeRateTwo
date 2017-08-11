package com.likeit.currenciesapp.im;


import android.text.TextUtils;

import com.hwangjr.rxbus.RxBus;

import com.likeit.currenciesapp.rxbus.MessageEvent;
import com.orhanobut.logger.Logger;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MyUserInfoProvider implements RongIM.UserInfoProvider {
    @Override
    public UserInfo getUserInfo(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            Logger.d("MyUserInfoProvider  getUserInfo  :" + userId);
            MessageEvent event=new MessageEvent("3");
            event.setUserId(userId);
            RxBus.get().post(event);
        }
        return null;
    }
}
