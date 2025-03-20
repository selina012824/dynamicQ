package com.example.dynamicQ.vo;


import java.util.List;

import com.example.dynamicQ.entity.Quiz;

public class QuizSearchRes extends BasicRes{
	
	private List<Quiz> quizList;

	public QuizSearchRes() {
		super();
	}
    
	
	//失敗時表的資料不會回傳，所以只傳錯誤代碼與訊息
	public QuizSearchRes(int code, String message) {
		super(code, message);
	}
    
	
	//成功時，表資料回傳，所以多一個放表資料的變數
	public QuizSearchRes(int code, String message,List<Quiz> quizList) {
		super(code, message);
		this.quizList = quizList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}

  
}
