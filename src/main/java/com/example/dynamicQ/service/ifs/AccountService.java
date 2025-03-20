package com.example.dynamicQ.service.ifs;

import com.example.dynamicQ.entity.Account;
import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.LogInReq;

public interface AccountService {
	
	public BasicRes createAccount(Account req);
	
	public BasicRes logIn(LogInReq req);

}
