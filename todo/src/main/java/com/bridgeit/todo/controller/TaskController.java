package com.bridgeit.todo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.TaskService;

/**
 * @author bridgeit
 *
 */
@RestController
public class TaskController 
{
	@Autowired
	TaskService taskService;
	
	UserResponse userResponse=new UserResponse();
	ErrorResponse errorResponse=new ErrorResponse();
	
	
	
	/**
	 * This method create new note 
	 * @param task	{@link Task}
	 * @param request {@link HttpServletRequest}
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/addNote")
	public ResponseEntity<Response> addNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		task.setUser(user);
		
		task.setCreation_date(new Date());
		
		task.setEdited_date(new Date());
		
		try 
		{
			taskService.addNote(task);
			List taskList = taskService.getNotes(user.getId());
			
			userResponse.setList(taskList);
			userResponse.setStatus(1);
			userResponse.setMessage("Note added");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Note not added");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
			
		}
		
	} 
	
	
	//..........................Update Notes...........................///
	
	
	/**
	 * This method update's note contents
	 * @param task {@link Task}
	 * @param request {@link HttpServletRequest}
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/updateNote",method=RequestMethod.POST)
	public ResponseEntity<Response> updateNote(@RequestBody Task task,HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		//task.setUser(user);
		System.out.println(task.toString());
		
		task.setEdited_date(new Date());
		
		try 
		{
			taskService.updateNote(task);
			
			List taskList = taskService.getNotes(user.getId());
			
			userResponse.setStatus(1);
			userResponse.setMessage("Note updated successfully");
			userResponse.setList(taskList);
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Updation failed due to some exception");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
		}
	}
	
	
	
	//..........................Delete Notes...........................///
	
	/**
	 * This method delete note 
	 * @param tid {@link Task}
	 * @param request {@link HttpServletRequest}
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/deleteNote/{tid}",method=RequestMethod.POST)
	public ResponseEntity<Response> deleteTask(@PathVariable("tid") int tid, HttpServletRequest request)
	{
		System.out.println("delete tid "+tid );
		
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		
		try 
		{
			taskService.deleteTask( tid);
			
			List taskList = taskService.getNotes(user.getId());
			
			userResponse.setList(taskList);
			userResponse.setStatus(1);
			userResponse.setMessage("Note deleted");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Note is not deleted");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
	}
	
	
	
	//..........................get notes..........................//
	
	/**
	 * @param request {@link HttpServletRequest}
	 * @return ResponseEntity {@link Response}
	 */
	@RequestMapping(value="/getNotes")
	public ResponseEntity<Response> getNotes(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("userSession");
		//System.out.println(user.getId());
	
		try 
		{
			List taskList = taskService.getNotes(user.getId());
			
			userResponse.setList(taskList);
			userResponse.setStatus(1);
			userResponse.setMessage("Notes list");
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("There is some problem in getting list of notes");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
		
	}
	
	
}
