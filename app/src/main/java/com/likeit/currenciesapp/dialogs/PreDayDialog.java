package com.likeit.currenciesapp.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.entity.PreRateInfoEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreDayDialog extends Dialog {


    @BindView(R.id.pre_days_layout)
    LinearLayout preDaysLayout;
    @BindView(R.id.cancle_tv)
    TextView cancleTv;
    @BindView(R.id.ok_tv)
    TextView okTv;
    private Context context;
    private ArrayList<PreRateInfoEntity> preRateInfoEntities;
    private PreRateInfoEntity preRateInfoEntitySelected;

    public PreDayDialog(@NonNull Context context, ArrayList<PreRateInfoEntity> preRateInfoEntities) {
        super(context);

        setContentView(R.layout.dialog_pre_day);
        ButterKnife.bind(this);
        this.context=context;
        this.preRateInfoEntities = preRateInfoEntities;
        initView();
    }

    private void initView() {
        if(preRateInfoEntities==null || preRateInfoEntities.size()==0 ||preDaysLayout==null){
            return;
        }

       LayoutInflater layoutInflater= LayoutInflater.from(context);
        for (int i=0;i<preRateInfoEntities.size();i++){
            LinearLayout view = (LinearLayout) layoutInflater.inflate(R.layout.item_pre_days_select,null,false);
            TextView days_tv= (TextView) view.findViewById(R.id.pre_select_days_tv);
            TextView date_tv= (TextView) view.findViewById(R.id.pre_select_date_tv);
            TextView rate_tv= (TextView) view.findViewById(R.id.pre_select_rate_tv);
            CheckBox checkBox= (CheckBox) view.findViewById(R.id.pre_select_checkbox_cb);
            date_tv.setText(preRateInfoEntities.get(i).getDate());
            days_tv.setText(preRateInfoEntities.get(i).getDays());
            rate_tv.setText(String.valueOf(preRateInfoEntities.get(i).getZvs()));
            checkBox.setTag(i);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecking){
                        isChecking=true;
                       int index= (int) buttonView.getTag();
                        initSelectStatus(index);
                        preRateInfoEntitySelected=preRateInfoEntities.get(index);
                    }
                    isChecking=false;
                }
            });

            preDaysLayout.addView(view);


        }
    }


    boolean isChecking=false;
    private void initSelectStatus(int index) {
        for (int i=0;i<preDaysLayout.getChildCount();i++){
            if(index==i){
                continue;
            }
            View view=preDaysLayout.getChildAt(i);
            CheckBox checkBox= (CheckBox) view.findViewById(R.id.pre_select_checkbox_cb);
            checkBox.setChecked(false);
//            ((CheckBox)((LinearLayout)( (LinearLayout)preDaysLayout.getChildAt(i)).getChildAt(3)).getChildAt(0)).setChecked(false);
        }
    }

    @OnClick({R.id.cancle_tv, R.id.ok_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle_tv:
                dismiss();
                preDaySelectCallBackListener.onCancle();
                break;
            case R.id.ok_tv:
                if(preRateInfoEntitySelected==null){
                    Toast.makeText(context,"請選擇存放時間!",Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                preDaySelectCallBackListener.onSubmit(preRateInfoEntitySelected);
                break;
        }
    }


    private PreDaySelectCallBackListener preDaySelectCallBackListener;
    public void setPreDaySelectCallBackListener(PreDaySelectCallBackListener preDaySelectCallBackListener){
        this.preDaySelectCallBackListener=preDaySelectCallBackListener;
    }
    public interface PreDaySelectCallBackListener {
        void onCancle();
        void onSubmit(PreRateInfoEntity entity);
    }

}
