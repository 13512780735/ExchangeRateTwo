package com.likeit.currenciesapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.meituan.android.walle.WalleChannelReader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UmengChannelActivity extends BaseActivity {

    @BindView(R.id.umeng_channel_tv)
    TextView umengChannelTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng_channel);
        ButterKnife.bind(this);
        initUmengInfo();
    }

    private void initUmengInfo(){
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        umengChannelTv.setText(""+channel);
    }
}
