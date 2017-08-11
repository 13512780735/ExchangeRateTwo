package com.likeit.currenciesapp.im;


import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.likeit.currenciesapp.activity.ServiceChatActivity;
import com.likeit.currenciesapp.configs.EaseConstant;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

public class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener{

    /**
     * 当点击会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId){
        Intent intent=new Intent(context, ServiceChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, targetId);
        context.startActivity(intent);
        return false;
    }

    /**
     * 当长按会话头像后执行。
     *
     * @param context          上下文。
     * @param conversationType 会话类型。
     * @param targetId         被点击的用户id。
     * @return 如果用户自己处理了点击后的逻辑处理，则返回 true，否则返回 false，false 走融云默认处理方式。
     */
    public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId){
        return false;
    }
    /**
     * 长按会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 长按时的会话条目。
     * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
        return false;
    }

    /**
     * 点击会话列表中的 item 时执行。
     *
     * @param context        上下文。
     * @param view           触发点击的 View。
     * @param uiConversation 会话条目。
     * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
     */
    @Override
    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
        Intent intent=new Intent(context, ServiceChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, uiConversation.getConversationTargetId());
        context.startActivity(intent);
        return true;
    }
}