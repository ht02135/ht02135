package com.hung.mockito;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class CallBackMockTest {
    
    // http://mockito.googlecode.com/svn/branches/1.7/javadoc/org/mockito/Mockito.html#doNothing%28%29
    
    private Caller caller = null;
    
    @MockitoAnnotations.Mock
    private ICallBack callBackMock;
    
    private int expectedCallBacks = 0;
    
    @Before
    public void setUp() { 
        expectedCallBacks = 0;
        MockitoAnnotations.initMocks(this); // Initializes objects annotated with @Mock
        caller = new Caller(callBackMock);
    }
    
    /*
     using answer to handle callback
     1>http://stackoverflow.com/questions/2684630/mockito-how-to-make-a-method-return-an-argument-that-was-passed-to-it
       other use for Answer.  interesting
     */
    @Test
    public void testCallBack() {
        
        // mock up the first callback to callBackMock:
        Mockito.doAnswer(new Answer<Void>(){
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String result = ((String) invocation.getArguments()[0]);
                expectedCallBacks++;
                System.out.println(">>>>> called back 1st time result="+result+", expectedCallBacks="+expectedCallBacks+"<<<<<");
                return null;    // answer must return void or null
            }
        }).when(callBackMock).callbackMethod("data-processed");
        
        caller.execute("data");
        caller.sleep(1000);
        
        System.out.println(">>>>> expectedCallBacks="+expectedCallBacks+" <<<<<");
        Assert.assertEquals(1,expectedCallBacks);   // should be called back only once
        
        // verify behavior
        Mockito.verify(callBackMock).callbackMethod("data-processed");
    }
    
    // do nothing for callback
    @Test
    public void testCallBackDoNothing() {
        try {
            // stubbing
            Mockito.doNothing().when(callBackMock).callbackMethod("data-processed");
            
            caller.execute("data");
            caller.sleep(1000);
            
            // verify behavior
            Mockito.verify(callBackMock).callbackMethod("data-processed");
        } catch (Exception e) {}
    }
    
    ///// Nested Class /////////////////////////////////////////////////////////////////////
    
    private interface ICallBack {
        // method that return something
        public void callbackMethod(String result);
    }
    
    private class CallBackImpl implements ICallBack {

        public void callbackMethod(String result) {
            System.out.println("result="+result);
        }
    }
    
    private class Caller {
        private ICallBack callBack = null;

        public Caller(ICallBack callBack) {
            this.callBack = callBack;
        }
        
        public void execute(final String data) {
            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println("processed data="+data);
                    callBack.callbackMethod(data+"-processed"); // asynch callback
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        
        public void sleep(long milli) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
        }
    }
}
