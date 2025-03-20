package com.example.dynamicQ.vo;

import java.util.List;

//一個 StatisticsVo 表示一個問題的答案次數
public class StatisticsVo {

	private String qName;

	private int quesID;

	private String quesName;

	private boolean must;

	private String type;

	List<OptionCount> optionCountList;

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

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

	public boolean isMust() {
		return must;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

	public List<OptionCount> getOptionCountList() {
		return optionCountList;
	}

	public void setOptionCountList(List<OptionCount> optionCountList) {
		this.optionCountList = optionCountList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
