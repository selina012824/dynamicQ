package com.example.dynamicQ.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.dynamicQ.entity.Question;

public class UpdateReq extends CreateReq{
	
	private int quizID;

	public int getQuizID() {
		return quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public UpdateReq() {
		super();
	}

	public UpdateReq(String qName, String qExplain, LocalDate startTime, LocalDate endTime, Boolean qSituation,
			List<Question> questionList,int quizID) {
		super(qName, qExplain, startTime, endTime, qSituation, questionList);
		this.quizID = quizID;
	}

	public UpdateReq(int quizID) {
		super();
		this.quizID = quizID;
	}
	
	

}
