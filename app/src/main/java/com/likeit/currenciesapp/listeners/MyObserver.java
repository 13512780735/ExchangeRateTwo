package com.likeit.currenciesapp.listeners;


public interface MyObserver<T> {

        void onHttpCompleted(T t);
        void onHttpError(Throwable e);

}
