package com.example.dynamicQ.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback")
@IdClass(value = FeedbackID.class)
public class Feedback {

	@Id
	@Column(name = "quiz_id")
	public int quizID;

	@Id
	@Column(name = "ques_id")
	public int quesID;

	@Column(name = "user_name")
	public String userName;

	@Column(name = "user_age")
	public int userAge;

	@Column(name = "gender")
	public String gender;

	@Column(name = "fillin_date")
	public LocalDate fillinDate;

	@Column(name = "answer")
	public String answer;

	@Id
	@Column(name = "email")
	public String email;


	public Feedback() {
		super();
	}

	public Feedback(int quizID, int quesID, String userName, int userAge, String gender, LocalDate fillinDate,
			String answer, String email) {
		super();
		this.quizID = quizID;
		this.quesID = quesID;
		this.userName = userName;
		this.userAge = userAge;
		this.gender = gender;
		this.fillinDate = fillinDate;
		this.answer = answer;
		this.email = email;
	}

	public int getQuizID() {
		return quizID;
	}

	public int getQuesID() {
		return quesID;
	}

	public String getUserName() {
		return userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	public String getAnswer() {
		return answer;
	}

	public String getEmail() {
		return email;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


}
