package experiment.deadlock;

import java.util.Random;

import org.apache.log4j.Logger;

public class DeadLockMain {
	
	private static final Logger log = Logger.getLogger(DeadLockMain.class);

	private Object cacheLock = new Object();
	private Object tableLock = new Object();
	private Random random = new Random();
	
	public void start() {
		log.info("start : enter");
		
		Thread oneMethodWorker = new Thread(new OneMethodWorker(), "OneMethodWorker");
		oneMethodWorker.start();
		
		Thread anotherMethodWorker = new Thread(new AnotherMethodWorker(), "AnotherMethodWorker");
		anotherMethodWorker.start();
		
		Thread oneThenAnotherMethodWorker = new Thread(new OneThenAnotherMethodWorker(), "OneThenAnotherMethodWorker");
		oneThenAnotherMethodWorker.start();
		
		Thread anotherThenOneMethodWorker = new Thread(new AnotherThenOneMethodWorker(), "AnotherThenOneMethodWorker");
		anotherThenOneMethodWorker.start();
		
		
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

	// -------------- inner classes -------------------------------------------------------------
	
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
	
	// ---------------------------------------------------------------------------
	
	protected class OneMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				oneMethod();
			} catch (Exception e) {}
		}
	}
	
	protected class AnotherMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				anotherMethod();
			} catch (Exception e) {}
		}
	}
	
	protected class OneThenAnotherMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				oneMethod();
				Thread.sleep(getSleepTime());
				anotherMethod();
			} catch (Exception e) {}
		}
	}
	
	protected class AnotherThenOneMethodWorker extends DefaultWorker {
		
		protected void doTask() {
			try {
				anotherMethod();
				Thread.sleep(getSleepTime());
				oneMethod();
				
			} catch (Exception e) {}
		}
	}
}
