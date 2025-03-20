package com.example.dynamicQ.entity;

import com.example.dynamicQ.contents.ResMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "question")
@IdClass(value = QuestionID.class)
public class Question {

	@Id
	@Column(name = "QID")
	private int qzId;
    
//	@Min(value=2,message = ResMessage.Constants.PARAM_QUESID_ERROR_MSG)
	@Id
	@Column(name = "ques_id")
	private int quesID;
    
//	@NotBlank(message = ResMessage.Constants.PARAM_QUESCONTENT_ERROR)
	@Column(name = "content")
	private String content;
    
//	@NotBlank(message = ResMessage.Constants.PARAM_TYPE_ERROR_MSG)
	@Column(name = "type")
	private String type;

	@Column(name = "options")
	private String options;

	@Column(name = "must")
	private boolean must;

	public Question() {
		super();
	}

	public Question(int qzId, int quesID, String content, String type, String options, boolean must) {
		super();
		this.qzId = qzId;
		this.quesID = quesID;
		this.content = content;
		this.type = type;
		this.options = options;
		this.must = must;
	}

	public int getQzId() {
		return qzId;
	}

	public int getQuesID() {
		return quesID;
	}

	public String getContent() {
		return content;
	}

	public String getType() {
		return type;
	}

	public String getOptions() {
		return options;
	}

	public boolean isMust() {
		return must;
	}

	public void setQzId(int qzId) {
		this.qzId = qzId;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

    

}
