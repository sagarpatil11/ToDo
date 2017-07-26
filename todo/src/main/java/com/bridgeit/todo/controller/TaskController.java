package com.bridgeit.todo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.TaskService;

@RestController
public class TaskController 
{
	@Autowired
	TaskService taskService;
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	//..........................Add Notes...........................///
	
	@RequestMapping(value="/addNote")
	public ResponseEntity<Response> addNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		task.setUser(user);
		
		task.setCreation_date(new Date());
		
		try {
			taskService.addNote(task);
			
			userResponse.setStatus(1);
			userResponse.setMessage("Note added");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Note not added");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
	} 
	
	
	//..........................Update Notes...........................///
	
	@RequestMapping(value="/updateNote")
	public void updateNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		task.setUser(user);
		
		try {
			taskService.updateNote(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//..........................Delete Notes...........................///
	
	@RequestMapping(value="/deleteNote")
	public void deleteTask(@RequestParam int tid)
	{
		try {
			taskService.deleteTask(tid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//..........................get notes..........................//
	
	@RequestMapping(value="/getNotes")
	public void getNotes(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		List<Task> notes;
		try {
			notes = taskService.getNotes(user.getId());
			System.out.println(notes.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
