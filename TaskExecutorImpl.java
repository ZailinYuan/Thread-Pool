package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockFIFO;
import edu.utdallas.taskExecutor.Task;

/**
 * This is actually TaskExecutor, used  by clients adding Tasks to FIFO;
 * 
 * A request pool needed to contain clients' requests:
 * @author User
 *
 */

public class TaskExecutorImpl
{	
	private Thread[] threadPool; // The thread pool:
	BlockFIFO fifo = BlockFIFO.getInstance(); // The blocking FIFO.
	
	// Create specified number of threads in the thread pool and start these threads.
	public TaskExecutorImpl(int numOfThreads) 
	{
		threadPool = new Thread[numOfThreads];
		
		for(int i=0; i<numOfThreads; ++i) {
			// 
			threadPool[i] = new Thread(new TaskRunner(),"#ThreadPool_Service(" + i +")");
			threadPool[i].start();
		}
	}
	
	public void addTask(Task task)
	{
		fifo.put(task);
	}

}