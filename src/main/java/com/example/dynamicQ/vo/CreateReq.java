package com.example.dynamicQ.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.dynamicQ.contents.ResMessage;
import com.example.dynamicQ.entity.Question;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateReq {

	// @NotBlank: 限制字串不得為 1.null 2.空字串 3.全白空字串
	// 括號中的message 表示不符合限制時回傳的訊息，等號後面只能放固定的字串
//	@NotBlank(message = ResMessage.Constants.PARAM_QNAME_ERROR_MSG)
	private String qName;
	
//	@NotBlank(message = ResMessage.Constants.PARAM_QEXPLAIN_ERROR_MSG)
	private String qExplain;
	
//	@NotNull(message = ResMessage.Constants.PARAM_STARTTIME_ERROR_MSG)
	private LocalDate startTime;
	
//	@NotNull(message = ResMessage.Constants.PARAM_ENDTIME_ERROR_MSG)
	private LocalDate endTime;
	
	private Boolean qSituation;

	// 因為 Question裡面還有對屬性有做驗證，屬於嵌套驗證，要加上@Valid才能讓裡面生效
//	@Valid
//	@Size(min = 1, message = ResMessage.Constants.PARAM_QUES_LIST_ERROR_MSG)
	private List<Question> questionList;

	public CreateReq() {
		super();
	}

	public CreateReq(String qName, String qExplain, LocalDate startTime, LocalDate endTime, Boolean qSituation,
			List<Question> questionList) {
		super();
		this.qName = qName;
		this.qExplain = qExplain;
		this.startTime = startTime;
		this.endTime = endTime;
		this.qSituation = qSituation;
		this.questionList = questionList;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getqExplain() {
		return qExplain;
	}

	public void setqExplain(String qExplain) {
		this.qExplain = qExplain;
	}

	public LocalDate getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDate startTime) {
		this.startTime = startTime;
	}

	public LocalDate getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDate endTime) {
		this.endTime = endTime;
	}

	public Boolean getqSituation() {
		return qSituation;
	}

	public void setqSituation(Boolean qSituation) {
		this.qSituation = qSituation;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}

}
