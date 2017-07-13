package com.bridgeit.todo.dao.daointerface;

import com.bridgeit.todo.model.Task;

public interface TaskDao 
{
	public void addNote(Task task);
	
	public void updateNote(Task task);
	
	public void deleteTask(int tid);
}
