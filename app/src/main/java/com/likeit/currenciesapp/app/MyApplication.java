package com.likeit.currenciesapp.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.igexin.sdk.PushManager;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.im.MyConnectionStatusListener;
import com.likeit.currenciesapp.im.MyConversationListBehaviorListener;
import com.likeit.currenciesapp.im.MyExtensionModule;
import com.likeit.currenciesapp.im.MyReceiveMessageListener;
import com.likeit.currenciesapp.im.MyUserInfoProvider;
import com.likeit.currenciesapp.service.GePushService;
import com.likeit.currenciesapp.service.MyGeIntentService;
import com.likeit.currenciesapp.utils.PreferencesUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;

public class MyApplication extends MultiDexApplication {

    public static MyApplication myApplication;
    public static boolean isVoiceCalling = true;
    //    public static boolean isLogin = false;
    public static boolean isNotice = true;

    public static MyApplication getInstall() {
        return myApplication;
    }


    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        myApplication = this;
        initLog();
        initSharePreferences(this);
        initGePushService();
        initIM();
        initUpdate();
    }

    private void initUpdate() {
        Bugly.init(getApplicationContext(), "5744a5eb46", false);
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 60 * 60 * 1000;
        Beta.largeIconId = R.mipmap.app_logo;

    }

    private void initIM() {

        RongPushClient.registerHWPush(this);
        RongPushClient.registerMiPush(this, "2882303761517577129", "5261757731129");
//        try {
//            RongPushClient.registerGCM(this);
//        } catch (RongException e) {
//            e.printStackTrace();
//        }
        RongIM.init(this);
        RongIM.setUserInfoProvider(new MyUserInfoProvider(), true);
        RongIM.setConnectionStatusListener(new MyConnectionStatusListener());
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
//        RongIM.setPublicServiceBehaviorListener(new MyServiceBehaviorListener());
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
        setMyExtensionModule();
    }


    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
    }

    private void initSharePreferences(Context context) {
        SharedPreferences preference = context.getSharedPreferences("Prefs", Context.MODE_PRIVATE);
        PreferencesUtil.SetPreference(preference);
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    private void initLog() {
        Logger
                .init("ExchangeRate")            // default PRETTYLOGGER or use just init()
                .methodCount(0)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(0);             // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
    }

    private void initGePushService() {
        PushManager.getInstance().initialize(this.getApplicationContext(), GePushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyGeIntentService.class);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
