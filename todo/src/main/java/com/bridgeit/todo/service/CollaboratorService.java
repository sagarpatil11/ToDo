package com.bridgeit.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.daointerface.CollaboratorDao;
import com.bridgeit.todo.model.Collaborator;

@Service
public class CollaboratorService 
{
	@Autowired
	CollaboratorDao collaboratorDao;
	
	@Transactional
	public void addCollborator(Collaborator collaborator)
	{
		collaboratorDao.addCollaborator(collaborator);
	}
}
