package com.bridgeit.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.daointerface.TaskDao;
import com.bridgeit.todo.model.Task;

@Service
public class TaskService 
{
	@Autowired
	TaskDao taskDao;
	
	@Transactional
	public int addNote(Task task)
	{
		return taskDao.addNote(task);
	}
	
	@Transactional
	public void updateNote(Task task)
	{
		taskDao.updateNote(task);
	}
	
	@Transactional
	public void deleteTask(int tid)
	{
		taskDao.deleteTask(tid);
	}
	
	@Transactional(readOnly=true)
	public List<Task> getNotes(int uid)
	{
		return taskDao.getNotes(uid);
		
	}

}
