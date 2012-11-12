package resourcepool;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ResourcePoolWorkers {
	
	private static Logger log = Logger.getLogger(ResourcePoolWorkers.class);
	
	private static IResourcePool RESOURCE_POOL;
	
	private static Random random = new Random();
	
	public void start() {
		log.info("start : enter");
		
		RESOURCE_POOL = new ResourcePool();
		
		// add 5 resources
		RESOURCE_POOL.add(new ResourcePool.Resource());
		RESOURCE_POOL.add(new ResourcePool.Resource());
		RESOURCE_POOL.add(new ResourcePool.Resource());
		RESOURCE_POOL.add(new ResourcePool.Resource());
		RESOURCE_POOL.add(new ResourcePool.Resource());
		
		RESOURCE_POOL.open();
		
		Thread worker = new Thread(new ResourceWorker("worker"));
		worker.start();
		
		Thread worker2 = new Thread(new ResourceWorker("worker2"));
		worker2.start();
		
		Thread worker3 = new Thread(new ResourceWorker("worker3"));
		worker3.start();
		
		Thread slacker = new Thread(new ResourceSlacker());
		slacker.start();
		
		Thread replacer = new Thread(new ResourceReplacer());
		replacer.start();
		
		Thread replacerNow = new Thread(new ResourceReplacerNow());
		replacerNow.start();
		
		try {
			log.info("enter 5min sleep");
			Thread.sleep(180000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			log.info("exit 5min sleep");
		} catch (InterruptedException e) {
		}	
		
		RESOURCE_POOL.close();
		
		log.info("start : end");
	}
	
	public static void main( String[] args ) {
		ResourcePoolWorkers workers = new ResourcePoolWorkers();
		workers.start();
	}

	// inner classes
	
	private abstract class AbstractWorker implements Runnable {
		
		private int loopCount = 100;
		
		public void run() {
			for (int i=0;i<loopCount;i++) {
				doTask();
			}
		}
		
		public int getSleepTime() {
			int sleepTime = Math.abs(random.nextInt() % 1000);
			// log.info("sleepTime="+sleepTime);
			return sleepTime;
		}
		
		protected abstract void doTask();
	}
	
	private class ResourceWorker extends AbstractWorker {
		
		private String name;
		
		public ResourceWorker(String name) {
			this.name = name;
		}
		
		public void run() {
			log.info("ResourceWorker " + name + " started");
			super.run();
			log.info("ResourceWorker " + name + " is done");
		}
		
		protected void doTask() {
			try {
				IResource resource = RESOURCE_POOL.acquire();
				Thread.sleep(getSleepTime());	// sleep 1 sec => do task for 1 sec
				RESOURCE_POOL.release(resource);
			} catch (Exception e) {}
		}
	}
	
	// work if only if can get resource
	private class ResourceSlacker extends AbstractWorker {
		
		public void run() {
			log.info("ResourceSlacker started");
			super.run();
			log.info("ResourceSlacker is done");
		}

		protected void doTask() {
			try {
				IResource resource = RESOURCE_POOL.acquire(1000, TimeUnit.MILLISECONDS);
				if (resource != null) {
					Thread.sleep(getSleepTime());	// sleep 1 sec
					RESOURCE_POOL.release(resource);
				} else {
					Thread.sleep(getSleepTime());	// sleep 1 sec
				}
			} catch (Exception e) {}
		}
		
	}
	
	private class ResourceReplacer extends AbstractWorker {
		
		public void run() {
			log.info("ResourceReplacer started");
			super.run();
			log.info("ResourceReplacer is done");
		}

		protected void doTask() {
			try {
				IResource resource = RESOURCE_POOL.acquire();
				RESOURCE_POOL.release(resource);
				RESOURCE_POOL.remove(resource);
				RESOURCE_POOL.add(new ResourcePool.Resource());
				Thread.sleep(getSleepTime());	// sleep 1 sec
			} catch (Exception e) {}
		}
		
	}
	
	private class ResourceReplacerNow extends AbstractWorker {

		public void run() {
			log.info("ResourceReplacerNow started");
			super.run();
			log.info("ResourceReplacerNow is done");
		}
		
		protected void doTask() {
			try {
				IResource resource = RESOURCE_POOL.acquire();
				RESOURCE_POOL.removeNow(resource);
				RESOURCE_POOL.add(new ResourcePool.Resource());
				Thread.sleep(getSleepTime());	// sleep 1 sec
			} catch (Exception e) {}
		}
		
	}
}


