package com.likeit.currenciesapp.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.dialogs.ModifyUserInfoDialog;
import com.likeit.currenciesapp.dialogs.TipsDialog;
import com.likeit.currenciesapp.entity.UserInfoEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.api_service.MyApiService;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.rxbus.CloseAppEvent;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.ui.GlideCircleTransform;
import com.likeit.currenciesapp.utils.FileUtil;
import com.likeit.currenciesapp.utils.PreferencesUtil;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.beta.Beta;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserInfoActivity extends BaseActivity {

    private static final int SELECT_ORIGINAL_PIC = 101;
    private static final int SELECT_PIC = 102;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.user_account_tv)
    TextView userAccountTv;
    @BindView(R.id.apply_date_tv)
    TextView applyDateTv;
    @BindView(R.id.last_login_date_tv)
    TextView lastLoginDateTv;
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.update_layout)
    LinearLayout updateLayout;
    @BindView(R.id.exit_layout)
    TextView exitLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        initTopBar("用戶信息");
        versionTv.setText(getVersion());
    }


    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V" + version;
        } catch (Exception e) {
            return "";
        }
    }

    private void getUserInfo() {
        HttpMethods.getInstance().getUserInfo(new MySubscriber<UserInfoEntity>(this) {

            @Override
            public void onHttpCompleted(HttpResult<UserInfoEntity> httpResult) {
                if (httpResult.isStatus()) {
                    String headUrl = MyApiService.IMG_BASE_URL + httpResult.getInfo().getPic();
                    Glide.with(context).load(headUrl)
                            .bitmapTransform(new GlideCircleTransform(context))
                            .error(getResources().getDrawable(R.mipmap.default_user_head))
                            .into(headImg);

                    nameTv.setText(httpResult.getInfo().getTruename());
                    userAccountTv.setText(httpResult.getInfo().getUser_name());
                    applyDateTv.setText("申請日期:" + httpResult.getInfo().getAddtime());
                    lastLoginDateTv.setText("最後登錄:" + httpResult.getInfo().getLogintime());
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        }, uKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @OnClick({R.id.modify_date_layout, R.id.head_img, R.id.modify_passwd_layout, R.id.exit_layout,
            R.id.update_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_layout:
                Beta.checkUpgrade();
                break;
            case R.id.modify_date_layout:
                final ModifyUserInfoDialog modifyUserDialog = new ModifyUserInfoDialog(context);
                modifyUserDialog.setOnClickListener(new ModifyUserInfoDialog.OnClickListener() {
                    @Override
                    public void onRightClick(String name) {
                        HttpMethods.getInstance().modifyPasswd(new MySubscriber<EmptyEntity>(UserInfoActivity.this) {
                            @Override
                            public void onHttpCompleted(HttpResult httpResult) {
                                if (httpResult.isStatus()) {
                                    modifyUserDialog.dismiss();
                                    showToast("修改成功");
                                    getUserInfo();
                                } else {
                                    showToast(httpResult.getMsg());
                                }
                            }

                            @Override
                            public void onHttpError(Throwable e) {

                            }

                        }, uKey, name, "", "");
                    }

                    @Override
                    public void onLeftClick() {

                    }
                });

                modifyUserDialog.show();
                break;
            case R.id.modify_passwd_layout:
                toActivity(ModifyPasswdActivity.class);
//                toActivity(UmengChannelActivity.class);
                break;
            case R.id.head_img:
                selectFromGallery();
//                cropFromGallery();
                break;
            case R.id.exit_layout:
                exit();
                break;
        }
    }

    private void exit() {
        TipsDialog tipsDialog = new TipsDialog(context);
        tipsDialog.setTips("確定要退出APP嗎？");
        tipsDialog.setLeftButt("取消");
        tipsDialog.setRightButt("退出");
        tipsDialog.setOnClickListener(new TipsDialog.OnClickListener() {
            @Override
            public void onRightClick() {
                PreferencesUtil.putBooleanValue(PreferConfigs.isLogin, false);
                toFinish();
                Im_Logout();
                RxBus.get().post(new CloseAppEvent());
            }

            @Override
            public void onLeftClick() {

            }
        });
        tipsDialog.show();
    }


    /**
     * 从相册选择原生的照片（不裁切）
     */
    private Uri imageUri;

    private void selectFromGallery() {
        //初始化imageUri
//        imageUri = Uri.fromFile(new File(UserInfoActivity.this.getExternalCacheDir(), "test.jpg"));
        Intent selPicIntent = new Intent();
        selPicIntent.setType("image/*");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            selPicIntent.setAction(Intent.ACTION_PICK);
        } else {
            selPicIntent.setAction(Intent.ACTION_GET_CONTENT);
        }
        startActivityForResult(selPicIntent, SELECT_ORIGINAL_PIC);
    }


    /**
     * 从相册选择照片进行裁剪
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_ORIGINAL_PIC:
                    Uri selectedImage = data.getData();
                    imageUri = FileUtil.getOutPutPicFileUri();
                    if (!TextUtils.isEmpty(selectedImage.toString())) {
                        UCrop.of(selectedImage, imageUri)
                                .withAspectRatio(1, 1)
                                .withMaxResultSize(200, 200)
                                .start(UserInfoActivity.this);
                    }

                    break;
                case UCrop.REQUEST_CROP:
                    Uri resultUri = UCrop.getOutput(data);
                    Logger.d("resultUri :" + resultUri.toString());
                    uploadFileBase64(resultUri);
                    break;
            }
        } else {
            if (resultCode == UCrop.RESULT_ERROR) {
                Logger.d("Ucrp Error :" + UCrop.getError(data).getMessage());
                showToast("選擇其他相片試試");
            } else {
                showToast("系統繁忙，請稍後再試");
            }

        }

    }

    private void uploadFileBase64(Uri resultUri) {
        if (TextUtils.isEmpty(resultUri.toString())) {
            showToast("圖片不存在");
            return;
        }
        String filePath = FileUtil.getPath(context, resultUri);
        if (filePath == null || TextUtils.isEmpty(filePath)) {
            showToast("圖片不存在");
            return;
        }
        Logger.d("filePath  :" + filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            showToast("圖片不存在");
            return;
        }

        try {
            String base64Token = Base64.encodeToString(FileUtil.getFileToByte(file), Base64.DEFAULT);//  编码后
            Logger.d("base64Token  start");
            Logger.d("base64Token  :" + base64Token);
            Logger.d("base64Token  end");
            HttpMethods.getInstance().uploadFileBase64(new MySubscriber<EmptyEntity>(this) {

                @Override
                public void onHttpCompleted(HttpResult<EmptyEntity> httpResult) {
                    if (httpResult.isStatus()) {
                        showToast("上傳成功");
                        getUserInfo();
                    } else {
                        showToast(httpResult.getMsg());
                    }
                }

                @Override
                public void onHttpError(Throwable e) {

                }
            }, uKey, base64Token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
