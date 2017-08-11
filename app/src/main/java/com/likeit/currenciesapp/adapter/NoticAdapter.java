package com.likeit.currenciesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.base.KKBaseAdapter;
import com.likeit.currenciesapp.entity.NoticeInfoEntity;


public class NoticAdapter extends KKBaseAdapter<NoticeInfoEntity> {

    @Override
    public BaseViewHolder<NoticeInfoEntity> onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notic, parent, false);
        return new NoticViewHolder(itemView);
    }
}
