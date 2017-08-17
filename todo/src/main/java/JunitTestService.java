import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;

import com.bridgeit.todo.dao.daointerface.TaskDao;

import junit.framework.TestCase;

public class JunitTestService extends TestCase {

	@Autowired
	TaskDao taskDao;
	public JunitTestService(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

public void testGetNotes(){
	List<com.bridgeit.todo.model.Task> list = taskDao.getNotes(2);
	assertEquals(25, list.size());
}
}
