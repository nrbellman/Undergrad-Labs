package edu.millersville.csci366.studentslab.nrbellma;

import java.util.Map;
import java.util.Scanner;

import edu.millersville.csci366.studentslab.startercode.*;

public class ConsoleStudentView implements StudentView {
	
	private static final int ID_COLUMN_LENGTH = 5;
	private static final int NAME_COLUMN_LENGTH = 20;
	private static final int DEPT_COLUMN_LENGTH = 20;
	private static final int CREDITS_COLUMN_LENGTH = 3;

	private static void printSpaces(int wordLength, int colLength) {
		
		for (int i = Math.abs(wordLength - colLength); i > 0; i--) {
			System.out.print(" ");
		}
		
	}
	
	private static void printStudentLine(Student student) {
		
		String studentID = student.getId();
		String studentName = student.getName();
		String studentDeptName = student.getDeptName();
		String studentTotCredits = String.valueOf(student.getTotCredits());
		
		printSpaces(studentID.length(), ID_COLUMN_LENGTH);
		System.out.print(studentID);
		
		System.out.print(" | ");
		printSpaces(studentName.length(), NAME_COLUMN_LENGTH);
		System.out.print(studentName);
		
		System.out.print(" | ");
		printSpaces(studentDeptName.length(), DEPT_COLUMN_LENGTH);
		System.out.print(studentDeptName);
		
		System.out.print(" | ");
		printSpaces(studentTotCredits.length(), CREDITS_COLUMN_LENGTH);
		System.out.print(studentTotCredits);
		
		System.out.println();
		
	}
	
	@Override
	public void showAllStudents(Map<String, Student> students) {
		
		System.out.println("   ID |                 Name |           Department | Credits");
		
		for (Student student : students.values()) {
		
			printStudentLine(student);
			
		}
		
	}

	@Override
	public Student getNewStudentDetails() {
		
		String studentId = "";
		String studentName = "";
		String studentDeptName = "";
		int studentTotCredits = 0;
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("ID: ");
		studentId = input.next();
		
		System.out.print("Name: ");
		studentName = input.next();
		
		System.out.print("Department: ");
		studentDeptName = input.next();
		
		System.out.print("Credits: ");
		studentTotCredits = input.nextInt();

		return new Student(studentId, studentName, studentDeptName, studentTotCredits);
		
	}

	@Override
	public void notifyCreateFailure(Student failedCreation, String reason) {

		System.out.println("Could not create student " + failedCreation.getId() + "because" + reason);
		
	}

	@Override
	public String getIdToDelete() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Which student (ID) to delete: ");
		String studentId = input.next();
		
		return studentId;
		
	}

	@Override
	public void notifyDeletionFailure(String id, String reason) {

		System.out.println("Could not delete student " + id + " because " + reason);
		
	}

	@Override
	public String getIdToEdit() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.print("Which student (ID) to edit: ");
		String studentId = input.next();
		
		return studentId;
	
	}

	@Override
	public void editStudent(Student student) {
		
		Scanner input = new Scanner(System.in);
		Student tempStudent = student;
		String choice = "";
		
		while (!choice.toLowerCase().equals("save") || !choice.toLowerCase().equals("cancel")) {
			
			printStudentLine(tempStudent);
			System.out.print("Select to edit name, department, credits, save, or cancel: ");
			choice = input.next();
			
			if (choice.toLowerCase().equals("name")) {
				System.out.print("New Name: ");
				String newName = input.next();
				tempStudent.setName(newName);
			}
			else if (choice.toLowerCase().equals("department")) {
				System.out.print("New Department: ");
				String newDept = input.next();
				tempStudent.setDeptName(newDept);
			}
			else if (choice.toLowerCase().equals("credits")) {
				System.out.print("New Credits: ");
				int newCredits = input.nextInt();
				tempStudent.setTotCredits(newCredits);
			}
			else {
				System.out.println("Invalid choice, try again.");
			}
			
		}
		
		if (choice.toLowerCase().equals("save")) {
			
			student = tempStudent;
			
		}
		
	}

	@Override
	public void notifyEditFailure(String id, String reason) {

		System.out.println("Could not edit student " + id + " because " + reason);
		
	}

	@Override
	public Action getNextAction() {
		
		Action nextAction;
		Scanner input = new Scanner(System.in);
		
		String action = "";
		while (!action.toLowerCase().equals("new") && !action.toLowerCase().equals("edit") && 
			   !action.toLowerCase().equals("delete") && !action.toLowerCase().equals("refresh") &&
			   !action.toLowerCase().equals("quit")) {
			
			System.out.print("Type one of 'new', 'edit', 'delete', 'refresh', or 'quit': ");
			action = input.next();
		
		}
		
		if (action.toLowerCase().equals("new")) {
			nextAction = Action.NEW;
		}
		else if (action.toLowerCase().equals("edit")) {
			nextAction = Action.EDIT;
		}
		else if (action.toLowerCase().equals("delete")) {
			nextAction = Action.DELETE;
		}
		else if (action.toLowerCase().equals("refresh")) {
			nextAction = Action.REFRESH;
		}
		else {
			nextAction = Action.QUIT;
		}
		
		return nextAction;
		
	}

	@Override
	public void notifyLoadFailure(String reason) {

		System.out.println("Could not load students because " + reason);
		
	}

}
