package com.likeit.currenciesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    List<T> ts;
    Context context;
    LayoutInflater inflater;

    public MyBaseAdapter(Context context, List<T> ts) {
        this.ts = ts;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MyBaseAdapter(List<T> ts) {
        this.ts = ts;
    }

    @Override
    public int getCount() {
        return ts.size();
    }

    @Override
    public T getItem(int position) {
        return ts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }

    public abstract View getItemView(int position, View convertView, ViewGroup parent);

    public void addAll(List<T> list, boolean isClearDatasource) {
        if (isClearDatasource) {
            ts.clear();
        }
        ts.addAll(list);
        notifyDataSetChanged();

    }

    public void addAll(List<T> list) {
        ts.clear();
        ts.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        ts.remove(t);
        notifyDataSetChanged();
    }

    public void removeAll(List<T> list) {
        list.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatasource() {
        return ts;
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

}