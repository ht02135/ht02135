package pattern.callback;

public class CallBackImpl implements ICallBack {

    public void callbackMethod(String result) {
        System.out.println("result="+result);
    }
}
