package com.example.dynamicQ.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dynamicQ.entity.Quiz;
import com.example.dynamicQ.vo.SearchVo;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {

	/**
	 * 1. 因為透過 join 來取得跨表的欄位，所以 nativeQuery 只能是 false <br>
	 * 2. nativeQuery = false，語法中表的名稱會改成 Entity class 的名稱，欄位名稱則是 Entity 的屬性變數名稱 3.
	 * nativeQuery = false 時，用來裝載的類別 SearchVo 必須要透過 new 4. 因為 SearchVo 沒有被 Spring
	 * boot 託管，所以要跟加上路徑
	 */

//	@Query(value ="select new com.example.dynamicQ.vo.SearchVo(QID,qname,qexplain,startTime,endTime,qsituation,questionList)"
//			+"from Quiz join Question on Quiz.id = Question.QID",
//			nativeQuery = false)
//    public List<SearchVo> getAll();

	@Query(value = "select * from quiz", nativeQuery = true)
	public List<Quiz> getAllQuiz();

	@Query(value = "select * from quiz where  start_time>=?1 " + "and end_time <=?2 ", nativeQuery = true)
	public List<Quiz> getQuiz(LocalDate startTime, LocalDate endTime);

	@Transactional
	@Modifying
	@Query(value = "delete from quiz where id in (?1)", nativeQuery = true)
	public int deleteByQuizIdIn(List<Integer> Qids);
    
	//查找的問卷得是已發佈
	@Query(value = "select count(id) from quiz where id= ?1 and Qsituation = true", nativeQuery = true)
	public int selectCountIfPublished(int quizID);
	
	@Query(value = "select count(id) from quiz where id= ?1 ", nativeQuery = true)
	public int selectCount(int quizID);

	@Transactional
	@Modifying
	@Query(value = "update quiz set Qname=?1, Qexplain=?2, start_time=?3,end_time=?4, Qsituation=?5 where id=?6", nativeQuery = true)
	public int updateQuiz(String name, String explain, LocalDate startTime, LocalDate endTime, 
			boolean situation, int quizID);
	
//	//此方法等於 jpa 的findById(int id)
//	@Query(value = "select * from quiz where id = ?1 ", nativeQuery = true)
//	public SearchVo getByID(int quizID);
	

//	 @Query("select new com.example.dynamicQ.vo.SearchVo(Qz.id, Qz.qName, Qz.qExplain, Qz.startTime, Qz.endTime, Qz.qSituation,Qu) " 
//	            +" from Quiz as Qz join Question as Qu on Qu.qzId = Qz.id " 
//	            +" where Qz.id = ?1")
//	    SearchVo getByID(int quizID);
}
