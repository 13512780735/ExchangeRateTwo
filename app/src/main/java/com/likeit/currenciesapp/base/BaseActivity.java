package com.likeit.currenciesapp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.configs.PreferConfigs;
import com.likeit.currenciesapp.dialogs.LoaddingDialog;
import com.likeit.currenciesapp.runtimepermissions.PermissionsManager;
import com.likeit.currenciesapp.runtimepermissions.PermissionsResultAction;
import com.likeit.currenciesapp.utils.PreferencesUtil;

import io.rong.imkit.RongIM;


public class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected ImageView top_bar_back_img;
    protected TextView top_bar_title;
    protected String uKey = "";
    protected String getuicid = "";
    protected int page = 1;

    public LoaddingDialog loaddingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uKey = PreferencesUtil.getStringValue(PreferConfigs.uKey);
        getuicid = PreferencesUtil.getStringValue(PreferConfigs.GeTuiId);
        context = this;
        loaddingDialog = new LoaddingDialog(context);
        RxBus.get().register(this);
        requestPermissions();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        LoaddingDismiss();
    }


    public void LoaddingShow() {
        if (loaddingDialog == null) {
            loaddingDialog = new LoaddingDialog(context);
        }

        if (!loaddingDialog.isShowing()) {
            loaddingDialog.show();
        }
    }

    protected void Im_Logout() {
        RongIM.getInstance().disconnect();
    }

//    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void LoaddingDismiss() {
        if (loaddingDialog != null && loaddingDialog.isShowing()) {
            loaddingDialog.dismiss();
        }
    }

    protected void initTopBar(String title) {
        top_bar_back_img = (ImageView) findViewById(R.id.top_bar_back_img);
        top_bar_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFinish();
            }
        });
        top_bar_title = (TextView) findViewById(R.id.top_bar_title);
        top_bar_title.setText(title);
    }

    protected void toFinish() {
        finish();
    }

    public void toActivityFinish(Class activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
        toFinish();
    }

    public void toActivity(Class activity) {
        Intent intent = new Intent(context, activity);
        startActivity(intent);
    }

    public void toActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showToast(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
