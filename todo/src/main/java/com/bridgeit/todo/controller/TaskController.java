package com.bridgeit.todo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.todo.model.Collaborator;
import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.model.WebScraper;
import com.bridgeit.todo.responsemsg.ErrorResponse;
import com.bridgeit.todo.responsemsg.Response;
import com.bridgeit.todo.responsemsg.UserResponse;
import com.bridgeit.todo.service.CollaboratorService;
import com.bridgeit.todo.service.TaskService;
import com.bridgeit.todo.service.UserRegService;
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
	
	@Autowired
	UserRegController userRegController;
	
	@Autowired
	UserRegService userRegService;
	
	@Autowired
	CollaboratorService collaboratorService;
	
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
			
		//	List taskList = taskService.getNotes(user.getId());
			
			//userResponse.setList(taskList);
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
		
		WebScraper webScraper=webScraperService.createWebScraper(task.getDescription());
		
		//task.setUser(user);
		task.setEdited_date(new Date());
		
		System.out.println("update task"+task);
		try 
		{
			taskService.updateNote(task);
			
			if(webScraper != null)
			{
				if(webScraperService.getScraperByHostUrl(webScraper.getHosturl()) == null)
				{	
					System.out.println("add scarper in update");
					
					webScraper.setTid(task.getTid());
					webScraperService.saveWebScraper(webScraper);
			
				}
			}
		//	List taskList = taskService.getNotes(user.getId());
			
			userResponse.setStatus(1);
			userResponse.setMessage("Note updated successfully");
		//	userResponse.setList(taskList);
			
			
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
			
		//	List taskList = taskService.getNotes(user.getId());
			
		//	userResponse.setList(taskList);
			userResponse.setStatus(1);
			userResponse.setMessage("Note deleted");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Note is not deleted");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/deleteScraper",method=RequestMethod.POST)
	public ResponseEntity<Response> deleteScraper(@RequestBody WebScraper scraper, HttpServletRequest request)
	{
		System.out.println("in deleteScraper: "+scraper);
		
		try
		{
			webScraperService.deleteScraper(scraper.getId());
			
			userResponse.setStatus(1);
			userResponse.setMessage("Scraper deleted");
			
			return new ResponseEntity<Response>(userResponse,HttpStatus.OK);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Scraper is not deleted");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
	}
	
	
	
	/**
	 * @param colMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/collaborator", method=RequestMethod.POST)
	public ResponseEntity<Response> collaborator(@RequestBody Map<String, Object> colMap, HttpServletRequest request)
	{
		int tid=(Integer) colMap.get("tid");
		
		Task task=taskService.getNoteById(tid);
		
		User usertoshare=null;
		
		try
		{
			usertoshare= userRegService.getUserByEmail((String) colMap.get("emailToShare"));
			
			if(usertoshare == null)
			{
				errorResponse.setStatus(-2);
				errorResponse.setMessage("email to share with not found");
				
				return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
			}
			
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Some Problem have occured in getting user");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
			
		}
		
		Collaborator collaborator=new Collaborator();
		
		collaborator.setTask(task);
		collaborator.setNoteOwnerId(task.getUser().getId());
		collaborator.setNoteSharedWith(usertoshare.getId());
		
		System.out.println("\nColl task"+collaborator);
		
		try
		{
			collaboratorService.addCollborator(collaborator);
			
			userResponse.setStatus(1);
			userResponse.setMessage("Note Collaborated successfully");
			
			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			
			errorResponse.setStatus(-1);
			errorResponse.setMessage("Some Problem have occured while adding collaborator");
			
			return new ResponseEntity<Response>(errorResponse, HttpStatus.OK);
			
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
			List<Task> taskList = taskService.getNotes(user);
			
			System.out.println("main list"+taskList);
			
			List<Task> colList = collaboratorService.getColList(user.getId());
			System.out.println("col list"+colList);

			
			List<Task> finalList=new ArrayList<Task>();
			
			finalList.addAll(colList);
			finalList.addAll(taskList);
			
			List<Task> list=addScraperInNote(finalList);
			
			userResponse.setList(list);
			userResponse.setStatus(1);
			userResponse.setMessage("Notes list");
			
			userResponse.setUser(user);

			return new ResponseEntity<Response>(userResponse, HttpStatus.OK);
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			errorResponse.setStatus(-1);
			errorResponse.setMessage("There is some problem in getting list of notes");
			
			return new ResponseEntity<Response>(errorResponse,HttpStatus.OK);
		}
		
		
	}
	
	
	private List<Task> addScraperInNote(List<Task> notes) 
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
