package com.example.dynamicQ.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamicQ.contents.ResMessage;
import com.example.dynamicQ.entity.Account;
import com.example.dynamicQ.service.ifs.AccountService;
import com.example.dynamicQ.service.ifs.FeedbackService;
import com.example.dynamicQ.service.ifs.QuizService;
import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.CreateReq;
import com.example.dynamicQ.vo.DeleteReq;
import com.example.dynamicQ.vo.FeedbackRes;
import com.example.dynamicQ.vo.FeedbackVo;
import com.example.dynamicQ.vo.FillinReq;
import com.example.dynamicQ.vo.GetQuesRes;
import com.example.dynamicQ.vo.LogInReq;
import com.example.dynamicQ.vo.QuizSearchRes;
import com.example.dynamicQ.vo.SearchReq;
import com.example.dynamicQ.vo.StatisticsRes;
import com.example.dynamicQ.vo.UpdateReq;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
public class QuizServiceController {
	
	//req是要從前端接收的資料，前面是要接收資料的資料型態定義在vo
	//res是要回傳給前端的資料

	@Autowired
	private QuizService quizService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private AccountService accountService;

	@PostMapping(value = "quiz/create")
	public BasicRes create( @RequestBody CreateReq req) {
		return quizService.create(req);
	}

	// getAll不需要接收任何資訊所以括號不用放東西
	@GetMapping(value = "quiz/get_all_quiz")
	public QuizSearchRes getAllQuiz() {
		return quizService.getAllQuiz();
	}
	

	
	@PostMapping(value = "quiz/search_quiz")
	public QuizSearchRes getQuiz(@RequestBody SearchReq req) {
		return quizService.getQuiz(req);
	}

	// 呼叫Api的路徑:http://localhost:8080/quiz/get_question?Qid=1(指定值)
	// 路徑?後面名稱得與變數名稱一樣
	@PostMapping(value = "quiz/get_question")
	public GetQuesRes getQuesByQuizID(@RequestBody int Qid) {
		return quizService.getQuesByQuizID(Qid);
	}

	// 呼叫Api的路徑:http://localhost:8080/quiz/get_question?quiz_id=1(指定值)
	// @RequestParam 中的 value，用來指定並對應路經?後面的名稱，並將路徑等號後面的值塞到方法的變數名稱中
	@PostMapping(value = "quiz/get_question_list")
	public GetQuesRes getQuesListByQuizID(@RequestParam(value = "quiz_id") int Qid) {
		return quizService.getQuesByQuizID(Qid);
	}

	// ===================================================
	// 多個參數使用 @RequestParam
	// 呼叫API的路徑: http://localhost:8080/quiz/search?name=AAA&start_date=2024-12-01
	@GetMapping(value = "quiz/search")
	public QuizSearchRes search( //
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return null;
	}

	// ==================================

	@PostMapping(value = "quiz/delete")
	public BasicRes delete(@RequestBody DeleteReq req) {
		return quizService.delete(req);
	}
	
	@PostMapping(value = "quiz/update")
	public BasicRes update(@RequestBody UpdateReq req) {
		return quizService.update(req);
	}
	
	@PostMapping(value = "quiz/fillIn")
	public BasicRes fillIn(@RequestBody FillinReq req) {
		return feedbackService.fillin(req);
	}
	
	@PostMapping(value = "quiz/feedback")
	public FeedbackRes feedback(@RequestBody int req) {
		FeedbackRes res = feedbackService.feedback(req);
		return res;
	}
	
	@PostMapping(value = "quiz/status")
    public StatisticsRes statusLook(@RequestBody int req) {
		return feedbackService.statistics(req);
	}
	
	// ==================================
	
	@PostMapping(value = "quiz/account_create")
	public BasicRes createAccount(@RequestBody Account req) {
		return accountService.createAccount(req);
	}
	
	@PostMapping(value = "quiz/logIn")
	public BasicRes logIn(@RequestBody LogInReq req, HttpSession httpSession) {
		//先執行登入
		BasicRes res = accountService.logIn(req);
		
	    //登入成功後，設定httpSession 的屬性
		// 設定 httpSession 的屬性
		httpSession.setAttribute("account", req.getEmail());
		// 設定session 的存活時間
		//   1.預設存活時間30分鐘
		//   2.小括號中的單位是秒，0或負數則代表該session 永遠不會過期
		httpSession.setMaxInactiveInterval(300);
		
		//取得session id
		System.out.println(httpSession.getId());
		
		return accountService.logIn(req);
	}
	
	@GetMapping("quiz/checkLogin")
	public BasicRes checkLogin(HttpSession httpSession) {
		String logIn = (String) httpSession.getAttribute("account");
		if(logIn != null) {
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		}
		
		return null;
	}
	
	@PostMapping(value="quiz/logOut")
	public BasicRes logOut(HttpSession httpSession) {
			//登出(將session關閉)
			httpSession.invalidate();
			
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
			
		}
	
	
}
