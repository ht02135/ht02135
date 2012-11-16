package experiment.threadlocal;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
	1>if resource is cheap/light to create and access frequently
	2>if you dont mind each thread have copy of calendar, then use ThreadLocal
	3>in this case, we use ThreadLocal to wrap (not thread safe) GregorianCalendar
 */

public class CalendarFactory {
	/*
		1>This class provides thread-local variables. These variables differ from their 
		  normal counterparts in that each thread that accesses one (via its get or set 
		  method) has its own, independently initialized copy of the variable. ThreadLocal 
		  instances are typically private static fields in classes that wish to associate 
		  state with a thread (e.g., a user ID or Transaction ID)
		2>Each thread holds an implicit reference to its copy of a thread-local variable 
		  as long as the thread is alive and the ThreadLocal instance is accessible; after 
		  a thread goes away, all of its copies of thread-local instances are subject to 
		  garbage collection (unless other references to these copies exist) 
		  
		3>potential memory leak: you can only leak by increase (large amount) of undead 
		  threads (holds reference to LARGE thread-local variable).   thread pool can 
		  create (large amount) undead threads
		  
		  Depending on the pool size – in production systems this can be several hundred threads 
		  – and the size of the objects reference by the ThreadLocal variable this can lead to 
		  problems. A pool of 200 threads and a ThreadLocal size of 5MB will in the worst case 
		  lead to 1 GB of unnecessarily occupied memory.
		  
		  but hmm, dealing with large size resource, why wouldnt you use resource-pool mechanism?
		  why would you use Threadlocal mechanism (which mean for light objects).  
	 */
	private ThreadLocal<Calendar> calendarRef = new ThreadLocal<Calendar>() {
		protected Calendar initialValue() {
			return new GregorianCalendar();
		}
	};

	private static CalendarFactory instance = null;

	// private constructor
	private CalendarFactory() {}	// singleton needs private constructor

	public static synchronized CalendarFactory getInstance() { 
		if (instance == null) instance = new CalendarFactory();
		return instance;
	}

	public Calendar get() {
		return calendarRef.get();
	}  
}
