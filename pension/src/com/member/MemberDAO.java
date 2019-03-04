package com.member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
	
	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			
			
			
			
		} catch (Exception e) {

		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	public int insertMember(MemberDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			
		} catch (Exception e) {
			
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}
		
		return result;
	}
}
