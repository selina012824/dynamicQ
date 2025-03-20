package com.example.dynamicQ.vo;

import java.time.LocalDate;

//用於將多張表資料撈回來後的裝載容器(因為要進行整理)
public class FeedbackDto {

	private String qName;

	private String explain;

	private String userName;

	private String email;

	private String gender;

	private int age;

	private String answer;

	private LocalDate fillinDate;

	private int quesID;

	private String quesName;

	public FeedbackDto() {
		super();
	}

	public FeedbackDto(String qName, String explain, String userName, String email, int age, String gender,
			String answer, LocalDate fillinDate, int quesID, String quesName) {
		super();
		this.qName = qName;
		this.explain = explain;
		this.userName = userName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.answer = answer;
		this.fillinDate = fillinDate;
		this.quesID = quesID;
		this.quesName = quesName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	public int getQuesID() {
		return quesID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	public String getQuesName() {
		return quesName;
	}

	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}

}
