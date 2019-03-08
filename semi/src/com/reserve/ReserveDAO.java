package com.reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.util.DBConn;

public class ReserveDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertReserve(ReserveDTO dto) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO reserve(reserveNum, userNum, reserveName, reserveCount, reserveMemo, startDay, endDay) VALUES(res_seq.NEXTVAL, client_seq.NEXTVAL, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getReserveName());
			pstmt.setInt(2, dto.getReserveCount());
			pstmt.setString(3, dto.getReserveMemo());
			pstmt.setString(4, dto.getStartDay());
			pstmt.setString(5, dto.getEndDay());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
