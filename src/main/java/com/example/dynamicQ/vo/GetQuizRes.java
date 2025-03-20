package com.example.dynamicQ.vo;

import com.example.dynamicQ.entity.Quiz;

public class GetQuizRes extends BasicRes{
	
	private SearchVo quiz;

	public GetQuizRes() {
		super();
	}

	public GetQuizRes(int code, String message) {
		super(code, message);
	}
	
	public GetQuizRes(int code, String message,SearchVo quiz) {
		super(code, message);
		this.quiz = quiz;
	}

	public GetQuizRes(SearchVo quiz) {
		super();
		this.quiz = quiz;
	}

	public SearchVo getQuiz() {
		return quiz;
	}
	
	
	
	

}
