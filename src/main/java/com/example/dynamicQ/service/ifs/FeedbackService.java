package com.example.dynamicQ.service.ifs;

import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.FeedbackRes;
import com.example.dynamicQ.vo.FillinReq;
import com.example.dynamicQ.vo.StatisticsRes;

public interface FeedbackService {
	
	public BasicRes fillin(FillinReq req);
	
	
	public FeedbackRes feedback(int quizId) ;
	
	public StatisticsRes statistics(int quizId);

}
