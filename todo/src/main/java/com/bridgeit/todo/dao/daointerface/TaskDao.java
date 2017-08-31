package com.bridgeit.todo.dao.daointerface;

import java.util.List;

import com.bridgeit.todo.model.Task;

public interface TaskDao 
{
	public int addNote(Task task);
	
	public void updateNote(Task task);
	
	public void deleteTask(int tid);
	
	public List<Task> getNotes(int uid);
	
}
