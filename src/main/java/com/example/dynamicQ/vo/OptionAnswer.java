package com.example.dynamicQ.vo;

import java.util.List;

//不同問題不同人的回答
public class OptionAnswer {

	private int quesID;

	private String quesName;

	private List<String> answerList;

	public int getQuesID() {
		return quesID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	public String getQuesName() {
		return quesName;
	}

	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}

	public List<String> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}

}
