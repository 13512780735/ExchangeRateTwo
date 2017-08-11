package com.likeit.currenciesapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.activity.ServiceChatActivity;
import com.likeit.currenciesapp.app.MyApplication;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.base.BaseViewHolder;
import com.likeit.currenciesapp.configs.EaseConstant;
import com.likeit.currenciesapp.entity.KeFuEntity;
import com.likeit.currenciesapp.network.api_service.MyApiService;
import com.likeit.currenciesapp.ui.GlideCircleTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.UserInfo;


public class KeFuViewHolder extends BaseViewHolder<KeFuEntity> {
    @BindView(R.id.img_tv)
    ImageView imgTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    BaseActivity baseActivity;

    public void setBaseActivity(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public KeFuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBind(final KeFuEntity keFuEntity, int position) {
        nameTv.setText(keFuEntity.getTruename());

        if(RongUserInfoManager.getInstance().getUserInfo(keFuEntity.getRongcloud())==null){
            UserInfo userInfo=new UserInfo(keFuEntity.getRongcloud()
                    ,keFuEntity.getTruename()
                    , Uri.parse(MyApiService.IMG_BASE_URL+keFuEntity.getPic()));
            RongIM.getInstance().refreshUserInfoCache(userInfo);
        }

        String headUrl = MyApiService.IMG_BASE_URL + keFuEntity.getPic();
        Glide.with(context).load(headUrl)
                .bitmapTransform(new GlideCircleTransform(context))
                .error(context.getResources().getDrawable(R.mipmap.default_user_head))
                .into(imgTv);

        itemView.setTag(R.id.kk_order_id,keFuEntity.getRongcloud());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ServiceChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, (String) itemView.getTag(R.id.kk_order_id));
                context.startActivity(intent);

            }
        });
    }


}
