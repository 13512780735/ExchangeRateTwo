package com.likeit.currenciesapp.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.currenciesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderNoticDialog extends Dialog {


    OnClickListener onClickListener;
    @BindView(R.id.notice_hui_name_et)
    EditText noticeHuiNameEt;
    @BindView(R.id.notice_hui_bank_et)
    EditText noticeHuiBankEt;
    @BindView(R.id.notice_hui_other_et)
    EditText noticeHuiOtherEt;
    @BindView(R.id.left_butt)
    TextView leftButt;
    @BindView(R.id.ok_butt)
    TextView okButt;
    private Context context;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OrderNoticDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        this.context=context;
        setContentView(R.layout.dialog_order_notic);
        setCancelable(false);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.left_butt, R.id.ok_butt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_butt:
                dismiss();
                break;
            case R.id.ok_butt:
                String name = noticeHuiNameEt.getText().toString().trim();
                String bank = noticeHuiBankEt.getText().toString().trim();
                String other = noticeHuiOtherEt.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "請輸入完整信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                onClickListener.onRightClick(name,bank,other);
                break;
        }
    }

    public interface OnClickListener {
        void onRightClick(String name, String bank, String num);

        void onLeftClick();
    }
}
