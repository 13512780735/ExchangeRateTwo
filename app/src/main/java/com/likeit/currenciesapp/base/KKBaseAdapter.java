package com.likeit.currenciesapp.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public abstract class KKBaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    private ArrayList<T> datas=new ArrayList<>();

    public void addDatas(ArrayList<T> new_datas){
        if(new_datas!=null && new_datas.size()>0){
            datas.addAll(new_datas);
            notifyDataSetChanged();
        }
    }

    public void setDatas(ArrayList<T> new_datas){
        if(new_datas!=null && new_datas.size()>0){
            datas.clear();
            datas=new_datas;
            notifyDataSetChanged();
        }
    }

    public void cleanDatas(){
        if(datas!=null){
            datas.clear();
            notifyDataSetChanged();
        }
    }
    public ArrayList<T> getDatas(){
        if(datas==null){
            datas=new ArrayList<>();
        }
        return datas;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.onBind(datas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
