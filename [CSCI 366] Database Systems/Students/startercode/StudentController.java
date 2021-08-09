package edu.millersville.csci366.studentslab.startercode;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * A class that is responsible for converting user interactions into data operations.
 * 
 * @author Chad Hogg
 * @version 2021-03-24
 */
public class StudentController {

	/** The model that will be used. */
	private StudentModel model;
	/** The view that will be used. */
	private StudentView view;
	
	/**
	 * Constructs a new Controller.
	 * 
	 * @param model The model that should be used.
	 * @param view The view that should be used.
	 */
	public StudentController(StudentModel model, StudentView view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Creates a new student.
	 */
	private void doNew() {
		Student details = view.getNewStudentDetails();
		try {
			model.newStudent(details);
		}
		catch (DataException exception) {
			view.notifyCreateFailure(details, exception.getMessage());
		}
	}
	
	/**
	 * Edits a Student.
	 */
	private void doEdit() {
		String id = view.getIdToEdit();
		Student thisStudent = model.getStudent(id);
		if(thisStudent != null) {
			Student copy = new Student(id, thisStudent.getName(), thisStudent.getDeptName(), thisStudent.getTotCredits());
			view.editStudent(copy);
			try {
				model.modifiedStudent(copy);
			}
			catch (DataException exception) {
				view.notifyEditFailure(id, exception.getMessage());
			}
		} else {
			view.notifyEditFailure(id, "that student does not exist.");
		}
	}
	
	/**
	 * Deletes a Student.
	 */
	private void doDelete() {
		String id = view.getIdToDelete();
		try {
			model.deleteStudent(id);
		}
		catch (DataException exception) {
			view.notifyDeletionFailure(id, exception.getMessage());
		}
	}
	
	/**
	 * [Re-]loads all students from the data source.
	 */
	private void doLoad() {
		try {
			model.loadStudents();
		}
		catch (DataException exception) {
			view.notifyLoadFailure(exception.getMessage());
		}
	}

	/**
	 * Runs the program.
	 */
	public void run() {
		doLoad();
		Action action = null;
		do {
			view.showAllStudents(model.getStudents());
			action = view.getNextAction();
			switch(action) {
			case NEW:
				doNew();
				break;
			case EDIT:
				doEdit();
				break;
			case DELETE:
				doDelete();
				break;
			case REFRESH:
				doLoad();
				break;
			case QUIT:
				break;
			}
		} while(!action.equals(Action.QUIT));
	}
}
