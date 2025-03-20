package com.example.dynamicQ.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.dynamicQ.contents.ResMessage;
import com.example.dynamicQ.dao.AccountDao;
import com.example.dynamicQ.entity.Account;
import com.example.dynamicQ.service.ifs.AccountService;
import com.example.dynamicQ.vo.BasicRes;
import com.example.dynamicQ.vo.CreateReq;
import com.example.dynamicQ.vo.LogInReq;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public BasicRes createAccount(Account req) {
		
		BasicRes checkRes = checkParam(req);
		if (checkRes != null) {
			return checkRes;
		}
		
		// 檢查同一個 email 是否重複創建帳號
		if (accountDao.selectCount(req.getEmail()) != 0) {
			return new BasicRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
		}
		
		// 檢查 使用者名稱 是否已存在
		if (accountDao.selectCount2(req.getUserName()) != 0) {
			return new BasicRes(ResMessage.USERNAME_DUPLICATED.getCode(), ResMessage.USERNAME_DUPLICATED.getMessage());
		}
			
		// 把密碼變亂碼(加密)
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		req.setPassword(encoder.encode(req.getPassword()));
		
		try {
			accountDao.save(req);
		}catch(Exception e){
			return new BasicRes(ResMessage.ACCOUNT_CREATE_ERROR.getCode(), ResMessage.ACCOUNT_CREATE_ERROR.getMessage());
		}
		
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}
	
	

	// 檢查參數
	private BasicRes checkParam(Account req) {
		// 檢查個參數有沒有值
		if (!StringUtils.hasText(req.getEmail())) {
			return new BasicRes(ResMessage.PARAM_EMAIL_ERROR.getCode(), ResMessage.PARAM_EMAIL_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getPassword())) {
			return new BasicRes(ResMessage.PARAM_PASSWORD_ERROR.getCode(),
					ResMessage.PARAM_PASSWORD_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getUserName())) {
			return new BasicRes(ResMessage.PARAM_USERNAME_ERROR.getCode(),
					ResMessage.PARAM_USERNAME_ERROR.getMessage());
		}
	

		return null;
	}



	@Override
	public BasicRes logIn(LogInReq req) {
		//檢查參數
		if (!StringUtils.hasText(req.getEmail()) ) {
			return new BasicRes(ResMessage.LOGIN_PARAM_ERROR.getCode(), ResMessage.LOGIN_PARAM_ERROR.getMessage());
		}
		
		if (!StringUtils.hasText(req.getPassword()) ) {
			return new BasicRes(ResMessage.LOGIN_PARAM_ERROR.getCode(), ResMessage.LOGIN_PARAM_ERROR.getMessage());
		}
		
		//檢查帳號存不存在
		if (accountDao.selectCount(req.getEmail()) == 0) {
			return new BasicRes(ResMessage.EMAIL_NOT_FOUND.getCode(), ResMessage.EMAIL_NOT_FOUND.getMessage());
		}
		
		//檢查帳號的密碼相符
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean check = encoder.matches(req.getPassword(), accountDao.checkPassword(req.getEmail()));
		
		
		if(!check) {
			return new BasicRes(ResMessage.PASSWORD_ERROR.getCode(), ResMessage.PASSWORD_ERROR.getMessage());
		}
		
		return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
	}

}
