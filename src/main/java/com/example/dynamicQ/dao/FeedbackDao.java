package com.example.dynamicQ.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.dynamicQ.entity.Feedback;
import com.example.dynamicQ.entity.FeedbackID;
import com.example.dynamicQ.entity.Question;
import com.example.dynamicQ.entity.QuestionID;
import com.example.dynamicQ.vo.FeedbackDto;
import com.example.dynamicQ.vo.StatisticsDto;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, FeedbackID>{

	@Query(value = "select count(quiz_id) from feedback where quiz_id =?1 and email = ?2 ", nativeQuery = true)
	public int selectCount(int quizId, String email);

	// nativeQuery = false,所以名稱要與變數名稱一樣
	// 因為沒有SpringBoot的託管機能所以要用新增建構方法:new com.example.dynamicQ.vo
	// 使用join將3張屬性的變數做連接，這樣才能同時把一個值新增到資料庫不同張表的相同欄位
	@Query(value = "select new com.example.dynamicQ.vo.FeedbackDto(Qz.qName,Qz.qExplain,F.userName,F.email,F.userAge,F.gender,F.answer,"
			+ " F.fillinDate,Qu.quesID,Qu.content) from Quiz as Qz join Question as Qu on Qz.id=Qu.qzId join"
			+ " Feedback as F on Qz.id=F.quizID and Qu.quesID=F.quesID  where Qz.id=?1", nativeQuery = false)
	public List<FeedbackDto> selectFeedbackByQuizID(int quizId);
	
	@Query(value ="select * from feedback", nativeQuery = true)
	public List<Feedback> getAllUser();

	@Query(value = "select new com.example.dynamicQ.vo.StatisticsDto(Qz.qName,Qu.quesID,Qu.content,Qu.must,"
			+ " Qu.options,Qu.type,F.answer) from Quiz as Qz join Question as Qu on Qz.id=Qu.qzId "
			+ " join Feedback as F on Qz.id=F.quizID and Qu.quesID=F.quesID where Qz.id=?1", nativeQuery = false)
	public List<StatisticsDto> statistics(int quizId);
}
