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
import com.bridgeit.todo.model.WebScraper;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.TaskService;
import com.bridgeit.todo.service.WebScraperService;

/**
 * this rest controller have todo task related methods
 * @author sagar
 *
 */
@RestController
public class TaskController 
{
	@Autowired
	TaskService taskService;
	
	@Autowired
	WebScraperService webScraperService;
	
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
		
		WebScraper webScraper=webScraperService.createWebScraper(task.getDescription());
		
		task.setUser(user);
		
		task.setCreation_date(new Date());
		
		task.setEdited_date(new Date());
		
		try 
		{
			int tid=taskService.addNote(task);
			
			if(webScraper != null)
			{
				webScraper.setTid(tid);
				webScraperService.saveWebScraper(webScraper);
			}
			
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
		
		System.out.println(task.toString());
		
		task.setUser(user);
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
	
	
	
	
	/**
	 * this method is used to get all notes 
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
			
			List<Task> list=addScraperInNote(taskList);
			
			userResponse.setList(list);
			userResponse.setStatus(1);
			userResponse.setMessage("Notes list");
			user.setPassword("");
			userResponse.setUser(user);
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			errorResponse.setStatus(-1);
			errorResponse.setMessage("There is some problem in getting list of notes");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
		
	}
	
	
	private List<Task> addScraperInNote(List notes) 
	{
		 for (int i = 0; i < notes.size(); i++) 
		 {
				Task todoNotes =  (Task) notes.get(i);
				List<WebScraper> scrapers = getAllWebScraper(todoNotes.getTid());
				todoNotes.setWebscraper(scrapers);;
		}
		return notes;
	}
	
	
	/**
	 * this method gets all webScrapers from database
	 * @param tid
	 * @return List<WebScraper> {@link List}
	 */
	private List<WebScraper> getAllWebScraper(int tid)
	{
		try
		{
			return webScraperService.getWebScraper(tid);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
