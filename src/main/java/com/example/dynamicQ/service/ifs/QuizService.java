package com.example.dynamicQ.service.ifs;

import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.CreateReq;
import com.example.dynamicQ.vo.DeleteReq;
import com.example.dynamicQ.vo.GetQuesRes;
import com.example.dynamicQ.vo.QuizSearchRes;
import com.example.dynamicQ.vo.SearchReq;
import com.example.dynamicQ.vo.UpdateReq;


public interface QuizService {
	
	public BasicRes create(CreateReq req);
	
	public QuizSearchRes getAllQuiz();
	
	public QuizSearchRes getQuiz(SearchReq req);
	
	public GetQuesRes getQuesByQuizID(int Qid);
		
	
	public BasicRes delete(DeleteReq req);
	
	public BasicRes update(UpdateReq req);
	
	
}
