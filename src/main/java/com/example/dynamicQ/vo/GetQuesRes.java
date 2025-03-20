package com.example.dynamicQ.vo;

import java.util.List;

import com.example.dynamicQ.entity.Question;

public class GetQuesRes extends BasicRes{
	
	private List<Question> quesList;

	public GetQuesRes() {
		super();
	}
	
	public GetQuesRes(int code, String message) {
		super(code, message);
	}

	public GetQuesRes(int code, String message,List<Question> quesList) {
		super(code, message);
		this.quesList = quesList;
	}

	public GetQuesRes(List<Question> quesList) {
		super();
		
	}

	public List<Question> getQuesList() {
		return quesList;
	}
	
	
	
	
	
	

}
