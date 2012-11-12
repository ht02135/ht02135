package resourcepool;

import java.util.concurrent.TimeUnit;

public interface IResourcePool {;
	public void open();
	public boolean isOpen();
	
	public void close();
	public void closeNow();
	
	public IResource acquire();
	public IResource acquire(long timeout, TimeUnit timeUnit);
	public void release(IResource resource);
	public boolean add(IResource resource);
	
	public boolean remove(IResource resource);
	public boolean removeNow(IResource resource);
}