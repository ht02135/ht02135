package experiment.resourcepool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class ResourcePool implements IResourcePool {

	/*
	effective java:
	1>inside synch region, dont call public or protected method that is designed to be overriden
	2>so if public or protected will be called from within synch region, then final it 
	*/
	
	private static Logger log = Logger.getLogger(ResourcePool.class);
	
	// shared vars
	private List<IResource> availableResources = null;
	private List<IResource> acquiredResources = null;
	
	/* volatile
	   1>Volatile is noway a replacement of synchronized keyword. It only deals with shared data visibility between threads. 
	   2>The typical use case is simple signaling between threads. One thread sets the signal and the others are only read it.  
	   3>It will not solve the problem caused by multiple thread updating the same variable, even if it is declared volatile. 
	   
	   use volatile variables instead of locks only under a restricted set of circumstances:
       1>Writes to the variable do not depend on its current value.
       2>The variable does not participate in invariants with other variables.
	 */
	private volatile boolean isOpen = false;	// use isOpen to signal resource pool is open or not between thread
	
	// locks
	private final ReentrantLock resourcesLock = new ReentrantLock();
	
	// conditions
	private final Condition availableResourceIsNotEmpty = resourcesLock.newCondition();
	private final Condition acquiredResourcesIsEmpty = resourcesLock.newCondition();
	private final Condition acquiredResourceIsReleased = resourcesLock.newCondition();
	
	public ResourcePool() {
		availableResources = new ArrayList<IResource>();
		acquiredResources = new ArrayList<IResource>();
	}

	// The pool shall not allow any resource to be acquired unless the pool is open.
	public final void open() {
		isOpen = true;
	}

	public final boolean isOpen() {
		return isOpen;
	}

	// The close() method should be such that it blocks until all acquired resources are released.
	public final void close() {
		try {
			resourcesLock.lock();	// effective java: lock
			while (!acquiredResources.isEmpty()) {	// effective java: test condition
				try {
					acquiredResourcesIsEmpty.await();
				} catch (InterruptedException ie) {
				}
			}
			// effective java: perform action appropriate to condition
			closeNow();
		} finally {
			resourcesLock.unlock();
		}
	}
	
	// closes the pool immediately without waiting for all acquired resources to be released
	// no more acquir, because is not open, but still allow resources to be released back
	public final void closeNow() {
		isOpen = false;
	}

	// checked out of the pool.  The acquire() method should block until a resource is available.
	public final IResource acquire() {
		if (!isOpen()) return null;
		
		IResource resource = null;
		try {
			resourcesLock.lock();	// effective java: synch or lock
			while (availableResources.isEmpty()) {	// effective java: test condition
				try {
					availableResourceIsNotEmpty.await();	// wait until signaled
				} catch (InterruptedException ie) {}
			}
			// effective java: perform action appropriate to condition
			resource = availableResources.remove(0);
			acquiredResources.add(resource);	
				
			// effective java: signall after perform action
			if (!availableResources.isEmpty()) availableResourceIsNotEmpty.signal();
			if (acquiredResources.isEmpty()) acquiredResourcesIsEmpty.signal();
		} finally {
			resourcesLock.unlock();
		}
		return resource;
	}

	// If a resource cannot be acquired within the timeout interval specified in the acquire(long, TimeUnit) method,
	// either a null can be returned
	public final IResource acquire(long timeout, TimeUnit timeUnit) {
		if (!isOpen()) return null;
		
		IResource resource = null;
		try {
			if (resourcesLock.tryLock(timeout, timeUnit)) {	// lock acquired
				if (!availableResources.isEmpty()) {	// effective java: test condition
					// effective java: perform action appropriate to condition
					resource = availableResources.remove(0);
					acquiredResources.add(resource);
							
					// effective java: signall after perform action
					if (!availableResources.isEmpty()) availableResourceIsNotEmpty.signal();
					if (acquiredResources.isEmpty()) acquiredResourcesIsEmpty.signal();
				}
				resourcesLock.unlock();
			}
		} catch (InterruptedException ie) {
		} finally {}
		return resource;
	}

	// checked back into the pool.  Resources can be released at any time.  Resources can be added or removed at any time
	public final void release(IResource resource) {
		if (resource != null) {
			// always lock available before lock acquired to avoid deadlock
			try {
				resourcesLock.lock();

				int resourceIndex = acquiredResources.indexOf(resource);
				if (resourceIndex >= 0) {	// effective java: test condition
					// effective java: perform action appropriate to condition
					acquiredResources.remove(resourceIndex);
					availableResources.add(resource);
						
					// effective java: signall after perform action
					if (!availableResources.isEmpty()) availableResourceIsNotEmpty.signal();
					if (acquiredResources.isEmpty()) acquiredResourcesIsEmpty.signal();
					acquiredResourceIsReleased.signal();
				}
			} finally {
				resourcesLock.unlock();
			}
		}
	}
	
	// The add(R) and remove(R) methods return true if the pool was modified as a result of the method call or false if the pool was not modified.
	// Resources can be added or removed at any time
	public final boolean add(IResource resource) {
		// check if is null
		if (resource == null) return false;
		
		// check if is valid resource in resource pool
		if (resourceExists(resource)) return false;
		
		boolean isAdded = false;
		try {
			resourcesLock.lock();
			if (!(availableResources.contains(resource) || acquiredResources.contains(resource))) {	// effective java: test condition
				// effective java: perform action appropriate to condition
				if (availableResources.add(resource)) {
					isAdded = true;
					// effective java: signall after perform action
					if (!availableResources.isEmpty()) availableResourceIsNotEmpty.signal();
				}
			}
		} finally {
			resourcesLock.unlock();
		}
		return isAdded;
	}

	// The remove(R) method should be such that if the resource that is being removed is currently in use, the remove
	// operation will block until that resource has been released.
	public final boolean remove(IResource resource) {
		// check if is null
		if (resource == null) return false;
		
		// check if is valid resource in resource pool
		if (!resourceExists(resource)) return false;
		
		boolean isRemoved = false;
		try {
			resourcesLock.lock();
			while (acquiredResources.contains(resource)) {	// effective java: test condition
				try {
					// cant wait forever (resource might be removed by other thread)
					acquiredResourceIsReleased.await(1000,TimeUnit.MILLISECONDS);
				} catch (InterruptedException ie) {}
			}
			// effective java: perform action appropriate to condition
			if (availableResources.remove(resource)) {
				isRemoved = true;	
				// effective java: signall after perform action
				if (!availableResources.isEmpty()) availableResourceIsNotEmpty.signal();
			}
		} finally {
			resourcesLock.unlock();
		}
		return isRemoved;
	}
	
	// removes the given resource immediately without waiting for it to be released. It returns true if the call
	// resulted in the pool being modified and false otherwise.
	public final boolean removeNow(IResource resource) {
		// check if is null
		if (resource == null) return false;
		
		// check if is valid resource in resource pool
		if (!resourceExists(resource)) return false;
		
		// release then remove resource
		release(resource);	// release resource right away
		return remove(resource);	// remove the resource
	}
	
	private final boolean resourceExists(IResource resource) {
		// check if resource exists in resource pool
		boolean exists = false;
		try {
			resourcesLock.lock();
			exists = availableResources.contains(resource) || acquiredResources.contains(resource);
		} finally {
			resourcesLock.unlock();
		}
		return exists;
	}
	
	public synchronized String toString() {
		return "[availableResources="+availableResources+", acquiredResources="+acquiredResources+", isOpen="+isOpen+"]";
	}
	
	// ---------------- Nested Class: Tasks ----------------------------------------------
	
	// make public nested class because it is used outside of enclosing class
	public static class Resource implements IResource {
		
		private static Logger log = Logger.getLogger(Resource.class);
		
		private String name = "resource";
		
		public Resource() {}
	}
}
