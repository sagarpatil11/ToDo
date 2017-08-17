
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.bridgeit.todo.model.Task;
import com.bridgeit.todo.model.User;
import com.bridgeit.todo.service.TaskService;

import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author sagar
 *
 */
public class ToDoTest extends TestCase {

	@Autowired
	TaskService taskService;
	
	Task task;
	
	/**
	 * @param name
	 */
	public ToDoTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link com.bridgeit.todo.controller.TaskController#addNote(com.bridgeit.todo.model.Task, javax.servlet.http.HttpServletRequest)}.
	 */
	/*public void testAddNote() 
	{
		task=new Task();
		task.setTitle("junit testing1");
		task.setDescription("testing");
		
		User user=new User();
		user.setId(1);
		task.setUser(user);
		
		
	
	}*/

	/**
	 * Test method for {@link com.bridgeit.todo.controller.TaskController#updateNote(com.bridgeit.todo.model.Task, javax.servlet.http.HttpServletRequest)}.
	 *//*
	public void testUpdateNote() {
		
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.bridgeit.todo.controller.TaskController#deleteTask(int, javax.servlet.http.HttpServletRequest)}.
	 *//*
	public void testDeleteTask() {
		fail("Not yet implemented");
	}

	*//**
	 * Test method for {@link com.bridgeit.todo.controller.TaskController#getNotes(javax.servlet.http.HttpServletRequest)}.
	 */
	
	public void testGetNotes() 
	{
		List<Task>  list = taskService.getNotes(2);
		
		assertEquals(list.size(),25);
		//assertNotNull(taskService.getNotes(2));
		
		
	}

}
