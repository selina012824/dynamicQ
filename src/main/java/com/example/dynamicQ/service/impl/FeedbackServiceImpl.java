package com.example.dynamicQ.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.dynamicQ.contents.QuesType;
import com.example.dynamicQ.contents.ResMessage;
import com.example.dynamicQ.dao.FeedbackDao;
import com.example.dynamicQ.dao.QuestionDao;
import com.example.dynamicQ.dao.QuizDao;
import com.example.dynamicQ.entity.Feedback;
import com.example.dynamicQ.entity.Question;
import com.example.dynamicQ.entity.Quiz;
import com.example.dynamicQ.service.ifs.FeedbackService;
import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.FeedbackDto;
import com.example.dynamicQ.vo.FeedbackRes;
import com.example.dynamicQ.vo.FeedbackVo;
import com.example.dynamicQ.vo.FillinReq;
import com.example.dynamicQ.vo.OptionAnswer;
import com.example.dynamicQ.vo.OptionCount;
import com.example.dynamicQ.vo.QuizSearchRes;
import com.example.dynamicQ.vo.StatisticsDto;
import com.example.dynamicQ.vo.StatisticsRes;
import com.example.dynamicQ.vo.StatisticsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private FeedbackDao feedbackDao;

	private ObjectMapper mapper = new ObjectMapper();

	private BasicRes checkParam(FillinReq req) {
		// 排除法
		if (req.getQuizID() <= 0) {
			return new BasicRes(ResMessage.PARAM_QUIZID_ERROR.getCode(), ResMessage.PARAM_QUIZID_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getUserName())) {
			return new BasicRes(ResMessage.PARAM_USERNAME_ERROR.getCode(),
					ResMessage.PARAM_USERNAME_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ResMessage.PARAM_EMAIL_ERROR.getCode(), ResMessage.PARAM_EMAIL_ERROR.getMessage());
		}
		if (req.getUserAge() < 0) {
			return new BasicRes(ResMessage.PARAM_AGE_ERROR.getCode(), ResMessage.PARAM_AGE_ERROR.getMessage());
		}
		if (!StringUtils.hasText(req.getGender())) {
			return new BasicRes(ResMessage.PARAM_EMAIL_ERROR.getCode(), ResMessage.PARAM_EMAIL_ERROR.getMessage());
		}

		return null;
	}

	@Override
	public BasicRes fillin(FillinReq req) {
		// 1.檢查參數
		BasicRes checkRes = checkParam(req);

		// 2.檢查問卷是否存在與是否發佈
		if (quizDao.selectCountIfPublished(req.getQuizID()) != 1) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}

		// 3.檢查同一個 email 是否有填過同一張問卷
		if (feedbackDao.selectCount(req.getQuizID(), req.getEmail()) != 0) {
			return new BasicRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
		}

		// 檢查問題
		// 檢查填寫的日期是否在問卷可填寫的範圍內
		// (1.)利用quizID找出問卷(使用JPA方法)
		Optional<Quiz> op = quizDao.findById(req.getQuizID());

		// (2.)判斷被 Optional 包起來的Quiz物件是否有值
		if (op.isEmpty()) { // 表示取回來沒有資料
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		// 將 Quiz 從 Optional 中取出
		Quiz quiz = op.get();

		// (3.)檢查填寫的日期是否在問卷可填寫的萬圍內
		if (req.getFillinDate().isBefore(quiz.getStartTime()) || req.getFillinDate().isAfter(quiz.getEndTime())) {
			return new BasicRes(ResMessage.OUT_OF_FILLIN_TIME_RANGE.getCode(),
					ResMessage.OUT_OF_FILLIN_TIME_RANGE.getMessage());
		}

		ObjectMapper mapper = new ObjectMapper();
		// 4.比對相同題號填寫的答案是否包含在問卷選項裡(簡答除外)
		List<Question> quesList = questionDao.getByQuizID(req.getQuizID());
		Map<Integer, List<String>> quesIDAnswer = req.getQuesIDAnswerMap();

		for (Question item : quesList) {
			// 比對題號
			int quesNumber = item.getQuesID();
			List<String> answerList = quesIDAnswer.get(quesNumber);

			// 若該題是必填但沒有答案
			if (item.isMust() && CollectionUtils.isEmpty(answerList)) {
				return new BasicRes(ResMessage.ONLY_ONE_ANSWER_ALLOWED.getCode(),
						ResMessage.ONLY_ONE_ANSWER_ALLOWED.getMessage());
			}
			// 題目是單選或簡答時答案不能有多個
			String quesType = item.getType();
			if (quesType.equalsIgnoreCase(QuesType.SINGLE.getType())
					|| quesType.equalsIgnoreCase(QuesType.TEXT.getType())) {
				if (answerList.size() > 1) {
					return new BasicRes(ResMessage.ONLY_ONE_ANSWER_ALLOWED.getCode(),
							ResMessage.ONLY_ONE_ANSWER_ALLOWED.getMessage());
				}
			}

			// 題目類型是文本問題時之後程式碼不用做
			if (quesType.equalsIgnoreCase(QuesType.TEXT.getType())) {
				// continue:跳過當次迴圈
				// break:跳出全部迴圈
				continue;
			}

			// 將資料庫option型態轉成List<String>:前端格式["xx","yy"]

			try {
				List<String> options = mapper.readValue(item.getOptions(), new TypeReference<>() {
				});
				// 比對相同題號中的選項與答案
				for (String answer : answerList) {
					if (!options.contains(answer)) {
						return new BasicRes(ResMessage.OPTIONS_ANSWER_MISMATCH.getCode(),
								ResMessage.OPTIONS_ANSWER_MISMATCH.getMessage());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new BasicRes(ResMessage.OPTIONS_PARSER_ERROR.getCode(),
						ResMessage.OPTIONS_PARSER_ERROR.getMessage());
			}

		}
		// 存資料
		List<Feedback> feedbackList = new ArrayList<>();
		for (Entry<Integer, List<String>> map : req.getQuesIDAnswerMap().entrySet()) {
			Feedback feedback = new Feedback();
			feedback.setQuizID(req.quizID);
			feedback.setUserName(req.userName);
			feedback.setEmail(req.getEmail());
			feedback.setUserAge(req.getUserAge());
			feedback.setGender(req.getGender());
			feedback.setQuesID(map.getKey());

			// 將List<String> 轉成String
			try {
				String answerStr = mapper.writeValueAsString(map.getValue());
				feedback.setAnswer(answerStr);
			} catch (JsonProcessingException e) {
				return new BasicRes(ResMessage.OPTIONS_PARSER_ERROR.getCode(),
						ResMessage.OPTIONS_PARSER_ERROR.getMessage());
			}
			feedback.setFillinDate(req.getFillinDate());
			feedbackList.add(feedback);

		}
		feedbackDao.saveAll(feedbackList);
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

	@Override
	public FeedbackRes feedback(int quizId) {
		if (quizId <= 0) {
			return new FeedbackRes(ResMessage.PARAM_QUIZID_ERROR.getCode(), ResMessage.PARAM_QUIZID_ERROR.getMessage());
		}
		List<FeedbackDto> feedbackList = feedbackDao.selectFeedbackByQuizID(quizId);

		// 整理資料(把裝回來的資料塞進整理好的Vo格式中)
		List<FeedbackVo> feedbackVoList = new ArrayList<>();

		for (FeedbackDto item : feedbackList) {

			// 檢查是否同一位填寫者(用email檢查)
			FeedbackVo resVo = getEmail(feedbackVoList, item.getEmail());

			if (resVo != null) {// 如果已有填寫者

				// 將相同填寫者的答案加到同一個 FeedbackVo 中
				List<OptionAnswer> optionAnswerList = resVo.getOptionAnswerList();
				OptionAnswer optionAnswer = new OptionAnswer();

				// 設定同一張問卷不同問題以及答案
				optionAnswer.setQuesID(item.getQuesID());
				optionAnswer.setQuesName(item.getQuesName());

				// 把答案(String)轉成List<String>
				List<String> answerList = new ArrayList<>();
				try {
					answerList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
					});
				} catch (Exception e) {
					return new FeedbackRes(ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getCode(),
							ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getMessage());
				}
				optionAnswer.setAnswerList(answerList);
				optionAnswerList.add(optionAnswer);
				resVo.setOptionAnswerList(optionAnswerList);
				// 取出的Vo已存在，所以不需要再去把resVo新增回去

			} else {// 如果是新填寫者，創建新的 FeedbackVo
				FeedbackVo vo = new FeedbackVo();
				List<OptionAnswer> optionAnswerList = new ArrayList<>();
				OptionAnswer optionAnswer = new OptionAnswer();

				// 設定同一張問卷同一個填寫者的資料
				vo.setQuizID(quizId);
				vo.setqName(item.getqName());
				vo.setExplain(item.getExplain());
				vo.setUserName(item.getUserName());
				vo.setEmail(item.getEmail());
				vo.setAge(item.getAge());
				vo.setFillinDate(item.getFillinDate());

				// 設定同一張問卷不同問題以及答案
				optionAnswer.setQuesID(item.getQuesID());
				optionAnswer.setQuesName(item.getQuesName());

				// 把答案(String)轉成List<String>
				List<String> answerList = new ArrayList<>();
				try {
					answerList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
					});
				} catch (Exception e) {
					return new FeedbackRes(ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getCode(),
							ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getMessage());
				}
				optionAnswer.setAnswerList(answerList);
				optionAnswerList.add(optionAnswer);
				vo.setOptionAnswerList(optionAnswerList);
				feedbackVoList.add(vo);
			}

		}
		return new FeedbackRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), feedbackVoList);
	}

	private FeedbackVo getEmail(List<FeedbackVo> feedbackVoList, String targetEmail) {
		for (FeedbackVo vo : feedbackVoList) {
			if (vo.getEmail().equalsIgnoreCase(targetEmail)) {
				return vo;
			}
		}
		return null;
	}

	// ========================================================================================

	@Override
	public StatisticsRes statistics(int quizId) {

		if (quizId <= 0) {
			return new StatisticsRes(ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getCode(),
					ResMessage.OPTIONS_ANSWER_PARSER_ERROR.getMessage());
		}
		List<StatisticsVo> statisticsVoList = new ArrayList<>();
		List<StatisticsDto> dtoList = feedbackDao.statistics(quizId);

		// 1.集合每一題的所有答案
		Map<Integer, List<String>> quesIDAnswerMap = gatherAnswer(dtoList);
		if (quesIDAnswerMap == null) {
			return new StatisticsRes(ResMessage.PARAM_QUIZID_ERROR.getCode(),
					ResMessage.PARAM_QUIZID_ERROR.getMessage());
		}
		// 2.蒐集每一題的選項(不直接從答案計算次數，是因為可能會有極端的狀況，某個選項無人選)
		List<OptionCount> optionCountList = gatherOptions(dtoList);
		if (optionCountList == null) {
			return new StatisticsRes(ResMessage.OPTIONS_PARSER_ERROR.getCode(),
					ResMessage.OPTIONS_PARSER_ERROR.getMessage());
		}
		// 3.蒐集每一題每個選項的次數
		optionCountList = makeCount(quesIDAnswerMap, optionCountList);
		if (optionCountList == null) {
			return new StatisticsRes(ResMessage.OPTIONS_COUNT_ERROR.getCode(),
					ResMessage.OPTIONS_COUNT_ERROR.getMessage());
		}
		// 4.設定結果
		List<StatisticsVo> statisticVoList = new ArrayList<>();
		for (StatisticsDto dto : dtoList) {
			StatisticsVo vo = new StatisticsVo();
			vo.setqName(dto.getqName());
			vo.setQuesID(dto.getQuesID());
			vo.setQuesName(dto.getContent());
			vo.setMust(dto.isMust());
			vo.setType(dto.getType());

			// 把相同題號的 OptionCount 放一起
			List<OptionCount> ocList = new ArrayList<>();
			for (OptionCount oc : optionCountList) {
				if (oc.getQuesID() == dto.getQuesID()) {
					// 相同題號的話，就把當初蒐集的 OptionCount 放一起
					ocList.add(oc);
				}
			}
			
			//蒐集text的答案
			if(dto.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
				List<String> ansList = quesIDAnswerMap.get(dto.getQuesID());
				if(ansList != null) {
					for(String str : ansList) {
						if(!StringUtils.hasText(str)) {
							continue;
						}
						OptionCount textOc = new OptionCount();
						textOc.setQuesID(dto.getQuesID());
						
						//沒有選項只有答案
						textOc.setOption(str);
						textOc.setCount(1);
						ocList.add(textOc);
					}
					
				}
			}
			vo.setOptionCountList(ocList);
			statisticVoList.add(vo);

		}

		return new StatisticsRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage(), statisticVoList);
	}

	// ==================================================================================

	private List<OptionCount> gatherOptions(List<StatisticsDto> dtoList) {
		List<OptionCount> optionCountList = new ArrayList<>();
		 Map<Integer,Boolean> map = new HashMap<>();
		 
		for (StatisticsDto dto : dtoList) {		
			// 跳過題型是文本問題:沒有選項可蒐集
			if (dto.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
				continue;
			}
			
			//表示同一題的選項已經蒐集過了
			Boolean boo = map.get(dto.getQuesID());
			if(boo != null && boo == true) {
				continue;
			}
			
			List<String> optionList = new ArrayList<>();
			try {
				optionList = mapper.readValue(dto.getOptions(), new TypeReference<>() {
				});

			} catch (Exception e) {
				return null;
			}
			// 搜題題號和選項
			for (String str : optionList) {
				// 相同題號下每個選項會有一個 OptionCount
				OptionCount oc = new OptionCount();
				oc.setQuesID(dto.getQuesID());
				oc.setOption(str);
				optionCountList.add(oc);
			}
			//表示已蒐集過該題的選項
			map.put(dto.getQuesID(), true);
		}
		return optionCountList;
	}

	// ==================================================================================

	// 根據每題編號將答案集合到每題中
	private Map<Integer, List<String>> gatherAnswer(List<StatisticsDto> dtoList) {

		// 新增一個初始化的Map<問題編號 ,答案列表>
		Map<Integer, List<String>> quesIDAnswerMap = new HashMap<>();

		for (StatisticsDto item : dtoList) {
			// 如果題型是文本問題的話，就跳過不收集(可選)
//			if (item.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
//				continue;
//			}

			// 將Answer的 String 轉成List<String>
			List<String> answerList = new ArrayList<>();
			try {
				answerList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
				});
			} catch (Exception e) {
				return null;
			}

			// 若quesIDAnswerMap中已儲存相同編號的 List<String> answerList，就從Map中取出
			if (quesIDAnswerMap.containsKey(item.getQuesID())) {
				List<String> answerListInMap = quesIDAnswerMap.get(item.getQuesID());
				answerList.addAll(answerListInMap);
			}
			quesIDAnswerMap.put(item.getQuesID(), answerList);
		}
		return quesIDAnswerMap;
	}

	// ==================================================================================
	// 此方法不能計算文本問題的次數
	private List<OptionCount> makeCount(Map<Integer, List<String>> quesIdAnswerMap, List<OptionCount> optionCountList) {

		// 因為以選項為主，所以外層的迴圈是optionCountList
		for (OptionCount item : optionCountList) {
			int quesId = item.getQuesID();
			String option = item.getOption();

			// 透過quesId 從 quesIdAnswerMap 取得對應的答案 List
			List<String> ansList = quesIdAnswerMap.get(quesId);
			if (ansList == null) {
				return null;
			}

			// 把List<String>串成一單一字串
			String ansStr = String.join("", ansList);

			// 計算每個選項的次數
			int ansStrLength = ansStr.length();// 原本的長度
			String newAnsStr = ansStr.replace(option, "");// 將目標答案用空字串替代
			int newStrLength = newAnsStr.length();// 扣掉某個選後字串的長度

			// 該選項都沒人選
			if (ansStrLength == newStrLength) {
				item.setCount(0);
			} else {
				// 計算次數要除以選項的字數，才是該選項的正確次數
				int count = (ansStrLength - newStrLength) / option.length();
				// 將次數設回
				item.setCount(count);
			}
		}
		return optionCountList;
	}
}
