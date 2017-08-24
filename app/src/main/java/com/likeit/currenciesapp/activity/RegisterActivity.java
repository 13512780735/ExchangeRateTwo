package com.likeit.currenciesapp.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.likeit.currenciesapp.R;
import com.likeit.currenciesapp.adapter.RegisterSourceAdapter;
import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.configs.APIComm;
import com.likeit.currenciesapp.configs.Constant;
import com.likeit.currenciesapp.entity.RegisterSourceEntity;
import com.likeit.currenciesapp.network.HttpMethods;
import com.likeit.currenciesapp.network.api_service.MyApiService;
import com.likeit.currenciesapp.network.entity.EmptyEntity;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.likeit.currenciesapp.subscriber.MySubscriber;
import com.likeit.currenciesapp.utils.HttpUtil;
import com.likeit.currenciesapp.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone_check_et)
    EditText phoneCheckEt;
    @BindView(R.id.get_check_tv)
    TextView getCheckTv;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.passwd_et)
    EditText passwdEt;
    @BindView(R.id.re_passwd_et)
    EditText rePasswdEt;
    @BindView(R.id.checkbox_cb)
    CheckBox checkboxCb;
    @BindView(R.id.registed_btn)
    TextView registedBtn;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.rl_source)
    LinearLayout llSource;
    @BindView(R.id.ll_introducer)
    LinearLayout llIntroducer;
    @BindView(R.id.re_source_tv)
    EditText tvSource;
    @BindView(R.id.re_introducer_name_et)
    EditText edSourceName;
    @BindView(R.id.re_introducer_phone_et)
    EditText edSourcePhone;

    private final static int TIME = 101;
    @BindView(R.id.protocol_tv)
    TextView protocolTv;
    private int time_tatol = 60;
    private PopupWindow popupWindow;
    private List<RegisterSourceEntity> dataSource;
    private View view;
    private ListView lv_group;
    private RegisterSourceAdapter groupAdapter;


    Handler myHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME:
                    if (!isFinishing()) {
                        time_tatol--;
                        getCheckTv.setText(time_tatol + "秒后刷新");
                        if (time_tatol <= 0) {
                            getCheckTv.setText("獲取驗證碼");
                            getCheckTv.setEnabled(true);
                            getCheckTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_right_round_phone_check_));
                        }
                        myHanlder.sendEmptyMessageDelayed(TIME, 1000);
                    }
                    break;
            }
        }
    };
    private String sourceName;
    private String sourcePhone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        dataSource = new ArrayList<RegisterSourceEntity>();
        initSource();
        initTopBar("註冊");

    }

    private void initSource() {
        String url = MyApiService.RegisterSource;
        RequestParams params = new RequestParams();
        HttpUtil.post(url, params, new HttpUtil.RequestListener() {
            @Override
            public void success(String response) {
                Log.d("TAG", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String code = obj.optString("code");
                    String message = obj.optString("message");
                    if ("0".equals(code)) {
                        JSONArray array = obj.optJSONArray("info");
                        for (int i = 0; i < array.length(); i++) {
                            RegisterSourceEntity mRegisterSourceEntity = new RegisterSourceEntity();
                            JSONObject object = array.optJSONObject(i);
                            mRegisterSourceEntity.setId(object.optString("id"));
                            mRegisterSourceEntity.setAgid(object.optString("agid"));
                            mRegisterSourceEntity.setName(object.optString("name"));
                            dataSource.add(mRegisterSourceEntity);
                        }
                        groupAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failed(Throwable e) {

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @OnClick({R.id.get_check_tv, R.id.registed_btn, R.id.protocol_tv, R.id.rl_source})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.protocol_tv:
                Bundle bundle = new Bundle();
                bundle.putString(WebActivity.WEB_TITLE, "服務條款");
                bundle.putString(WebActivity.WEB_URL, "http://dtb.wbteam.cn/api.php?m=login&a=reg_detail");
                toActivity(WebActivity.class, bundle);
                break;
            case R.id.get_check_tv:
                getCheckPhone();
                break;
            case R.id.registed_btn:
                String name = nameEt.getText().toString().trim();
                String phoneCheck = phoneCheckEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();
                String passwd = passwdEt.getText().toString().trim();
                String source = tvSource.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneCheck) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(passwd)) {
                    showToast("請填寫完整信息");
                    return;
                }
                if (TextUtils.isEmpty(source)) {
                    showToast("請輸入註冊來源");
                    return;
                }

                if (!checkboxCb.isChecked()) {
                    showToast("請同意條款");
                    return;
                }
                if("介绍人".equals(source)){
                    sourceName=edSourceName.getText().toString().trim();
                    sourcePhone=edSourcePhone.getText().toString().trim();
                    if(TextUtils.isEmpty(sourceName)||TextUtils.isEmpty(sourcePhone)){
                        showToast("請填寫完整信息");
                        return;
                    }
                }

                HttpMethods.getInstance().registerUser(new MySubscriber<EmptyEntity>(this) {
                    @Override
                    public void onHttpCompleted(HttpResult httpResult) {
                        if (httpResult.isStatus()) {
                            showToast("註冊成功");
                            toFinish();
                        } else {
                            showToast(httpResult.getMsg());
                        }
                    }

                    @Override
                    public void onHttpError(Throwable e) {

                    }

                }, getuicid, phone, passwd,phoneCheck, name,source,sourceName,sourcePhone);
                break;
            case R.id.rl_source:
                showPopup();
                break;
        }
    }

    private void showPopup() {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = layoutInflater.inflate(R.layout.operationinto_popmenulist, null);

            lv_group = (ListView) view.findViewById(R.id.menulist);
            groupAdapter = new RegisterSourceAdapter(this, dataSource);
            lv_group.setAdapter(groupAdapter);
            // 创建一个PopuWidow对象
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.filter_bg));
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        Log.i("coder", "xPos:" + xPos);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);// 动画效果必须放在showAsDropDown()方法上边，否则无效
        backgroundAlpha(0.5f);// 设置背景半透明
        popupWindow.showAsDropDown(tvSource);

        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
              // ToastUtil.showS(RegisterActivity.this, dataSource.get(position).getName());
                tvSource.setText(dataSource.get(position).getName());
                if (position == 0) {
                    llIntroducer.setVisibility(View.VISIBLE);
                } else {
                    llIntroducer.setVisibility(View.INVISIBLE);
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    backgroundAlpha(1.0f);// 当点击屏幕时，使半透明效果取消
                }
            }
        });
    }

    // 设置popupWindow背景半透明
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;// 0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void getCheckPhone() {
        String phoneNum = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            showToast("請輸入手機號碼");
            return;
        }


        HttpMethods.getInstance().getPhoneCheck(new MySubscriber<EmptyEntity>(this) {
            @Override
            public void onHttpCompleted(HttpResult<EmptyEntity> httpResult) {
                if (httpResult.isStatus()) {
                    showToast("驗證碼發送成功，稍後請留言你的短信!");
                    time_tatol = 60;
                    getCheckTv.setText(time_tatol + "秒后刷新");
                    getCheckTv.setEnabled(false);
                    getCheckTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_right_round_phone_unable_check_));
                    myHanlder.sendEmptyMessageDelayed(TIME, 1000);
                } else {
                    showToast(httpResult.getMsg());
                }
            }

            @Override
            public void onHttpError(Throwable e) {

            }
        }, phoneNum, 1);
    }
}
