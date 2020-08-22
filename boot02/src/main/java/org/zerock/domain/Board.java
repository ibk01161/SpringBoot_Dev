package org.zerock.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_boards")
public class Board {
	
	// 식별키(PK키)를 의미하는 어노테이션
	@Id
	// 식별키 생성 전략, 식별키를 어떤 방식으로 부여하는지 결정 
	// IDENTITY는 기본 키 생성 방식 자체를 데이터베이스에 위임하는 방식, 데이터베이스에 의존적인 방식
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	private String title;
	private String writer;
	private String content;
	
	// 날짜 데이터를 기록하는 설정
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	

}

