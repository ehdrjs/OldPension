package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class ReviewDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertReview(ReviewDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		
		try {
			
			sb.append(" INSERT INTO review( ");
			sb.append(" reviewNum, reviewContent, parent, orderNo, depth, groupNum, userId ");
			sb.append(" VALUES( ? , ? , ? , ? , ? , ? , ? )");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, dto.getReviewNum());
			pstmt.setString(2, dto.getReviewContent());
			pstmt.setInt(3, dto.getParent());
			pstmt.setInt(4, dto.getOrderNo());
			pstmt.setInt(5, dto.getDepth());
			pstmt.setInt(6, dto.getGroupNum());
			pstmt.setString(7, dto.getUserId());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	
	
	
	
}
