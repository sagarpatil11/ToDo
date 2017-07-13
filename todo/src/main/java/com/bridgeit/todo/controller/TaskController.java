package com.bridgeit.todo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TaskService;

@RestController
public class TaskController 
{
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value="/addNote")
	public void addNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		//task.setUser(user);
		
		taskService.addNote(task);
	}
	
	@RequestMapping(value="/updateNote")
	public void updateNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
	//	task.setUser(user);
		
		taskService.updateNote(task);
	}
	
	@RequestMapping(value="/deleteNote")
	public void deleteTask(@RequestParam int tid)
	{
		taskService.deleteTask(tid);
	}
}
