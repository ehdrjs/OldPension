package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertQna(QnaDTO dto, String mode) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		int seq;
		
		try {
			sql="SELECT qna_seq.NEXTVAL FROM dual";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			seq=0;
			if(rs.next())
				seq=rs.getInt(1);
			rs.close();
			pstmt.close();
			rs=null;
			pstmt=null;
			
			dto.setNum(seq);
			if(mode.equals("created")) {
				dto.setGroupNum(seq);
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0);
		}
			sql = "INSERT INTO qna(qnaNum, userId, qnaSubject, qnaContent, ";
			sql += " groupNum, depth, orderNo, parent, qnaPwd) ";
			sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getDepth());
			pstmt.setInt(7, dto.getOrderNo());
			pstmt.setInt(8, dto.getParent());
			pstmt.setString(9, dto.getPwd());
			
			result=pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
				}
			}
		}
		return result;
	}
}
