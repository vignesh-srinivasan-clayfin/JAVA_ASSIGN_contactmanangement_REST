package com.resource.common;

public class CustomException extends Exception{
	
	
	 private static final long serialVersionUID = 1L;
	 
	 public String errorCode;
	 public String errorMessage;

	public CustomException() {
	        super();
	    }

	    public CustomException(String message) {
	        super(message);
	    }
	    
	    public CustomException(String message, String errorCode, String errorMessage) {
	    	super(message);
	        this.errorCode = errorCode;
	        this.errorMessage = errorMessage;
	    }
	    
	 // Getter methods to retrieve the values
	    public String getErrorCode() {
	        return errorCode;
	    }

	    public String getErrorMessage() {
	        return errorMessage;
	    }

}
