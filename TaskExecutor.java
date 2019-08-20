package edu.utdallas.taskExecutor;

/**
 * This service accepts instance of tasks, add these tasks into FIFO.
 * Clients use this service to add tasks. Mind that Tasks are customized by clients 
 * by implementing 
 * 
 * @author User
 *
 */
public interface TaskExecutor
{
	void addTask(Task task);
}
