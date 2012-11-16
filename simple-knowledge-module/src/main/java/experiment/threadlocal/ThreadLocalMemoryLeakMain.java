package experiment.threadlocal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import experiment.threadlocal.CalendarPoolFactory.MyGregorianCalendar;

public class ThreadLocalMemoryLeakMain {
	private static final Logger log = Logger.getLogger(ThreadLocalMemoryLeakMain.class);
	
	public static void sleep() {
		try {
			Thread.sleep(10);	// sleep in millis, 1000millis=1sec, 60000millis=1min
		} catch (InterruptedException e) {
		}
	}
	
	public static void main(String[] args) {
		List<Thread> threadPool = new ArrayList<Thread>();
		
		// perform leak
		for (int i=0;i<1000;i++) {
			String workerName = "DisplayCalendarWorker" + i;
			Thread t = new Thread(new DisplayCalendarWorker(workerName), workerName);
			t.start();
			sleep();	// pace the thread creation speed
		}
		
		// pool, no leak, but performance drag
		for (int i=0;i<10;i++) {
			CalendarPoolFactory.getInstance().add(new MyGregorianCalendar());
		}
		CalendarPoolFactory.getInstance().open();
		for (int i=0;i<1000;i++) {
			String workerName = "DisplayPoolCalendarWorker" + i;
			Thread t = new Thread(new DisplayPoolCalendarWorker(workerName), workerName);
			t.start();
			sleep();	// pace the thread creation speed
		}
		
		try {
			log.info("enter 2min sleep");
			Thread.sleep(240000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			log.info("exit 2min sleep");
		} catch (InterruptedException e) {
		}	
	}
	
	// -------- DisplayCalendarWorker ------------------------------------------------------------
	
	public static abstract class DefaultCalendarWorker implements Runnable {
		protected String name ="";
		
		protected DefaultCalendarWorker(String name) {
			this.name = name;
		}
		
		public void sleep() {
			try {
				Thread.sleep(240000);	// sleep in millis, 1000millis=1sec, 60000millis=1min
			} catch (InterruptedException e) {
			}
		}
	}
	
	// leak
	public static class DisplayCalendarWorker extends DefaultCalendarWorker {
		
		public DisplayCalendarWorker(String name) {
			super(name);
		}
		
		public void run() {
			// leak, 1000 calendar for 1000 thread
			Calendar calendar = CalendarFactory.getInstance().get();
			String date = String.format("%d/%d/%d", calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR));
		    System.out.println(name + " : run - date="+date);
		    sleep();	// dont want it die right away, keep alive
		}
	}
	
	// -------- DisplayPoolCalendarWorker -----------------------------------------------------------
	
	// pool, no leak, but performance drag
	public static class DisplayPoolCalendarWorker extends DefaultCalendarWorker {
		
		public DisplayPoolCalendarWorker(String name) {
			super(name);
		}
		
		public void run() {
			MyGregorianCalendar calendar = CalendarPoolFactory.getInstance().get();
			String date = String.format("%d/%d/%d", calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR));
			System.out.println(name + " : run - date="+date);
		    CalendarPoolFactory.getInstance().release(calendar);
		    sleep();	// dont want it die right away, keep alive
		}
	}
}
