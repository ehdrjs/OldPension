package com.special;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	// 글 추가
	public int insertSpecial(SpecialDTO dto) {
		int result = 0;

		return result;
	}

	// 글 수정
	public int updateSpecial(SpecialDTO dto, String userId) {
		int result = 0;
		return result;
	}

	// 글 삭제
	public int deleteSpecial(int specialNum, String userId) {
		int result = 0;

		return result;
	}

	// 달력 리스트
	public List<SpecialDTO> listSpecial(int start, int end) {
		List<SpecialDTO> list = new ArrayList<>();

		return list;
	}

	// 게시물 보기
	public SpecialDTO readSpecial(int specialNum) {
		SpecialDTO dto = null;
		return dto;
	}

	// 데이터 개수
	public int dataCount() {
		int result = 0;
		return result;
	}

	// 조회수 증가
	public int hitCountUpdate(int specialNum) {
		int result = 0;

		return result;
	}

}
