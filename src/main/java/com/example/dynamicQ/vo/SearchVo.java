package com.example.dynamicQ.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.dynamicQ.entity.Question;

public class SearchVo {

	private int qzId;

	private String qName;
	private String qExplain;
	private LocalDate startTime;
	private LocalDate endTime;
	private Boolean qSituation;
	private List<Question> questionList;

	public SearchVo() {
		super();
	}

	public SearchVo(int qzId, String qName, String qExplain, LocalDate startTime, LocalDate endTime, Boolean qSituation,
			List<Question> questionList) {
		super();
		this.qzId = qzId;
		this.qName = qName;
		this.qExplain = qExplain;
		this.startTime = startTime;
		this.endTime = endTime;
		this.qSituation = qSituation;
		this.questionList = questionList;
	}

}
