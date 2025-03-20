package com.example.dynamicQ.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz {

	// @GeneratedValue參考投影片 SpringBoot_基礎的P26~P27
	// 此Annotation 使用上的差別在於若是使用JPA的 save方法:
	// 1.欄位是AI，且資料型態為int時，可不加
	// 2.但 save 方法的回傳值中，不會有最新的 AI 值，加了才會有最新的AI欄位值
	// 3. 資料型態是 Integer 時，一定要加，不然會報錯
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "Qname")
	private String qName;

	@Column(name = "Qexplain")
	private String qExplain;

	@Column(name = "start_time")
	private LocalDate startTime;

	@Column(name = "end_time")
	private LocalDate endTime;

	@Column(name = "Qsituation")
	private Boolean qSituation;

	public Quiz() {
		super();
	}

	public Quiz(String qName, String qExplain, LocalDate startTime, LocalDate endTime, Boolean qSituation) {
		super();
		this.qName = qName;
		this.qExplain = qExplain;
		this.startTime = startTime;
		this.endTime = endTime;
		this.qSituation = qSituation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getqExplain() {
		return qExplain;
	}

	public void setqExplain(String qExplain) {
		this.qExplain = qExplain;
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

	public Boolean getqSituation() {
		return qSituation;
	}

	public void setqSituation(Boolean qSituation) {
		this.qSituation = qSituation;
	}

}
