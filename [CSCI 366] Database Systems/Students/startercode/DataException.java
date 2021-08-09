package edu.millersville.csci366.studentslab.startercode;

import java.io.IOException;
import java.sql.SQLException;

/**
 * An exception that occurred while interacting with some kind of data source.
 * This class exists so that we can make our code generic -- some data sources will throw SQLExceptions
 *   while others will throw IOExceptions, but we want to be able to handle both the same way.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public class DataException extends Exception {

	/** A version ID because this type can be serialized -- don't worry about what any of that means. */
	private static final long serialVersionUID = 1L;
	
	/** An explanation of the exception's cause. */
	private String message;
	
	/**
	 * Constructs a new DataException from an SQLException.
	 * 
	 * @param cause The SQLException that was thrown.
	 */
	public DataException(SQLException cause) {
		message = cause.getMessage();
	}
	
	/**
	 * Constructs a new DataException from an IOException.
	 * 
	 * @param cause The IOException that was thrown.
	 */
	public DataException(IOException cause) {
		message = cause.getMessage();
	}
	
	/**
	 * Constructs a new DataException for some other type of problem that occurred.
	 * @param message
	 */
	public DataException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
