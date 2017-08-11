package com.likeit.currenciesapp.subscriber;

import com.likeit.currenciesapp.base.BaseActivity;
import com.likeit.currenciesapp.listeners.MyObserver;
import com.likeit.currenciesapp.network.entity.HttpResult;
import com.orhanobut.logger.Logger;

import rx.Subscriber;



public  abstract class MySubscriber<T> extends Subscriber<HttpResult<T>> implements MyObserver<HttpResult<T>> {

    private BaseActivity baseActivity;
    public MySubscriber(BaseActivity baseActivity){
        this.baseActivity=baseActivity;
    }



    @Override
    public void onStart() {
        super.onStart();
        if(baseActivity!=null){
            baseActivity.LoaddingShow();
        }
    }

    @Override
    public void onCompleted() {
        if(baseActivity!=null){
            baseActivity.LoaddingDismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e("出錯原因 e  :"+e.getMessage());
        if(baseActivity!=null){
            baseActivity.LoaddingDismiss();
            baseActivity.showToast("服務器開小差,請稍後再試!");
        }
        onHttpError(e);
    }

    @Override
    public void onNext(HttpResult<T> httpResult) {
        if(baseActivity!=null){
            baseActivity.LoaddingDismiss();
        }

        onHttpCompleted(httpResult);
    }
}
