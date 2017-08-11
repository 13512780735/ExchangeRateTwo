package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.adapter.KeFuListAdapter;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class KeFuListActivity extends BaseActivity {

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private KeFuListAdapter kefuListAdapter;
    private boolean isHttp=true;

    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initTopBar("客服");
        initSwipeRefresh();
        refreshItems();
    }

    private void initSwipeRefresh() {
        kefuListAdapter =new KeFuListAdapter();
        kefuListAdapter.setBaseActivity(this);
        linearLayoutManager= new LinearLayoutManager(context);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(kefuListAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                if(isHttp){
                    isHttp=true;
                    kefuListAdapter.cleanDatas();
                    refreshItems();
                }else{
                    showToast("没有更多数据了");
                }
            }
        });

    }


    void refreshItems() {
        HttpMethods.getInstance().getKeFu(new MySubscriber<ArrayList<KeFuEntity>>(this) {
            @Override
            public void onHttpCompleted(HttpResult<ArrayList<KeFuEntity>> httpResult) {
                if(httpResult.isStatus()){
                    if(httpResult.getInfo()!=null && httpResult.getInfo().size()>0){
                        kefuListAdapter.addDatas(httpResult.getInfo());
                    }
                }else{
                    isHttp=false;
                    showToast("没有更多数据了");
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        },uKey);

        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }
}
