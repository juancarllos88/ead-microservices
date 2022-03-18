package br.com.ead.course.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6134392779871786826L;
    
    public ResourceNotFoundException(String message) {
    	super(message);
    }

}

