package com.likeit.currenciesapp.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.activity.WebActivity;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.entity.NoticeInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.likeit.currenciesapp.network.api_service.MyApiService.BASE_URL;


public class NoticViewHolder extends BaseViewHolder<NoticeInfoEntity> {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.time_tv)
    TextView timeTv;

    public NoticViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onBind(final NoticeInfoEntity noticeInfoEntity, int position) {
        titleTv.setText(noticeInfoEntity.getCntitle());
        timeTv.setText(noticeInfoEntity.getAddtime());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.WEB_TITLE,noticeInfoEntity.getCntitle());
                intent.putExtra(WebActivity.WEB_URL,BASE_URL+"?&m=index&a=notice_detail&id="+noticeInfoEntity.getId());
                context.startActivity(intent);
            }
        });

    }
}
