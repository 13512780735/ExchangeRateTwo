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

public class ModifyUserInfoDialog extends Dialog {


    OnClickListener onClickListener;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.modify_butt)
    TextView modifyButt;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ModifyUserInfoDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
        setContentView(R.layout.dialog_modify_userinfo);
        setCancelable(false);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.modify_butt, R.id.left_butt})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.modify_butt:
                String name=nameEt.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(nameEt.getContext(),"請輸入你的姓名",Toast.LENGTH_SHORT).show();
                    return;
                }
                onClickListener.onRightClick(name);
                break;
            case R.id.left_butt:
                dismiss();
                onClickListener.onLeftClick();
                break;
        }
    }

    public interface OnClickListener {
        void onRightClick(String name);

        void onLeftClick();
    }
}
