package com.example.dynamicQ.vo;

public class StatisticsDto {
	
	private String qName;
	
	private int quesID;
	
	private String content;
	
	private boolean must;
	
    private String options;
	
	private String type;
	
	private String answer;

	public StatisticsDto() {
		super();
	}



	public StatisticsDto(String qName, int quesID, String content, boolean must, String options, String type,
			String answer) {
		super();
		this.qName = qName;
		this.quesID = quesID;
		this.content = content;
		this.must = must;
		this.options = options;
		this.type = type;
		this.answer = answer;
	}



	public String getqName() {
		return qName;
	}

	public int getQuesID() {
		return quesID;
	}



	public String getContent() {
		return content;
	}



	public boolean isMust() {
		return must;
	}

	public String getAnswer() {
		return answer;
	}

	public String getOptions() {
		return options;
	}

	public String getType() {
		return type;
	}
	
	
	
	

}
