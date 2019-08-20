package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockFIFO;
import edu.utdallas.taskExecutor.Task;

/**
 * This class is used to get a task from the blocking FIFO and execute it.
 * @author User
 *
 */
public class TaskRunner implements Runnable
{
	public void run() 
	{
		// Request tasks from blocking FIFO.
		while(true) 
		{
			Task task = BlockFIFO.getInstance().take();
			
			try 
			{
				task.execute();
			}
			catch(Throwable th) 
			{
				System.out.println("Thread-" + Thread.currentThread().getName() + 
						"-RuntimeException: Task started twice!");
			}
		}
	}
}
