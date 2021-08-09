package edu.millersville.csci366.studentslab.startercode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import edu.millersville.csci366.studentslab.nrbellma.Student;

/**
 * A view of student data written with the Swing GUI library.
 * It's been about 15 years since I last wrote a Swing application, so I'm sure this could be much better ...
 * 
 * @author Chad Hogg
 * @version 2021-03-25
 */
public class SwingStudentView implements StudentView {
	
	/** 
	 * The next action the user wishes to take.
	 * This will be null when the system is idle, and the various GUI components will set it when buttons are pressed.
	 */
	private Action nextAction;
	
	/** The main window of the application. */
	private JFrame frame;
	
	/** The model underlying the table. */
	private MyTableModel tableModel;
	
	/** A text field into which the user can type the ID of a new student. */
	private JTextField newIDText;
	/** A text field into which the user can type the name of a new student. */
	private JTextField newNameText;
	/** A text field into which the user can type the department name of a new student. */
	private JTextField newDeptNameText;
	/** A spinner into which the user can enter the credits of a new student. */
	private JSpinner newCreditsSpinner;
	/** A text field into which the user can type the ID of a student to be deleted. */
	private JTextField deleteIDText;
	
	/** The student that has been edited -- will be null except for brief periods of time. */
	private Student editedStudent;
	
	/**
	 * Constructs a new SwingStudentView.
	 */
	public SwingStudentView() {
		nextAction = null;
		tableModel = new MyTableModel();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	/**
	 * Builds the window.
	 */
	private void createAndShowGUI() {
		// The JFrame is the main application window.
		frame = new JFrame("University Students");
		
		// This allows us to discover QUIT actions.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				nextAction = Action.QUIT;
			}
		});

		// A pane containing an editable table showing all of the existing students.
		JTable table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(450,200));
        table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, BorderLayout.PAGE_START);
		
		JPanel deletePanel = new JPanel();
		deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Student"));
		JLabel deleteIDLabel = new JLabel("ID");
		deletePanel.add(deleteIDLabel);
		deleteIDText = new JTextField();
		deleteIDText.setColumns(5);
		deletePanel.add(deleteIDText);
		JButton deleteButton = new JButton("Delete Student");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextAction = Action.DELETE;
			}
		});
		deletePanel.add(deleteButton);
		frame.getContentPane().add(deletePanel, BorderLayout.CENTER);
		
		// A panel containing all components needed for creating a new student.
		JPanel newPanel = new JPanel();
		newPanel.setBorder(BorderFactory.createTitledBorder("Create New Student"));
		JLabel newIDLabel = new JLabel("ID");
		newPanel.add(newIDLabel);
		newIDText = new JTextField();
		newIDText.setColumns(5);
		newPanel.add(newIDText);
		JLabel newNameLabel = new JLabel("Name");
		newPanel.add(newNameLabel);
		newNameText = new JTextField();
		newNameText.setColumns(10);
		newPanel.add(newNameText);
		JLabel newDeptNameLabel = new JLabel("Department");
		newPanel.add(newDeptNameLabel);
		newDeptNameText = new JTextField();
		newDeptNameText.setColumns(10);
		newPanel.add(newDeptNameText);
		JLabel newTotCreditsLabel = new JLabel("Credits");
		newPanel.add(newTotCreditsLabel);
		newCreditsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		newPanel.add(newCreditsSpinner);
		JButton newButton = new JButton("New Student");
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextAction = Action.NEW;
			}
		});
		newPanel.add(newButton);
		frame.getContentPane().add(newPanel, BorderLayout.PAGE_END);
		
		
		// Places things on the window and makes it visible.
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void showAllStudents(Map<String, Student> students) {
		tableModel.refreshData(students.values());
	}

	@Override
	public Student getNewStudentDetails() {
		nextAction = null;
		String id = newIDText.getText();
		String name = newNameText.getText();
		String deptName = newDeptNameText.getText();
		int credits = (Integer)newCreditsSpinner.getValue();
		newIDText.setText("");
		newNameText.setText("");
		newDeptNameText.setText("");
		newCreditsSpinner.setValue(0);
		return new Student(id, name, deptName, credits);
	}

	@Override
	public void notifyCreateFailure(Student failedCreation, String reason) {
		JOptionPane.showMessageDialog(frame, "Could not create student " + failedCreation.getId() + " because " + reason);
	}

	@Override
	public String getIdToDelete() {
		nextAction = null;
		String id = deleteIDText.getText();
		deleteIDText.setText("");
		return id;
	}

	@Override
	public void notifyDeletionFailure(String id, String reason) {
		JOptionPane.showMessageDialog(frame, "Could not delete student " + id + " because " + reason);
	}

	@Override
	public String getIdToEdit() {
		return editedStudent.getId();
	}

	@Override
	public void editStudent(Student student) {
		nextAction = null;
		student.setName(editedStudent.getName());
		student.setDeptName(editedStudent.getDeptName());
		student.setTotCredits(editedStudent.getTotCredits());
		editedStudent = null;
	}

	@Override
	public void notifyEditFailure(String id, String reason) {
		JOptionPane.showMessageDialog(frame, "Could not edit student " + id + " because " + reason);
	}

	@Override
	public Action getNextAction() {
		// Trying to make our view interface sensible for console applications makes it weird for GUI applications.
		// Maybe there's a better way to do this?
		// I essentially want to wait until an event occurs.
		while(nextAction == null) {
			try {
				Thread.sleep(100);
			}
			catch(InterruptedException exception) {
				// who cares?
			}
		}
		return nextAction;
	}

	@Override
	public void notifyLoadFailure(String reason) {
		JOptionPane.showMessageDialog(frame, "Could not load students because " + reason);
	}
	
	/**
	 * A way to track what data is displayed in the table.
	 * 
	 * @author Chad Hogg
	 * @version 2021-03-25
	 */
	private class MyTableModel extends AbstractTableModel {

		/** A unique version ID for serialization. */
		private static final long serialVersionUID = -7694499919614209822L;
		
		/** A list of all students. */
		private List<Student> students;
		
		/** Constructs a new model with no students. */
		public MyTableModel() {
			students = new ArrayList<>();
		}

		/**
		 * Updates the model with a new batch of students.
		 * 
		 * @param students The students that should be displayed.
		 */
		public void refreshData(Collection<Student> students) {
			this.students = new ArrayList<>(students);
			this.fireTableDataChanged();
		}
		
		@Override
		public int getRowCount() {
			return students.size();
		}
		
		@Override
		public int getColumnCount() {
			return 4;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			switch(column) {
			case 0:
				return students.get(row).getId();
			case 1:
				return students.get(row).getName();
			case 2:
				return students.get(row).getDeptName();
			case 3:
				return students.get(row).getTotCredits();
			default:
				return null;
			}
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch(columnIndex) {
			case 0:
			case 1:
			case 2:
				return String.class;
			case 3:
				return int.class;
			default:
				return null;
			}
		}
		
		@Override
		public String getColumnName(int column) {
			switch(column) {
			case 0:
				return "ID";
			case 1:
				return "Name";
			case 2:
				return "Department";
			case 3:
				return "Credits";
			default:
				return null;
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex != 0;
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			switch(columnIndex) {
			case 1:
				students.get(rowIndex).setName((String)aValue); break;
			case 2:
				students.get(rowIndex).setDeptName((String)aValue); break;
			case 3:
				students.get(rowIndex).setTotCredits((Integer)aValue); break;
			}
			editedStudent = students.get(rowIndex);
			nextAction = Action.EDIT;
		}
	}
	
}
