package com.hung.callback;

import java.util.ArrayList;
import java.util.List;

import com.hung.observer.IObserver;

public class Caller {
    private ICallBack callBack = null;
    
    public Caller() {}

    /*
    1>only one callback vs observers in observer pattern
    2>notion of registering callback vs add observer
    */
    public Caller(ICallBack callBack) {
        registerCallBack(callBack);
    }
    
    private void registerCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }
    
    public void notifyCallBack() {
        callBack.callbackMethod("method result");
    }
    
    public void dummyMethod() {
        // something happened, signal call back
        notifyCallBack();
    }
    
    public void doWork () {
        // something happened, signal call back
        notifyCallBack();
    }
}
