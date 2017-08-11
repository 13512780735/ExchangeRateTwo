package com.likeit.currenciesapp.activity;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.app.MyApplication;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.rxbus.RefreshEvent;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MyConversationListActivity2 extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list2);
        initTopBar();
        initIm();
    }

    private ImageView top_bar_right_tv;
    private void initTopBar(){
        top_bar_back_img= (ImageView) findViewById(R.id.top_bar_back_img);
        top_bar_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFinish();
            }
        });
        top_bar_title= (TextView) findViewById(R.id.top_bar_title);
        top_bar_right_tv= (ImageView) findViewById(R.id.top_bar_right_tv);
        top_bar_title.setText("聯繫人");
//        if(!MyApplication.isLogin){
//            top_bar_right_tv.setImageDrawable(getResources().getDrawable(R.mipmap.service_off_line));
//        }

        top_bar_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(MyApplication.isLogin){
                    toActivity(KeFuListActivity.class);
//                }else{
//                    showToast("IM正在登陸中,請稍後再試!");
//                }
            }
        });
    }

    private void initIm(){
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);
        getSupportFragmentManager().beginTransaction().add(R.id.container, conversationListFragment).commit();

    }

}
