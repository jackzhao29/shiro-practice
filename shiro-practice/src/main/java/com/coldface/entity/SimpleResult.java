package com.coldface.entity;

import java.io.Serializable;

public class SimpleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5167253055329696043L;
	
	private String success;
    private String errorCode;
    private String errorMessage;

    public SimpleResult() {
    }

    public String isSuccess() {
        return this.success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    
    public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
