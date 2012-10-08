package com.hung.callback;

public class CallBackImpl implements ICallBack {

    public void callbackMethod(String methodResult) {
        System.out.println("I've been called back to handle methodResult="+methodResult);
    }
}
