package com.example.dynamicQ.vo;

import java.time.LocalDate;
import java.util.List;


//回饋需要的所有參數(整理完)
public class FeedbackVo {
	
	private int quizID;
  
	private String qName;

	private String explain;

	private String userName;

	private String email;

	private int age;
	
	private String gender;

	private List<OptionAnswer> optionAnswerList;//option相關需要欄位整理在這

	private LocalDate fillinDate;

	public int getQuizID() {
		return quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
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

	public List<OptionAnswer> getOptionAnswerList() {
		return optionAnswerList;
	}

	public void setOptionAnswerList(List<OptionAnswer> optionAnswerList) {
		this.optionAnswerList = optionAnswerList;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
    
	

}
