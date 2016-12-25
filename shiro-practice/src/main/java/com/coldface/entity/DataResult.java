package com.coldface.entity;


public class DataResult<T> extends SimpleResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1502070871239807626L;

	 private T data;

	    public DataResult() {
	    }

	    public T getData() {
	        return this.data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }
}
