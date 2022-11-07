package com.ecom.priceengine.exception;

public class ServiceException extends RuntimeException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 656965155730206655L;
	private String userDisplayMessage;   
        
    public ServiceException() {
    	super();
    }
        
    public ServiceException(String message, String userDisplayMessage) {
    	super(message);
        this.userDisplayMessage = userDisplayMessage;
    }

    public ServiceException(String userDisplayMessage) {
    	super();
        this.userDisplayMessage = userDisplayMessage;
    }
    
    public ServiceException(String message, Throwable cause, String userDisplayMessage) {
        super(message, cause);
        this.userDisplayMessage = userDisplayMessage;
    }

    public String getUserDisplayMessage() {
        return userDisplayMessage;
    }

    public void setUserDisplayMessage(String userDisplayMessage) {
        this.userDisplayMessage = userDisplayMessage;
    }    
}

