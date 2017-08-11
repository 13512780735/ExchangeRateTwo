package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.adapter.KeFuListAdapter;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.listeners.EndLessOnScrollListener;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KeFuSearchActivity extends BaseActivity {

    @BindView(R.id.top_bar_back_img)
    ImageView topBarBackImg;
    @BindView(R.id.search_content_et)
    EditText searchContentEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.itemsRecyclerView)
    RecyclerView itemsRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    EndLessOnScrollListener endLessOnScrollListener;
    private KeFuListAdapter kefuListAdapter;
    private boolean isHttp = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu_search);
        ButterKnife.bind(this);
        initSwipeRefresh();
    }


    private void initSwipeRefresh() {
        kefuListAdapter = new KeFuListAdapter();
        kefuListAdapter.setBaseActivity(this);
        linearLayoutManager = new LinearLayoutManager(context);
        itemsRecyclerView.setLayoutManager(linearLayoutManager);
        itemsRecyclerView.setAdapter(kefuListAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
               R.color.holo_orange_light, R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                page = 1;
                search();
            }
        });

        endLessOnScrollListener = new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                page++;
                search();
            }
        };
        itemsRecyclerView.addOnScrollListener(endLessOnScrollListener);

    }


    @OnClick({R.id.top_bar_back_img, R.id.search_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back_img:
                toFinish();
                break;
            case R.id.search_tv:
                search();
                break;
        }
    }

    private void search() {
        String searchContent = searchContentEt.getText().toString();
        if (TextUtils.isEmpty(searchContent)) {
            showToast("請輸入用戶名或ID");
            onRefreshComplete();
            return;
        }

        HttpMethods.getInstance().searchUsers(new MySubscriber<ArrayList<KeFuEntity>>(this) {

            @Override
            public void onHttpCompleted(HttpResult<ArrayList<KeFuEntity>> httpResult) {
                onRefreshComplete();
                if(httpResult.isStatus()){
                    if (httpResult.getInfo() != null && httpResult.getInfo().size() > 0) {
                        if (page == 1) {
                            kefuListAdapter.setDatas(httpResult.getInfo());
                        } else {
                            kefuListAdapter.addDatas(httpResult.getInfo());
                        }
                    } else {
                        showToast("没有更多数据了");
                    }
                }else{
                    showToast(httpResult.getMsg());
                }


            }

            @Override
            public void onHttpError(Throwable e) {
                onRefreshComplete();
            }
        }, uKey,"100",page,searchContent);
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
