package edu.millersville.csci366.studentslab.startercode;

/**
 * A high-level action the user can take in this program.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public enum Action {
	
	/** Creates a new student. */
	NEW,
	/** Modified an existing student. */
	EDIT,
	/** Deletes an existing student. */
	DELETE,
	/** Asks the data source to reload all data, so that we can see any changes made outside of our program. */
	REFRESH,
	/** Exits the program. */
	QUIT;
}
