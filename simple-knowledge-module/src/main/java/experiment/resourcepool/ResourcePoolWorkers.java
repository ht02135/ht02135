package experiment.resourcepool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ResourcePoolWorkers {
	
	private static final Logger log = Logger.getLogger(ResourcePoolWorkers.class);
	
	protected IResourcePool resourcePool;
	protected Random random = new Random();
	
	public void start() {
		log.info("start : enter");
		
		resourcePool = new ResourcePool();
		
		// add 5 resources
		resourcePool.add(new ResourcePool.Resource());
		resourcePool.add(new ResourcePool.Resource());
		resourcePool.add(new ResourcePool.Resource());
		resourcePool.add(new ResourcePool.Resource());
		resourcePool.add(new ResourcePool.Resource());
		
		resourcePool.open();
		
		Thread worker = new Thread(new ResourceWorker("worker"), "worker");
		worker.start();
		
		Thread worker2 = new Thread(new ResourceWorker("worker2"), "worker2");
		worker2.start();
		
		Thread worker3 = new Thread(new ResourceWorker("worker3"), "worker3");
		worker3.start();
		
		Thread slacker = new Thread(new ResourceSlacker(), "slacker");
		slacker.start();
		
		Thread replacer = new Thread(new ResourceReplacer(), "replacer");
		replacer.start();
		
		Thread replacerNow = new Thread(new ResourceReplacerNow(), "replacerNow");
		replacerNow.start();
		
		try {
			log.info("enter 2min sleep");
			Thread.sleep(120000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			log.info("exit 2min sleep");
		} catch (InterruptedException e) {
		}	
		
		resourcePool.close();
		
		log.info("start : end");
	}
	
	public static void main( String[] args ) {
		ResourcePoolWorkers workers = new ResourcePoolWorkers();
		workers.start();
	}

	// ------------ inner classes ------------------------------------------------------
	
	protected abstract class AbstractWorker implements Runnable {
		
		protected int loopCount = 100;
		
		public void run() {
			for (int i=0;i<loopCount;i++) {
				doTask();
			}
		}
		
		public int getSleepTime() {
			int sleepTime = Math.abs(random.nextInt() % 1000);	// sleep within 1000milli=1s
			// log.info("sleepTime="+sleepTime);
			return sleepTime;
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
	
	// ------------------------------------------------------------------
	
	protected class ResourceWorker extends DefaultWorker {
		
		public ResourceWorker(String name) {
			super(name);
		}
		
		protected void doTask() {
			try {
				IResource resource = resourcePool.acquire();
				Thread.sleep(getSleepTime());	// sleep 1 sec => do task for 1 sec
				resourcePool.release(resource);
			} catch (Exception e) {}
		}
	}
	
	// work if only if can get resource
	protected class ResourceSlacker extends DefaultWorker {

		protected void doTask() {
			try {
				IResource resource = resourcePool.acquire(1000, TimeUnit.MILLISECONDS);
				if (resource != null) {
					Thread.sleep(getSleepTime());	// sleep 1 sec
					resourcePool.release(resource);
				} else {
					Thread.sleep(getSleepTime());	// sleep 1 sec
				}
			} catch (Exception e) {}
		}
		
	}
	
	protected class ResourceReplacer extends DefaultWorker {

		protected void doTask() {
			try {
				IResource resource = resourcePool.acquire();
				Thread.sleep(getSleepTime() % 10);
				resourcePool.release(resource);
				Thread.sleep(getSleepTime() % 10);
				resourcePool.remove(resource);
				Thread.sleep(getSleepTime() % 10);
				resourcePool.add(new ResourcePool.Resource());
				Thread.sleep(getSleepTime());	// sleep 1 sec
			} catch (Exception e) {}
		}
		
	}
	
	protected class ResourceReplacerNow extends DefaultWorker {
		
		protected void doTask() {
			try {
				IResource resource = resourcePool.acquire();
				Thread.sleep(getSleepTime() % 10);
				resourcePool.removeNow(resource);	// release + remove
				Thread.sleep(getSleepTime() % 10);
				resourcePool.add(new ResourcePool.Resource());
				Thread.sleep(getSleepTime());	// sleep 1 sec
			} catch (Exception e) {}
		}
		
	}
}


