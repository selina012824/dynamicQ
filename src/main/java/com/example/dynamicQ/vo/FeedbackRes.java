package com.example.dynamicQ.vo;

import java.util.List;

public class FeedbackRes extends BasicRes{
	
	private List<FeedbackVo> feedbackVoList;//再將所有的回饋整理成一個List

	public FeedbackRes() {
		super();
	}

	public FeedbackRes(int code, String message) {
		super(code, message);
	}
	
	public FeedbackRes(int code, String message,List<FeedbackVo> feedbackVoList) {
		super(code, message);
		this.feedbackVoList = feedbackVoList;
	}

	public FeedbackRes(List<FeedbackVo> feedbackVoList) {
		super();
		this.feedbackVoList = feedbackVoList;
	}

	public List<FeedbackVo> getFeedbackVoList() {
		return feedbackVoList;
	}
	
	
	
	
	
	
   
}
