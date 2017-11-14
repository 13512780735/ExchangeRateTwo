package com.likeit.currenciesapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.entity.RegisterSourceEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class RegisterSourceAdapter extends MyBaseAdapter<RegisterSourceEntity> {

    public RegisterSourceAdapter(Context context, List<RegisterSourceEntity> registerSourceEntities) {
        super(context, registerSourceEntities);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getInflater().inflate(
                    R.layout.register_source_listview_items, parent, false);
            holder.sourceName = (TextView) convertView
                    .findViewById(R.id.register_source_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RegisterSourceEntity data1 = getItem(position);
        holder.sourceName.setText(data1.getName());
        return convertView;
    }

    private class ViewHolder {
        TextView sourceName;

    }
}
