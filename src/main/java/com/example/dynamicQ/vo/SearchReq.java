package com.example.dynamicQ.vo;

import java.time.LocalDate;

public class SearchReq {

	private String searchname;
	private LocalDate startTime;
	private LocalDate endTime;

	public String getSearchname() {
		return searchname;
	}

	public void setName(String searchname) {
		this.searchname = searchname;
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

}
