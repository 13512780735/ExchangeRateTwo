//package com.likeit.currenciesapp.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Toast;
//
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.easeui.ui.EaseChatFragment;
//import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
//import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
//import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
//import com.likeit.currenciesapp.R;
//import com.likeit.currenciesapp.activity.VoiceCallActivity;
//import com.likeit.currenciesapp.configs.Constant;
//import com.likeit.currenciesapp.ui.ChatRowVoiceCall;
//
//import java.util.List;
//
//public class ChatFragmentMy extends EaseChatFragment implements EaseChatFragmentHelper {
//
//
//    private static final int ITEM_VOICE_CALL = 13;
//
//    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
//    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    protected void registerExtendMenuItem() {
//        //use the menu in base class
//        super.registerExtendMenuItem();
//        //extend menu items
//        if(chatType == Constant.CHATTYPE_SINGLE){
//            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onSetMessageAttributes(EMMessage message) {
//
//    }
//
//    @Override
//    protected void setUpView() {
//        setChatFragmentListener(this);
//        super.setUpView();
//    }
//
//    @Override
//    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
//        return new CustomChatRowProvider();
//    }
//
//
//    @Override
//    public void onEnterToChatDetails() {
//    }
//
//    @Override
//    public void onAvatarClick(String username) {
//    }
//
//    @Override
//    public void onAvatarLongClick(String username) {
//        inputAtUsername(username);
//    }
//
//
//    @Override
//    public boolean onMessageBubbleClick(EMMessage message) {
//        return false;
//    }
//
//    @Override
//    public void onCmdMessageReceived(List<EMMessage> messages) {
//        super.onCmdMessageReceived(messages);
//    }
//
//    @Override
//    public void onMessageBubbleLongClick(EMMessage message) {
//    	// no message forward when in chat room
//    }
//
//    @Override
//    public boolean onExtendMenuItemClick(int itemId, View view) {
//        switch (itemId) {
//        case ITEM_VOICE_CALL:
//            startVoiceCall();
//            break;
//        default:
//            break;
//        }
//        //keep exist extend menu
//        return false;
//    }
//
//
//    /**
//     * make a voice call
//     */
//    protected void startVoiceCall() {
//        Log.d(TAG,"startVoiceCall....");
//        if (!EMClient.getInstance().isConnected()) {
//            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
//        } else {
//            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
//                    .putExtra("isComingCall", false));
//            // voiceCallBtn.setEnabled(false);
//            inputMenu.hideExtendMenuContainer();
//        }
//    }
//
//
//
//    /**
//     * chat row provider
//     *
//     */
//    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
//        @Override
//        public int getCustomChatRowTypeCount() {
//            //here the number is the message type in EMMessage::Type
//        	//which is used to count the number of different chat row
//            return 4;
//        }
//
//        @Override
//        public int getCustomChatRowType(EMMessage message) {
//            if(message.getType() == EMMessage.Type.TXT){
//                //voice call
//                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
//                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
//                }
//            }
//            return 0;
//        }
//
//        @Override
//        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
//            if(message.getType() == EMMessage.Type.TXT){
//                // voice call
//                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ){
//                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
//                }
//            }
//            return null;
//        }
//
//    }
//
//}
