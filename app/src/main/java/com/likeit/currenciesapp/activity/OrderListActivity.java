package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.adapter.OrderListAdapter;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.entity.OrderInfoEntity;
import com.likeit.currenciesapp.listeners.EndLessOnScrollListener;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.rxbus.RefreshEvent;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OrderListActivity extends BaseActivity {

    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private OrderListAdapter orderListAdapter;
    public int page=1;

    private LinearLayoutManager linearLayoutManager;
    EndLessOnScrollListener endLessOnScrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        initTopBar("交易記錄");
        initSwipeRefresh();
        refreshItems();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD)
    public void getImEvent(RefreshEvent event) {
        if(event.getType()==RefreshEvent.GET_ORDER_LIST){
            page=1;
            refreshItems();
        }
    }

    private void initSwipeRefresh() {
        orderListAdapter=new OrderListAdapter();
        orderListAdapter.setBaseActivity(OrderListActivity.this);
        linearLayoutManager = new LinearLayoutManager(context);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(orderListAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                    page=1;
                    refreshItems();

            }
        });

        endLessOnScrollListener = new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                refreshItems();
            }
        };
        itemsRecyclerView.addOnScrollListener(endLessOnScrollListener);
    }


   public void refreshItems() {
        HttpMethods.getInstance().getOrderList(new MySubscriber<ArrayList<OrderInfoEntity>>(this) {
            @Override
            public void onHttpCompleted(HttpResult<ArrayList<OrderInfoEntity>> httpResult) {
                onRefreshComplete();
                if(httpResult.isStatus()){
                    if(httpResult.getInfo()!=null && httpResult.getInfo().size()>0){
                        if(page==1){
                            orderListAdapter.setDatas(httpResult.getInfo());
                        }else{
                            orderListAdapter.addDatas(httpResult.getInfo());
                        }
                    }
                }else{
                    if(page==1){
                        orderListAdapter.cleanDatas();
                    }
                    showToast("没有更多数据了");
                }
            }

            @Override
            public void onHttpError(Throwable e) {
                onRefreshComplete();
            }
        },uKey,page,100);

    }

    private void onRefreshComplete() {
        if(swipeRefreshLayout!=null){
            swipeRefreshLayout.setRefreshing(false);
        }
        if(endLessOnScrollListener!=null){
            endLessOnScrollListener.onLoadMoreFinish();
        }
    }
}
