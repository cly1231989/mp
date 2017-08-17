package com.koanruler.mp.entity;

public class DataState{
	private int id;
	private int handlestate;
	
	public DataState(int id, int state) {
		super();
		this.id = id;
		this.setHandlestate(state);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHandlestate() {
		return handlestate;
	}
	public void setHandlestate(int handlestate) {
		this.handlestate = handlestate;
	}
}