package com.example.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5895270510799026773L;

    private Status status;
	
	private Object message;
	
	
	List rows = new ArrayList();
	
	
	
	public Result(Status status, Object message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
	public Result(List rows) {
		super();
		this.rows = rows;
		this.status = Status.ok;
	}


	public enum Status{
		ok,error
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
