package experiment.resourcepool;

import org.apache.log4j.Logger;

public class MemoryLeakResourcePoolWorkers extends ResourcePoolWorkers {
	
	private static final Logger log = Logger.getLogger(MemoryLeakResourcePoolWorkers.class);
	
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
		
		Thread resourceAdder = new Thread(new ResourceAdder("resourceAdder"), "resourceAdder");
		resourceAdder.start();
		
		Thread resourceAdder2 = new Thread(new ResourceAdder("resourceAdder2"), "resourceAdder2");
		resourceAdder2.start();
		
		Thread resourceAdder3 = new Thread(new ResourceAdder("resourceAdder3"), "resourceAdder3");
		resourceAdder3.start();
		
		Thread resourceAdder4 = new Thread(new ResourceAdder("resourceAdder4"), "resourceAdder4");
		resourceAdder4.start();
		
		Thread resourceAdder5 = new Thread(new ResourceAdder("resourceAdder5"), "resourceAdder5");
		resourceAdder5.start();
		
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
		MemoryLeakResourcePoolWorkers workers = new MemoryLeakResourcePoolWorkers();
		workers.start();
	}

	// inner classes
	
	protected class ResourceAdder extends DefaultWorker {
		
		public ResourceAdder(String name) {
			super(name);
			loopCount = 10000;
		}
		
		// sleep shorter time than inherited sleep time
		public int getSleepTime() {
			int sleepTime = Math.abs(random.nextInt() % 10);
			return sleepTime;
		}

		protected void doTask() {
			try {
				resourcePool.add(new ResourcePool.Resource());
				Thread.sleep(getSleepTime());	// sleep 1 sec
			} catch (Exception e) {}
		}
		
	}
}
