package com.example.dynamicQ.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dynamicQ.entity.Question;
import com.example.dynamicQ.entity.QuestionID;

import jakarta.transaction.Transactional;


@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionID>{
	
	@Query(value="select * from question where QID= ?1 ",nativeQuery = true)
	public List<Question> getByQuizID(int Qid);
	
	
	
	@Transactional
	@Modifying
	@Query(value ="delete from question where QID in (?1)",nativeQuery = true)
	public int deleteByQuizIdIn(List<Integer> Qids);

}
