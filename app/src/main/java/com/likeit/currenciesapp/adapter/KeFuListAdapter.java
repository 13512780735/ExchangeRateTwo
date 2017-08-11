package com.likeit.currenciesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.activity.KeFuListActivity;
import com.likeit.currenciesapp.activity.OrderListActivity;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.base.KKBaseAdapter;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.entity.OrderInfoEntity;

import butterknife.BindView;


public class KeFuListAdapter extends KKBaseAdapter<KeFuEntity> {

    @Override
    public BaseViewHolder<KeFuEntity> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kefu_list, parent, false);
        KeFuViewHolder orderViewHolder = new KeFuViewHolder(itemView);
        orderViewHolder.setBaseActivity(baseActivity);
        return orderViewHolder;
    }

    BaseActivity baseActivity;

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }
}
