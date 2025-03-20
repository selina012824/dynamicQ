package com.example.dynamicQ.vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.dynamicQ.entity.Feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class FillinReq {

	public int quizID;

	public int quesID;

	public String quesType;

	public String userName;

	public int userAge;

	// 給定預設值(當前日期)，前端若沒有傳回值就會是預設值
	public LocalDate fillinDate = LocalDate.now();
	// 3: ["x", "y"]
	// 問題編號(quesID),答案(answer)
	public Map<Integer, List<String>> quesIDAnswerMap;

	public String email;

	public String gender;

	public FillinReq() {
		super();
	}

	public FillinReq(int quizID, int quesID, String quesType, String userName, int userAge, LocalDate fillinDate,
			Map<Integer, List<String>> quesIDAnswerMap, String email, String gender) {
		super();
		this.quizID = quizID;
		this.quesID = quesID;
		this.quesType = quesType;
		this.userName = userName;
		this.userAge = userAge;
		this.fillinDate = fillinDate;
		this.quesIDAnswerMap = quesIDAnswerMap;
		this.email = email;
		this.gender = gender;
	}

	public String getQuesType() {
		return quesType;
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

	public Map<Integer, List<String>> getQuesIDAnswerMap() {
		return quesIDAnswerMap;
	}

	public String getEmail() {
		return email;
	}

	public String getGender() {
		return gender;
	}

}
