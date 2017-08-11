package com.likeit.currenciesapp.activity;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.adapter.NoticAdapter;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.entity.NoticeInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesActivity extends BaseActivity {

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private NoticAdapter noticeAdapter;
    private int page=1;
    private boolean isHttp=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        initTopBar("通知公告");
        refreshItems();
        initSwipeRefresh();
    }

    private void initSwipeRefresh() {
        noticeAdapter=new NoticAdapter();
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        itemsRecyclerView.setAdapter(noticeAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                if(isHttp){
                    isHttp=true;
                    page=1;
                    noticeAdapter.cleanDatas();
                    refreshItems();
                }else{
                    showToast("没有更多数据了");
                }
            }
        });

        itemsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Logger.d("newState :"+newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Logger.d("onScrolled ...dy :"+dy);
                Logger.d("onScrolled ...dx :"+dx);
            }
        });
    }


    void refreshItems() {
        HttpMethods.getInstance().getNoticList(new MySubscriber<ArrayList<NoticeInfoEntity>>(this) {
            @Override
            public void onHttpCompleted(HttpResult<ArrayList<NoticeInfoEntity>> httpResult) {
                if(httpResult.isStatus()){
                    if(httpResult.getInfo()!=null && httpResult.getInfo().size()>0){
                        noticeAdapter.addDatas(httpResult.getInfo());
                    }
                }else{
                    isHttp=false;
                    showToast("没有更多数据了");
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        },uKey,page,100);

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }
}
