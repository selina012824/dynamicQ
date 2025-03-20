package com.example.dynamicQ.vo;

import java.util.List;


public class SearchRes extends BasicRes{
	

	private List<SearchVo> searchList;

	public SearchRes() {
		super();
	}
	
	public SearchRes(int code, String message) {
		super(code,message);
	}

	public SearchRes(int code, String message, List<SearchVo> searchList) {
		super(code, message);
		this.searchList = searchList;
	}

	public SearchRes(List<SearchVo> searchList) {
		super();
		this.searchList = searchList;
	}
	
	
	

	
	

}
