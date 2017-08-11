package com.likeit.currenciesapp.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.likeit.currenciesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TipsDialog extends Dialog {

    @BindView(R.id.tips_tv)
    TextView tipsTv;
    @BindView(R.id.left_butt)
    TextView leftButt;
    @BindView(R.id.right_butt)
    TextView rightButt;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public TipsDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        setContentView(R.layout.dialog_tips);
        setCancelable(false);
        ButterKnife.bind(this);
    }

    public void setTips(String tips){
        tipsTv.setText(tips);
    }

    public void setRightButt(String right_){
        rightButt.setText(right_);
    }

    public void setLeftButt(String left_){
        leftButt.setText(left_);
    }

    public void sigleButt(){
        leftButt.setVisibility(View.GONE);
    }

    @OnClick({R.id.left_butt, R.id.right_butt})
    public void onClick(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.left_butt:
                onClickListener.onLeftClick();
                break;
            case R.id.right_butt:
                onClickListener.onRightClick();
                break;
        }
    }

   public interface OnClickListener{
        void onRightClick();
        void onLeftClick();
    }
}
