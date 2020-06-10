//-------------------------------------------------------------------
//Assignment 3
//Question: 2
//Written by: Jean-Francois Vo (40132554)
//-------------------------------------------------------------------
//This is the exception that is thrown when a file is invalid.
/**
 * @author Jean-Francois Vo (40132554)
 * COMP 249
 * Assignment 3
 * 30 March 2020
 */

/**
 * Exception thrown when the file is invalid
 */
public class FileInvalidException extends Exception{
	
	/**
	 * Default constructor of the exception
	 */
	public FileInvalidException() {
		super("ERROR:  Input  file  cannot  be  parsed  due  to  missing  information (i.e. month={}, title={}, etc.)");
	}
	
	/**
	 * Constructor with a parameter
	 * @param message a customized message that is taken as a parameter of the constructor
	 */
	public FileInvalidException(String message) {
		super(message);
	}
}
