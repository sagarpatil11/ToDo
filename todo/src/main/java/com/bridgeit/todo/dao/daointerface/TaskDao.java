package com.bridgeit.todo.dao.daointerface;

import java.util.List;

import com.bridgeit.todo.model.Collaborator;
import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;

public interface TaskDao 
{
	public int addNote(Task task);
	
	public void updateNote(Task task);
	
	public void deleteTask(int tid);
	
	public List<Task> getNotes(User user);
	
	public Task getNoteById(int tid);

}
