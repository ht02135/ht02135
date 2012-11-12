package deadlock;

import java.util.Random;

import org.apache.log4j.Logger;

public class DeadLockMain {
	
	private static final Logger log = Logger.getLogger(DeadLockMain.class);

	private Object cacheLock = new Object();
	private Object tableLock = new Object();
	private Random random = new Random();
	
	/* 
		1> in jvisualvm, i see 2 threads mostly in sleeping and monitor (RED) state. 
		2>monitor state => the thread is in the Thread.State.BLOCKED
		  Monitor will mean the thread is waiting to attain a lock on an object. For example when one 
		  thread is running a synchronized method and another one tries to invoke it on the same object, 
		  it will not be able to until the first invocation of the method is finished. This is because 
		  the first thread has a monitor or lock on that object, so the second one must wait until it 
		  is released. 
		3>click on jconsole deadlock is useless (not working)
	*/
	
	public void start() {
		log.info("start : enter");
		
		Thread oneMethodWorker = new Thread(new OneMethodWorker(), "OneMethodWorker");
		oneMethodWorker.start();
		
		Thread anotherMethodWorker = new Thread(new AnotherMethodWorker(), "AnotherMethodWorker");
		anotherMethodWorker.start();
		
		try {
			log.info("enter 2min sleep");
			Thread.sleep(120000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			log.info("exit 2min sleep");
		} catch (InterruptedException e) {
		}	
		
		log.info("start : exit");
	}
	  
	public void oneMethod() {
		synchronized (cacheLock) {
			synchronized (tableLock) { 
				doSomething();
			}
		}
	}
	
	public void anotherMethod() {
		synchronized (tableLock) {
			synchronized (cacheLock) { 
				doSomethingElse();
			}
		}
	}
	
	public void doSomething() {
		try {
			Thread.sleep(getSleepTime());
		} catch (Exception e) {}
	}
	
	public void doSomethingElse() {
		try {
			Thread.sleep(getSleepTime());
		} catch (Exception e) {}
	}
	
	public int getSleepTime() {
		int sleepTime = Math.abs(random.nextInt() % 1000);	// sleep within 1000milli=1s
		return sleepTime;
	}
	
	public static void main( String[] args ) {
		DeadLockMain workers = new DeadLockMain();
		workers.start();
	}

	// inner classes
	
	protected abstract class AbstractWorker implements Runnable {
		
		protected int loopCount = 100;
		
		public void run() {
			for (int i=0;i<loopCount;i++) {
				doTask();
			}
		}
		
		protected abstract void doTask();
	}
	
	protected class DefaultWorker extends AbstractWorker {
		
		private String name;
		
		public DefaultWorker() {
			this.name = this.getClass().getName();
		}
		
		public DefaultWorker(String name) {
			this.name = name;
		}
		
		public void run() {
			log.info(name + " started");
			super.run();
			log.info(name + " is done");
		}
		
		protected void doTask() {}
	}
	
	protected class OneMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				oneMethod();
				Thread.sleep(getSleepTime());
			} catch (Exception e) {}
		}
	}
	
	protected class AnotherMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				anotherMethod();
				Thread.sleep(getSleepTime());
			} catch (Exception e) {}
		}
	}
}
