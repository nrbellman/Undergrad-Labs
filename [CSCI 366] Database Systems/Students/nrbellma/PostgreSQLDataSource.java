package edu.millersville.csci366.studentslab.nrbellma;

import java.util.Map;
import java.util.TreeMap;

import edu.millersville.csci366.studentslab.startercode.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQLDataSource implements DataSource{

	/* ===== Class Constants ======= */
	
	public static final String PROTOCOL = "jdbc:postgresql";
	public static final String HOSTNAME = "mucs.chadhogg.name";
	public static final String DATABASE = "a07students_nrbellma";
	public static final String URL = PROTOCOL + "://" + HOSTNAME + "/" + DATABASE;
	public static final String USERNAME = "nrbellma";
	public static final String PASSWORD = "MonoBlueTempo";
	
	/* ===== Fields ================ */
	
	private Connection conn;
	
	/* ===== Constructor =========== */
	
	public PostgreSQLDataSource(Connection conn) {
		
		this.conn = conn;
	
	}
	
	@Override
	public Map<String, Student> loadAllStudents() throws DataException {
		
		Map<String, Student> students = new TreeMap<>();
		
		try (Statement statement = conn.createStatement();){
			
			final String SQL = "SELECT * FROM student";
			try (ResultSet result = statement.executeQuery(SQL);) {
				
				while (result.next()) {
					
					String studentId = result.getString("id");
					String studentName = result.getString("name");
					String deptName = result.getString("dept_name");
					int totalCredits = result.getInt("tot_cred");
					
					students.put(studentId, new Student(studentId, studentName, deptName, totalCredits));
					
				}
			
			}
			
		}
		catch (SQLException exception) {
			
			throw new DataException(exception);
		
		}
		
		return students;
		
	}

	@Override
	public void createStudent(Student student) throws DataException {
		
		Map<String, Student> students = loadAllStudents();
		if (students.containsKey(student.getId())) {
			
			throw new DataException("the database already contains a student with ID" + student.getId());
		
		}
			
		final String SQL = "INSERT INTO student (id, name, dept_name, tot_cred) VALUES (?, ?, ?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(SQL);) {
				
			statement.setString(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getDeptName());
			statement.setInt(4, student.getTotCredits());
				
			statement.executeUpdate();
					
		}
		catch (SQLException exception) {
			
			throw new DataException(exception);
			
		}
		
	}

	@Override
	public void updateStudent(Student student) throws DataException {
				
		Map<String, Student> students = loadAllStudents();
		if (!students.containsKey(student.getId())) {
			
			throw new DataException("the database does not contain a student with ID" + student.getId());
		
		}
			
		final String SQL = "UPDATE student SET name = ?, dept_name = ?, tot_cred = ? WHERE id = ?";
		try (PreparedStatement statement = conn.prepareStatement(SQL);) {
			
			statement.setString(1, student.getName());
			statement.setString(2, student.getDeptName());
			statement.setInt(3, student.getTotCredits());
			statement.setString(4, student.getId());
			
			statement.executeUpdate();
				
		}
		catch (SQLException exception) {
		
			throw new DataException(exception);
		
		}
		
	}

	@Override
	public void deleteStudent(Student student) throws DataException {
		
		Map<String, Student> students = loadAllStudents();
		if (!students.containsKey(student.getId())) {
			
			throw new DataException("the database does not contain a student with ID" + student.getId());
		
		}
		
		final String SQL = "DELETE FROM student WHERE id = ?";
		try(PreparedStatement statement = conn.prepareStatement(SQL);) {
			
			statement.setString(1, student.getId());
			
			statement.executeUpdate();
			
		}
		catch (SQLException exception) {
			
			throw new DataException(exception);
			
		}
	}

}
