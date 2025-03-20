package com.example.dynamicQ.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.dynamicQ.contents.QuesType;
import com.example.dynamicQ.contents.ResMessage;
import com.example.dynamicQ.dao.QuestionDao;
import com.example.dynamicQ.dao.QuizDao;
import com.example.dynamicQ.entity.Question;
import com.example.dynamicQ.entity.Quiz;
import com.example.dynamicQ.service.ifs.QuizService;

import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.CreateReq;
import com.example.dynamicQ.vo.DeleteReq;
import com.example.dynamicQ.vo.GetQuesRes;
import com.example.dynamicQ.vo.GetQuizRes;
import com.example.dynamicQ.vo.QuizSearchRes;
import com.example.dynamicQ.vo.SearchReq;
import com.example.dynamicQ.vo.SearchVo;
import com.example.dynamicQ.vo.UpdateReq;

import jakarta.transaction.Transactional;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

	// @Transactional:因為同時新增問卷與問題是一次的行為，要嘛全成功，要嘛全失敗
	// / rollbackOn = Exception.class: 指定@Transactional 資料回朔有效的例外層級
	// 發生例外(Exception)是 RuntimeException 或其子類別時，@Transactional 才會讓資料回朔，
	// 藉由 rollbackOn 可以指定發生哪個例外時，就可以讓資料回朔

	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes create(CreateReq req) {

		BasicRes checkRes = checkParam(req);
		if (checkRes != null) {
			return checkRes;
		}

		// 因為 quiz 的 PK 是流水號，不會重複寫入，所以不用檢查資料料庫是否已存在相同的 PK
		// 因為 Quiz 中的 id 是 AI 自動生成的流水號，要讓 quizDao 執行 save 後可以把該 id 的值回傳，
		// 必須要在 Quiz 此 Entity 中將資料型態為 int 的屬性 id
		// 加上 @GeneratedValue(strategy = GenerationType.IDENTITY)
		// JPA 的 save，PK 已存在於 DB，會執行 update，若PK不存在，則會執行 insert

		// 新增問卷
		// 增加新增資料失敗:使用try-catch，包含 @Transactional 的資料回溯層級
		try {
			Quiz quiz = quizDao.save(new Quiz(req.getqName(), req.getqExplain(), req.getStartTime(), req.getEndTime(),
					req.getqSituation()));

			// 把 問卷的id 塞到question中的QID
			for (Question item : req.getQuestionList()) {
				item.setQzId(quiz.getId());// setQID是question中設定問卷ID的方法
			}

			// 新增問題(多筆)
			questionDao.saveAll(req.getQuestionList());
			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			return new BasicRes(ResMessage.DATA_SAVE_ERROR.getCode(), ResMessage.DATA_SAVE_ERROR.getMessage());
		}

	}

	// ===================================================================================

	// 檢查參數
	private BasicRes checkParam(CreateReq req) {
		// 問卷名稱
		if (!StringUtils.hasText(req.getqName())) {
			return new BasicRes(ResMessage.PARAM_QNAME_ERROR.getCode(), ResMessage.PARAM_QNAME_ERROR.getMessage());
		}

//		// 問卷說明
//		if (!StringUtils.hasText(req.getQexplain())) {
//			return new BasicRes(ResMessage.PARAM_QEXPLAIN_ERROR.getCode(),
//					ResMessage.PARAM_QEXPLAIN_ERROR.getMessage());
//		}

		// 開始時間
		if (req.getStartTime() == null) {
			return new BasicRes(ResMessage.PARAM_STARTTIME_ERROR.getCode(),
					ResMessage.PARAM_STARTTIME_ERROR.getMessage());
		}

		// 結束時間
		if (req.getStartTime() == null) {
			return new BasicRes(ResMessage.PARAM_ENDTIME_ERROR.getCode(), ResMessage.PARAM_ENDTIME_ERROR.getMessage());
		}

		// 開始時間不能比結束時間晚
		// 開始時間.isAfter(結束時間)
		if (req.getStartTime().isAfter(req.getEndTime())) {
			return new BasicRes(ResMessage.PARAM_TIME_ERROR.getCode(), ResMessage.PARAM_TIME_ERROR.getMessage());
		}

		// 問題題數
		List<Question> quesList = req.getQuestionList();

		if (quesList.size() <= 0) {
			return new BasicRes(ResMessage.PARAM_QUES_LIST_ERROR.getCode(),
					ResMessage.PARAM_QUES_LIST_ERROR.getMessage());
		}

		for (Question item : quesList) {
			// 問題編號一定是從1開始，但無法檢查是否有按照順序以及中間使否有空缺的編號
			if (item.getQuesID() <= 0) {
				return new BasicRes(ResMessage.PARAM_QUESID_ERROR.getCode(),
						ResMessage.PARAM_QUESID_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getContent())) {
				return new BasicRes(ResMessage.PARAM_QUESCONTENT_ERROR.getCode(),
						ResMessage.PARAM_QUESCONTENT_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getType())) {
				return new BasicRes(ResMessage.PARAM_TYPE_ERROR.getCode(), ResMessage.PARAM_TYPE_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getOptions())) {
				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(),
						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
			}

			// 檢查type類型是否為單選、多選、文字
			if (!QuesType.checkType(item.getType())) {
				return new BasicRes(ResMessage.QUES_TYPE_MISMATCH.getCode(),
						ResMessage.QUES_TYPE_MISMATCH.getMessage());
			}

//			// 文字類型時option不能有值
//			if (item.getType().equalsIgnoreCase(QuesType.TEXT.getType()) && StringUtils.hasText(item.getOptions())) {
//				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(),
//						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
//			}

			// type為單選、多選題時，options要有值
			if (!item.getType().equalsIgnoreCase(QuesType.TEXT.getType()) && !StringUtils.hasText(item.getOptions())) {
				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(),
						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
			}

		}

		return null;
	}

	// 撈所有問卷資料
	@Override
	public QuizSearchRes getAllQuiz() {

		List<Quiz> res = quizDao.getAllQuiz();
		return new QuizSearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
	}

	// 時間範圍搜尋
	@Override
	public QuizSearchRes getQuiz(SearchReq req) {
		// 檢查參數
		// 改變條件值:如果name是null一律轉成空字串
		String searchname = req.getSearchname();
		if (!StringUtils.hasText(searchname)) {
			searchname = "";
		}
		LocalDate startTime = req.getStartTime();
		if (startTime == null) { // 表示此欄位前端回傳沒有帶值
			startTime = LocalDate.of(1970, 1, 1);// 把時間指定到一個很早的時間點
		}

		LocalDate endTime = req.getEndTime();
		if (endTime == null) { // 表示此欄位前端回傳沒有帶值
			endTime = LocalDate.of(2999, 12, 31);// 把時間指定到一個很久之後的時間點
		}

		List<Quiz> res = quizDao.getQuiz(startTime, endTime);
		return new QuizSearchRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);

	}

	// 用問卷ID撈問題資料
	@Override
	public GetQuesRes getQuesByQuizID(int Qid) {
		if (Qid <= 0) {
			return new GetQuesRes(ResMessage.PARAM_QUIZID_ERROR.getCode(), ResMessage.PARAM_QUIZID_ERROR.getMessage());
		}
		List<Question> res = questionDao.getByQuizID(Qid);
		return new GetQuesRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
	}

	// 刪除問卷
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes delete(DeleteReq req) {

		try {
			quizDao.deleteByQuizIdIn(req.getQuizIdList());
			questionDao.deleteByQuizIdIn(req.getQuizIdList());

		} catch (Exception e) {
			return new BasicRes(ResMessage.DATA_DELETE_ERROR.getCode(), ResMessage.DATA_DELETE_ERROR.getMessage());

		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	// 更新問卷
	@Override
	public BasicRes update(UpdateReq req) {
		// 檢查回傳的各種參數
		BasicRes checkRes = checkParam(req);
		if (checkRes != null) {
			return checkRes;
		}

		// 檢查該問卷id對應資料是否存在
		int count = quizDao.selectCount(req.getQuizID());
		if (count != 1) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		// 檢查問題的id是否與問卷的ID相符
		for (Question item : req.getQuestionList()) {
			if (req.getQuizID() != item.getQzId()) {
				return new BasicRes(ResMessage.QUIZ_ID_MISMATCH.getCode(), ResMessage.QUIZ_ID_MISMATCH.getMessage());
			}
		}

		// 更新(問題更新直接打掉重來最快)
		try {
			// 1.更新quiz by quizID
			quizDao.updateQuiz(req.getqName(), req.getqExplain(), req.getStartTime(), req.getEndTime(),
					req.getqSituation(), req.getQuizID());

			// 2.刪除question by quizID
			questionDao.deleteByQuizIdIn(List.of(req.getQuizID()));

			// 新增question
			questionDao.saveAll(req.getQuestionList());

			return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
		} catch (Exception e) {
			return new BasicRes(ResMessage.DATA_UPDATE_ERROR.getCode(), ResMessage.DATA_UPDATE_ERROR.getMessage());
		}

	}

//	@Override
//	public GetQuizRes getQuizByID(int Qid) {
//
//		// 檢查ID參數
//		if (Qid <= 0) {
//			return new GetQuizRes(ResMessage.PARAM_QUIZID_ERROR.getCode(), ResMessage.PARAM_QUIZID_ERROR.getMessage());
//		}
//
//		// 檢查該ID有無對應資料
//		int count = quizDao.selectCount(Qid);
//		if (count != 1) {
//			return new GetQuizRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
//		}
//
//		// 抓問卷資料
//		SearchVo res = quizDao.getByID(Qid);
//		return new GetQuizRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), res);
//
//	}

}
