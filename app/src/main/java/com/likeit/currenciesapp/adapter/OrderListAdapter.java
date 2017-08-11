package com.likeit.currenciesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.activity.OrderListActivity;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.base.KKBaseAdapter;
import com.likeit.currenciesapp.entity.OrderInfoEntity;



public class OrderListAdapter extends KKBaseAdapter<OrderInfoEntity> {
    @Override
    public BaseViewHolder<OrderInfoEntity> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        OrderViewHolder orderViewHolder=new OrderViewHolder(itemView);
        orderViewHolder.setBaseActivity(baseActivity);
        return orderViewHolder;
    }

    OrderListActivity baseActivity;

    public void setBaseActivity(OrderListActivity baseActivity){
        this.baseActivity=baseActivity;
    }
}
