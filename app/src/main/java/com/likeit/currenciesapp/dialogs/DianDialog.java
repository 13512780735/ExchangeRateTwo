package com.likeit.currenciesapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.entity.DianInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DianDialog extends Dialog {

    OnClickListener onClickListener;
    @BindView(R.id.year_rate_tv)
    TextView yearRateTv;
    @BindView(R.id.totalr_dian_tv)
    TextView totalrDianTv;
    @BindView(R.id.yesterday_rate_tv)
    TextView yesterdayRateTv;
    @BindView(R.id.total_rate_tv)
    TextView totalRateTv;
    @BindView(R.id.buy_butt)
    TextView buyButt;
    @BindView(R.id.get_butt)
    TextView getButt;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DianDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        setContentView(R.layout.dialog_dian);
        setCancelable(true);
        ButterKnife.bind(this);
    }

    public void initDianInfo(DianInfoEntity dianInfoEntity){
        yearRateTv.setText(dianInfoEntity.getNian());
        totalrDianTv.setText(dianInfoEntity.getDianshu());
        yesterdayRateTv.setText(dianInfoEntity.getY_dianshu());
        totalRateTv.setText(dianInfoEntity.getTotal());

    }

    @OnClick({R.id.buy_butt, R.id.get_butt})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.buy_butt:
                onClickListener.onBuy();
                break;
            case R.id.get_butt:
                onClickListener.onGet();
                break;
        }
    }


    public interface OnClickListener {
        void onBuy();
        void onGet();
    }
}
