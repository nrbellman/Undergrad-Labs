package edu.millersville.csci366.studentslab.startercode;

import java.util.Map;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * An interface for ways to permanently store information about students.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public interface DataSource {

	/**
	 * Reads information about every student from persistent storage.
	 * 
	 * @return A map from ID -> Student for every student.
	 * @throws DataException If there is a problem interacting with the data source.
	 */
	public abstract Map<String, Student> loadAllStudents() throws DataException;
	
	/**
	 * Stores information about a new student in persistent storage.
	 * 
	 * @param student The new student.
	 * @throws DataException If there is a problem interacting with the data source.
	 */
	public abstract void createStudent(Student student) throws DataException;
	
	/**
	 * Changes some of the information about an existing student.
	 * 
	 * @param student A student object containing the updated values.
	 * @throws DataException If there is a problem interacting with the data source.
	 */
	public abstract void updateStudent(Student student) throws DataException;
	
	/**
	 * Deletes a student from persistent storage.
	 * 
	 * @param student The student to delete.
	 * @throws DataException If there is a problem interacting with the data source.
	 */
	public abstract void deleteStudent(Student student) throws DataException;

}
