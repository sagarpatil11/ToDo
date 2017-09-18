package com.bridgeit.todo.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Collaborator implements Serializable 
{
	@Id
	@GenericGenerator(name= "id", strategy = "increment")
	@GeneratedValue(generator="id")
	private int id; 
	
	private int noteOwnerId;
	
	private int noteSharedWith;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tid")
	private Task task;
	
	public Collaborator(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoteOwnerId() {
		return noteOwnerId;
	}

	public void setNoteOwnerId(int noteOwnerId) {
		this.noteOwnerId = noteOwnerId;
	}

	public int getNoteSharedWith() {
		return noteSharedWith;
	}

	public void setNoteSharedWith(int noteSharedWith) {
		this.noteSharedWith = noteSharedWith;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Collaborator [id=" + id + ", noteOwnerId=" + noteOwnerId + ", noteSharedWith=" + noteSharedWith
				+ ", task=" + task + "]";
	}
	
	
}
