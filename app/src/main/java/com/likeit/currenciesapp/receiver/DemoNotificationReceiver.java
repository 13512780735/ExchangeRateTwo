package com.likeit.currenciesapp.receiver;

import android.content.Context;
import android.content.Intent;

import com.likeit.currenciesapp.activity.MainActivity;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;



public class DemoNotificationReceiver  extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        return false;

    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }
}