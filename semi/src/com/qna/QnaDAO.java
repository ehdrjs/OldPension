package com.qna;

import java.sql.Connection;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertBoard(QnaDTO dto, String mode) {
		int result=0;
		
		return result;
	}
}
