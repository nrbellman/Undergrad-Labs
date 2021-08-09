package edu.millersville.csci366.studentslab.nrbellma;

public class Student {
	
	/* ===== Fields ================ */
	
	private String ID;
	private String name;
	private String departmentName;
	private int totalCredits;
	
	/* ===== Constructors ========== */
	
	public Student(String ID, String name, String departmentName, int totalCredits) {
		this.ID = ID;
		this.name = name;
		this.departmentName = departmentName;
		this.totalCredits = totalCredits;
	}
	
	/* ===== Getters =============== */
	
	public String getId() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDeptName() {
		return departmentName;
	}
	
	public int getTotCredits() {
		return totalCredits;
	}
	
	/* ===== Setters =============== */
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setDeptName(String newDepartmentName) {
		departmentName = newDepartmentName;
	}
	
	public void setTotCredits(int newTotalCredits) {
		totalCredits = newTotalCredits;
	}

}
