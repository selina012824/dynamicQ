package com.example.dynamicQ.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dynamicQ.entity.Account;
import com.example.dynamicQ.entity.Feedback;
import com.example.dynamicQ.entity.FeedbackID;

@Repository
public interface AccountDao extends JpaRepository<Account, String>{
	
	@Query(value = "select count(email) from account where  email = ?1 ", nativeQuery = true)
	public int selectCount(String email);
	
	@Query(value = "select count(user_name) from account where  user_name = ?1 ", nativeQuery = true)
	public int selectCount2(String userName);
    
	
	@Query(value = "select password from account where  email = ?1 ", nativeQuery = true)
	public String checkPassword(String email);

}
