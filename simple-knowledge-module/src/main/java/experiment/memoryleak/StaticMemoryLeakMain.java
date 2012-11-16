package experiment.memoryleak;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;


public class StaticMemoryLeakMain {
	
	private static final Logger log = Logger.getLogger(StaticMemoryLeakMain.class);
	
	private static final Random random = new Random();

	public static void main(String[] args) {
		
		StaticMemoryLeakMain staticMemoryLeakMain = new StaticMemoryLeakMain();
		for (int i=0;i<5;i++) {
			staticMemoryLeakMain.launchWorkerDoWork();
			System.gc();
		}
		
		// main thread sleep 3min
		try {
			log.info("enter 2min sleep");
			Thread.sleep(180000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			log.info("exit 2min sleep");
		} catch (InterruptedException e) {
		}	
	}
	
	public void launchWorkerDoWork() {
		Thread taskWorker = new Thread(new TaskWorker());
		taskWorker.start();
	}
	
	public static class MemoryLeakMap {
		private static List<String> list = new LinkedList<String>();
		
		public static void add(String value) {
			list.add(value);
		}
		
		public static Iterator iterator() {
			return list.iterator();
		}
	}
	
	public static class TaskWorker implements Runnable {
		private List<String> list = new LinkedList<String>();
		
		public void run() {
			for (int i=0;i<1000000;i++) {
				addWork(String.valueOf(random.nextInt()));
			}
			doWork();
		}
		
		public void addWork(String value) {
			list.add(value);
			StaticMemoryLeakMain.MemoryLeakMap.add("MemoryLeakMap"+value);	// leak
			System.out.println("added value="+value);
			sleep();	// need to sleep for jvisualvm and eclipse memory analyzer to attach to
		}
		
		public void doWork() {
			Iterator<String> iter = list.iterator();
			while (iter.hasNext()) {
				System.out.println("done iter.next()="+iter.next());
			}
			
			Iterator<String> iter2 = StaticMemoryLeakMain.MemoryLeakMap.iterator();
			while (iter2.hasNext()) {
				System.out.println("deone iter2.next()="+iter2.next());
			}
			sleep();	// need to sleep for jvisualvm and eclipse memory analyzer to attach to
		}
		
		public void sleep() {
			try {
				Thread.sleep(10);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			} catch (InterruptedException e) {
			}
		}
	}
}
