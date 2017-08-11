package com.likeit.currenciesapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.rxbus.RefreshEvent;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import java.util.ArrayList;
import java.util.HashMap;

public class MyEaseContactListActivity extends BaseActivity {

    /*EaseContactListFragment contactListFragment;
    private HashMap<String,EaseUser> contactsMap=new HashMap<>();

    private Handler myHandler =new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_contact_list);
        initTopBar("客服");

        HttpMethods.getInstance().getKeFu(new MySubscriber<ArrayList<KeFuEntity>>(this) {
            @Override
            public void onHttpCompleted(HttpResult<ArrayList<KeFuEntity>> httpResult) {
                if(httpResult.isStatus()){
                    if(httpResult.getInfo()!=null && httpResult.getInfo().size()>0){
                        for (int i=0;i<httpResult.getInfo().size();i++){
                            EaseUser easeUser=new EaseUser(httpResult.getInfo().get(i).getRongcloud());
                            easeUser.setAvatar(httpResult.getInfo().get(i).getPic());
                            easeUser.setAvatar(httpResult.getInfo().get(i).getTruename());
                            contactsMap.put(httpResult.getInfo().get(i).getRongcloud(),easeUser);
                        }
                        initContactList();
                    }
                }else{
                    showToast("没有更多数据了");
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        },uKey);
    }

    private void initContactList(){
        contactListFragment = new EaseContactListFragment();
        //需要设置联系人列表才能启动fragment
        contactListFragment.setContactsMap(contactsMap);
        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(MyEaseContactListActivity.this, ServiceChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.container, contactListFragment).commit();

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                contactListFragment.hideTitleBar();
                contactListFragment.refresh();
            }
        },500);
    }
    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void getImEvent(RefreshEvent event) {
        contactListFragment.refresh();
    }*/
}
