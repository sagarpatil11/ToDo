package com.bridgeit.todo.controller;

import java.util.Date;
import java.util.List;

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
	
	
	//..........................Add Notes...........................///
	
	@RequestMapping(value="/addNote")
	public void addNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		task.setUser(user);
		
		task.setCreation_date(new Date());
		
		taskService.addNote(task);
	}
	
	
	//..........................Update Notes...........................///
	
	@RequestMapping(value="/updateNote")
	public void updateNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		task.setUser(user);
		
		taskService.updateNote(task);
	}
	
	
	
	//..........................Delete Notes...........................///
	
	@RequestMapping(value="/deleteNote")
	public void deleteTask(@RequestParam int tid)
	{
		taskService.deleteTask(tid);
	}
	
	
	
	//..........................get notes..........................//
	
	@RequestMapping(value="/getNotes")
	public void getNotes(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		List<Task> notes=taskService.getNotes(user.getId());
		
		System.out.println(notes.toString());
	}
}
