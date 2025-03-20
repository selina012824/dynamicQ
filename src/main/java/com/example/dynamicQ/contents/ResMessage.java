package com.example.dynamicQ.contents;

//列舉
public enum ResMessage {
	SUCCESS(200,"Success!!"), //
	PARAM_QNAME_ERROR(400,"Param name error"),
	PARAM_QEXPLAIN_ERROR(400,"Param description error"),
	PARAM_STARTTIME_ERROR(400,"Param start_time error"),
	PARAM_ENDTIME_ERROR(400,"Param end_time error"),
	PARAM_QUESID_ERROR(400,"Param ques_id error"),
	PARAM_QUESCONTENT_ERROR(400,"Param question content error"),
	PARAM_TYPE_ERROR(400,"Param type error"),
	PARAM_OPTIONS_ERROR(400,"Param question option error"),
	PARAM_QUES_LIST_ERROR(400,"Param question list error"),
	PARAM_TIME_ERROR(400,"start_time is after end_time"),
	PARAM_USERNAME_ERROR(400,"Param username error"),
	PARAM_EMAIL_ERROR(400,"Param email error"),
	PARAM_PASSWORD_ERROR(400,"Param password error"),
	PARAM_GENDER_ERROR(400,"Param gender error"),
	PARAM_AGE_ERROR(400,"Param age error"),
	DATA_SAVE_ERROR(400," Data saved error"),
	DATA_DELETE_ERROR(400,"Data delete error"),
	DATA_UPDATE_ERROR(400,"Data update error"),
	QUES_TYPE_MISMATCH(400,"Question type mismatch"),
	QUIZ_ID_MISMATCH(400,"Quiz_ID mismatch"),
	PARAM_QUIZID_ERROR(400,"Quiz_ID error"),
	QUIZ_NOT_FOUND(404,"Quiz not found"),
	EMAIL_DUPLICATED(400,"Email duplicated"),
	EMAIL_NOT_FOUND(404,"Account not found"),
	USERNAME_DUPLICATED(400,"User name duplicated"),
	OUT_OF_FILLIN_TIME_RANGE(400,"Out of fillin time range"),
	ANSWER_IS_REQUIRED(400,"Answer id required"),
	ONLY_ONE_ANSWER_ALLOWED(400,"Only one answer allowed"),
	OPTIONS_PARSER_ERROR(400,"Options parser error"),
	OPTIONS_ANSWER_MISMATCH(400,"Options parser error"),
	OPTIONS_ANSWER_PARSER_ERROR(400,"Options answer parser error"),
	OPTIONS_COUNT_ERROR(400,"Options count error"),
	ACCOUNT_CREATE_ERROR(400,"account created error"),
	LOGIN_PARAM_ERROR(400,"Log in param error"),
	PASSWORD_ERROR(400,"Password error");
	
	
	
	
	private int code;
	
	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
	// PARAM_QUIZ_NAME_ERROR_MSG 要宣告成 final 才會是常數
	// PARAM_QUIZ_NAME_ERROR_MSG 宣告成 static 才可以直接透過 ResMessage.Constants 取得
	public static class Constants {
		public static final String  PARAM_QNAME_ERROR_MSG = "Param name error";
		public static final String  PARAM_QEXPLAIN_ERROR_MSG = "Param description error";
		public static final String  PARAM_STARTTIME_ERROR_MSG = "Param start_time error";
		public static final String  PARAM_ENDTIME_ERROR_MSG = "Param end_time error";
		public static final String  PARAM_QUES_LIST_ERROR_MSG = "Param question list error";
		public static final String  PARAM_QUESID_ERROR_MSG = "Param question ID error";
		public static final String  PARAM_TYPE_ERROR_MSG = "Param type error";
		public static final String  PARAM_QUESCONTENT_ERROR = "Param question name error";
	}
	
	

}
