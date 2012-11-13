package pattern.callback;


public class Caller {
    private ICallBack callBack = null;
    
    public Caller() {}

    /*
    1>only one callback vs observers in observer pattern
    2>notion of registering callback vs add observer
    */
    public Caller(ICallBack callBack) {
        this.callBack = callBack;
    }
    
    public void execute(final String data) {
        // simulate asynch callback
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("processed data="+data);
                callBack.callbackMethod(data+"-processed");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
