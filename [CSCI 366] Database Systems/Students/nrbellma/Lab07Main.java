package edu.millersville.csci366.studentslab.nrbellma;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import edu.millersville.csci366.studentslab.startercode.*;

public class Lab07Main {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		StudentController studentController;
		
		System.out.print("What would you like to use for student storage? (database/file): ");
		String answer = input.next();
		
		if (answer.toLowerCase().equals("database")) {
			
			try (Connection conn = DriverManager.getConnection(PostgreSQLDataSource.URL, PostgreSQLDataSource.USERNAME, 
															   PostgreSQLDataSource.PASSWORD);) {
				
				PostgreSQLDataSource SQLSource = new PostgreSQLDataSource(conn);
				StudentModel studentModelSQL = new StudentModel(SQLSource);
				StudentView studentView = chooseView(input);
				studentController = new StudentController(studentModelSQL, studentView);
				
				studentController.run();
				
			}
			catch (SQLException exception) {
				
				System.out.println("SQLException: " + exception.getMessage());
			
			}
			
		}
		else if (answer.toLowerCase().equals("file")) {
			
			System.out.print("Filename: ");
			String filename = input.next();
				
			CSVFileDataSource CSVSource = new CSVFileDataSource(filename);
			StudentModel studentModel = new StudentModel(CSVSource);
			StudentView studentView = chooseView(input);
			studentController = new StudentController(studentModel, studentView);
			
			studentController.run();
			
		}
		input.close();
		
	}
	
	public static StudentView chooseView(Scanner input) {
		
		StudentView studentView;
		
		System.out.print("Which view you you like to use? (console/Swing): ");
		String choice = input.next();
		
		if (choice.toLowerCase().equals("console")) {
			studentView = new ConsoleStudentView();
		}
		else {
			studentView = new SwingStudentView();
		}
		
		return studentView;
		
	}

}
