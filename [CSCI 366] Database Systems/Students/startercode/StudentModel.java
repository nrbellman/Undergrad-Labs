package edu.millersville.csci366.studentslab.startercode;

import java.util.Map;
import java.util.TreeMap;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * A way that student data is stored in memory as the program runs and is persistently stored.
 */
public class StudentModel {

	/** The data source for permanent storage. */
	private DataSource dataSource;
	/** The in-memory data structure for immediate use. */
	private Map<String, Student> students;
	
	/**
	 * Constructs a new StudentModel.
	 * 
	 * @param dataSource The data source that should be used.
	 */
	public StudentModel(DataSource dataSource) {
		this.dataSource = dataSource;
		students = new TreeMap<>();
	}
	
	/**
	 * Gets a specific student.
	 * 
	 * @param id The ID of the student.
	 * @return The student with that ID, or null if none does.
	 */
	public Student getStudent(String id) {
		return students.get(id);
	}
	
	/**
	 * [Re-]loads all students from the data source.
	 * 
	 * @throws DataException If the operation is illegal.
	 */
	public void loadStudents() throws DataException {
		students = dataSource.loadAllStudents();
	}
	
	/**
	 * Gets access to the in-memory student collection.
	 * 
	 * @return A map of ID -> student.
	 */
	public Map<String, Student> getStudents() {
		return students;
	}
	
	/**
	 * Creates a new student, telling the data source about it and putting it in the map.
	 * 
	 * @param student The new student.
	 * @throws DataException If the operation is illegal.
	 */
	public void newStudent(Student student) throws DataException {
		dataSource.createStudent(student);
		students.put(student.getId(), student);
	}
	
	/**
	 * Modifies a student, telling the data source about it and replacing the old with the new version in the map.
	 * 
	 * @param student The modified version of the student.
	 * @throws DataException If the operation is illegal.
	 */
	public void modifiedStudent(Student student) throws DataException {
		dataSource.updateStudent(student);
		students.put(student.getId(), student);
	}
	
	/**
	 * Deletes a student, telling the data source about it and removing it from the map.
	 * 
	 * @param id The ID of the student to delete.
	 * @throws DataException If the operation is illegal.
	 */
	public void deleteStudent(String id) throws DataException {
		if(students.containsKey(id)) {
			dataSource.deleteStudent(students.get(id));
			students.remove(id);
		}
		else {
			throw new DataException("that student does not exist.");
		}

	}
}
