package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

/**
 *  This Blocking FIFO is implemented in singleton manner to ensure there will be just
 *  one instance of Blocking FIFO in the whole project. The reference to the unique 
 *  instance of this class can only be obtained by getInstance() method in this class.
 *  
 * @author User
 *
 */
public class BlockFIFO 
{
	private static final int SIZE = 100; // Default size of the blocking FIFO.
	private static Task[] buffer; // The blocking FIFO.
	private static int count; // Number of tasks in the blocking FIFO.
	private static int nextIn; // Pointer to the tail of the blocking FIFO.
	private static int nextOut; // Pointer to the head of the blocking FIFO.
	
	private static Object notFull; // Monitors used to control putting tasks into the FIFO.
	private static Object notEmpty; // Monitors used to control taking tasks from the FIFO.
	
	// Generate the unique blocking FIFO. (unique instance of the class)
	static BlockFIFO singletonInstance = new BlockFIFO(); 
	
	// Mute default constructor.
	private BlockFIFO()
	{
		BlockFIFO.buffer = new Task[SIZE];
		BlockFIFO.count = 0;
		BlockFIFO.nextIn = 0;
		BlockFIFO.nextOut = 0;
		
		notFull = new Object();
		notEmpty = new Object();
	}
	
	// Put one task into blocking FIFO if not full:
	public void put(Task task) 
	{
		while(true) 
		{
			if(count >= SIZE) 
				synchronized(notFull) 
				{
					try 
					{
						if(count >= SIZE) // Double checking.
							notFull.wait();
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			
			synchronized(this) 
			{
				if(count >= SIZE) continue;
				buffer[nextIn] = task;
				nextIn = (++nextIn) % SIZE;
				++count;
				synchronized(notEmpty) 
				{ 
					notEmpty.notifyAll();
				}
			}
			
			break; 
		}
	}
	
	// Take one task from the blocking FIFO if not empty:
	public Task take() 
	{
		while(true) 
		{
			if(count < 1) 
				synchronized(notEmpty) 
				{
					try 
					{
						if(count < 1) // Double checking.
							notEmpty.wait();
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			
			Task result = null;
			synchronized(this) 
			{
				if(count < 1) continue;
				result = buffer[nextOut];
				nextOut = (++nextOut) % SIZE;
				--count;
				synchronized(notFull) 
				{ 
					notFull.notifyAll();
				}
			}

			return result; 
		}
	}
	
	// Return reference to the unique instance of this class. (Return the reference to the 
	// unique blocking FIFO of this project)
	public static BlockFIFO getInstance() 
	{
		return singletonInstance;
	}
}
