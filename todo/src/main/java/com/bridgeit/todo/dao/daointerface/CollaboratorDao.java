package com.bridgeit.todo.dao.daointerface;

import java.util.List;

import com.bridgeit.todo.model.Collaborator;

public interface CollaboratorDao 
{
	public void addCollaborator(Collaborator collaborator);
	
	public List getColList(int uid);
}
