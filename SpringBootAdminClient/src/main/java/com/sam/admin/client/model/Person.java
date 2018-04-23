package com.sam.admin.client.model;

public class Person {
	
	//private Long id;
	private String fname;
	private String lname;
	private Long age;
	
	
	
	public Person(String fname, String lname, Long age) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.age = age;
	}
	/*public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}*/
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}

}
