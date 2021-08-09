package edu.millersville.csci366.studentslab.startercode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * A class for permanently storing data about students in a comma-separated values file.
 * Because these files contain variable-length records, modifying them in place is essentially impossible.
 * 
 * Known limitations:
 *   - Ids, names, and addresses may not contain commas.  
 *       Could be swapped out for a less common character by changing DELIMETER.
 *   - This assumes the file will be small enough that reading the entire contents into memory won't be 
 *       terribly inefficient.
 *   - If another program edits the file between when data is read and written, that program's edits will be lost.  
 *       This could be fixed by locking the file at the beginning of createStudent / updateStudent / deleteStudent 
 *       and unlocking at the end, but I am trying to avoid using libraries students will not have seen in 161/162 
 *       as much as possible.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public class CSVFileDataSource implements DataSource {

	/** The default name of the file that stores the student data. */
	public static final String FILENAME = "data/students.csv";
	/** The delimeter used to separate id / name / department name / total credits. */
	public static final String DELIMETER = ",";
	
	/** The file in which the data is stored. */
	public File dataFile;
	
	/**
	 * Constructs a new CSVFileDataSource.
	 * 
	 * @param filename The name of the file in which student data can be found.
	 */
	public CSVFileDataSource(String filename) {
		dataFile = new File(filename);
	}
	
	/**
	 * Re-writes the entire data file to contain a certain group of students.
	 * 
	 * @param students A map from ID -> Student object.  These are the students that should exist in the file.
	 * @throws IOException
	 */
	private void writeFile(Map<String, Student> students) throws IOException {
		try (PrintWriter writer = new PrintWriter(dataFile);) {
			for(String id : students.keySet()) {
				Student student = students.get(id);
				String line = id + DELIMETER + student.getName() + DELIMETER + student.getDeptName() + DELIMETER + student.getTotCredits();
				writer.println(line);
			}
		}
	}
	 
	/**
	 * Throws a DataException if any aspect of a student will not work with this class.
	 * 
	 * @param student The student to validate.
	 * @throws DataException If any field in the student contains DELIMETER.
	 */
	private void validateStudent(Student student) throws DataException {
		if(student.getId().contains(DELIMETER)) {
			throw new DataException("the student's ID may not contain \"" + DELIMETER + "\"");
		}
		if(student.getName().contains(DELIMETER)) {
			throw new DataException("the student's name may not contain \"" + DELIMETER + "\"");
		}
		if(student.getDeptName().contains(DELIMETER)) {
			throw new DataException("the student's department name may not contain \"" + DELIMETER + "\"");
		}
	}

	@Override
	public Map<String, Student> loadAllStudents() throws DataException {
		Map<String, Student> students = new TreeMap<>();
		try {
			try (Scanner fileScanner = new Scanner(dataFile);) {
				while(fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					String[] parts = line.split(DELIMETER);
					if(parts.length != 4) {
						throw new DataException("a line contained " + parts.length + " fields (rather than 4).");
					}
					if(students.containsKey(parts[0])) {
						throw new DataException("the file contained two lines with the same first field.");
					}
					students.put(parts[0], new Student(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
				}
			}
		}
		catch(IOException exception) {
			throw new DataException(exception);
		}
		return students;
	}

	@Override
	public void createStudent(Student student) throws DataException {
		validateStudent(student);
		try {
			Map<String, Student> students = loadAllStudents();
			if(students.containsKey(student.getId())) {
				throw new DataException("the file already contains a student with ID " + student.getId());
			}
			students.put(student.getId(), student);
			writeFile(students);
		}
		catch(IOException exception) {
			throw new DataException(exception);
		}
	}

	@Override
	public void updateStudent(Student student) throws DataException {
		validateStudent(student);
		try {
			Map<String, Student> students = loadAllStudents();
			if(!students.containsKey(student.getId())) {
				throw new DataException("the file does not contain a student with ID " + student.getId());
			}
			students.put(student.getId(), student);
			writeFile(students);
		}
		catch(IOException exception) {
			throw new DataException(exception);
		}
	}

	@Override
	public void deleteStudent(Student student) throws DataException {
		try {
			Map<String, Student> students = loadAllStudents();
			if(!students.containsKey(student.getId())) {
				throw new DataException("the file does not contain a student with ID " + student.getId());
			}
			students.remove(student.getId());
			writeFile(students);
		}
		catch(IOException exception) {
			throw new DataException(exception);
		}
	}
}
