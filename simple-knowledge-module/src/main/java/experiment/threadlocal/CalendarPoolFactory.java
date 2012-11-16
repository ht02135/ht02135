package experiment.threadlocal;

import java.util.Calendar;
import java.util.GregorianCalendar;

import experiment.resourcepool.IResource;
import experiment.resourcepool.IResourcePool;
import experiment.resourcepool.ResourcePool;

/*
	1>pool is good for expensive resource + not referenced frequently
	2>price to pay is 2 synchronization for this implementatin
 */

public class CalendarPoolFactory {
	private IResourcePool calendarPool = new ResourcePool();

	private static CalendarPoolFactory instance = null;
	
	// private constructor
	private CalendarPoolFactory() {}	// singleton needs private constructor

	public static synchronized CalendarPoolFactory getInstance() { 
		if (instance == null) instance = new CalendarPoolFactory();
		return instance;
	}
	
	public void open() {
		calendarPool.open();
	}
	
	public void close() {
		calendarPool.close();
	}

	public boolean add(MyGregorianCalendar calendar) {
		return calendarPool.add(calendar);
	}
	
	public MyGregorianCalendar get() {
		return (MyGregorianCalendar) calendarPool.acquire();
	}

	public void release(MyGregorianCalendar calendar) {
		calendarPool.release(calendar);
	}
	
	public static class MyGregorianCalendar extends GregorianCalendar implements IResource {
		public MyGregorianCalendar() {
			super();
		}
	}
}
