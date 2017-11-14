package com.likeit.currenciesapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.app.MyApplication;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.EaseConstant;
import com.likeit.currenciesapp.im.ConversationFragment01;
import com.likeit.currenciesapp.rxbus.RefreshEvent;
import com.likeit.currenciesapp.utils.DialogUtil;
import com.orhanobut.logger.Logger;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ServiceChatActivity extends BaseActivity {

    private String toChatUsername = "客服";
    private String targetId = "";
    private TextView tvClear;
    private ConversationFragment fragment;
    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getExtras() == null) {
            toFinish();
            return;
        }

        setContentView(R.layout.activity_fragment_service_chat);
        targetId = getIntent().getExtras().getString(EaseConstant.EXTRA_USER_ID, "1");
        tvClear = (TextView) findViewById(R.id.top_bar_tvClear);
        refreshTopBar();
        initView();
    }

    private void initView() {

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showDialog(ServiceChatActivity.this, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, targetId, new RongIMClient.ResultCallback<Boolean>() {
                            @Override
                            public void onSuccess(Boolean aBoolean) {
                                Log.d("TAG", aBoolean.toString());
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Toast.makeText(ServiceChatActivity.this, "清除失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },"","是否確認刪除聊天記錄？").show();

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        initIm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.isNotice = true;
    }


    private void initIm() {
        Logger.d("initIm targetId :" + targetId);
        fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.service_chat_container);

        uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation")
                .appendPath("PRIVATE")
                .appendQueryParameter("targetId", targetId)
                .build();
        if (fragment != null) {
            Logger.d("initIm uri :" + uri.toString());
            fragment.setUri(uri);
        } else {
            Logger.d("fragment  is null :" + uri.toString());
        }

    }

    private void refreshTopBar() {
        UserInfo userinfo = RongUserInfoManager.getInstance().getUserInfo(targetId);
        if (userinfo != null && !TextUtils.isEmpty(userinfo.getUserId())) {
            initTopBar(userinfo.getName());
        } else {
            initTopBar("客服");
        }
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void getImEvent(RefreshEvent event) {
        refreshTopBar();
//        chatFragment.messageList.refresh();
    }

}
