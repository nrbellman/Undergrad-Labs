package edu.millersville.csci366.studentslab.startercode;

import java.util.Map;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * An abstract way for the user to interact with student data.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public interface StudentView {
	
	/**
	 * Presents to the user information about every student.
	 * 
	 * @param students A map from ID to Student object, containing every student at the university.
	 */
	public abstract void showAllStudents(Map<String, Student> students);
	
	/**
	 * Requests from the user all the information about a new Student they would like to create.
	 * 
	 * @return A Student object for the student the user would like to create.
	 */
	public abstract Student getNewStudentDetails();
	
	/**
	 * Notifies the user that their attempt to create a student failed.
	 * 
	 * @param failedCreation The student that we were unable to create.
	 * @param reason An explanation about why it was not possible to create the student.
	 */
	public abstract void notifyCreateFailure(Student failedCreation, String reason);
	
	/**
	 * Requests from the user the ID of the student who should be deleted.
	 * 
	 * @return The ID of a student that the user wishes to delete.
	 */
	public abstract String getIdToDelete();
	
	/**
	 * Notifies the user that their attempt to delete a student failed.
	 * 
	 * @param id The ID of the student that we were unable to delete.
	 * @param reason An explanation about why it was not possible to delete the student.
	 */
	public abstract void notifyDeletionFailure(String id, String reason);
	
	/**
	 * Requests from the user the ID of the student who should be edited.
	 * 
	 * @return The ID of a student that the user wishes to edit.
	 */
	public abstract String getIdToEdit();
	
	/**
	 * Allows the user to make changes to a student.
	 * 
	 * @param student The student that can be edited.  When this method returns, it's values should be what the user wants them to be.
	 */
	public abstract void editStudent(Student student);
	
	/**
	 * Notifies the user that their attempt to edit a student failed.
	 * 
	 * @param id The ID of the student that we are unable to edit.
	 * @param reason An explanation about why it was not possible to edit the student.
	 */
	public abstract void notifyEditFailure(String id, String reason);
	
	/**
	 * Requests from the user the next action they would like to take.
	 * 
	 * @return The desired action.
	 */
	public abstract Action getNextAction();
	
	/**
	 * Notifies the user that we were unable to load the students.
	 * 
	 * @param reason An explanation about why it was not possible to load the students.
	 */
	public abstract void notifyLoadFailure(String reason);
	
}
