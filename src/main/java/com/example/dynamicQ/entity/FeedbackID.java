package com.example.dynamicQ.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeedbackID implements Serializable {

	public int quizID;
	public int quesID;
	public String email;

	public int getQuizID() {
		return quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public int getQuesID() {
		return quesID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
