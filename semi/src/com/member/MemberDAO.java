package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public MemberDTO readMember(String userId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT c.usernum, userid, pwd, username, usertel, useremail, userip, admin");
			sb.append("	FROM client c JOIN member m ON c.usernum = m.usernum");
			sb.append("	WHERE userid = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setUserNum(rs.getString("userNum"));
				dto.setUserId(rs.getString("userid"));
				dto.setUserName(rs.getString("username"));
				dto.setUserPwd(rs.getString("pwd"));
				dto.setIp(rs.getString("userip"));
				dto.setUserRole(rs.getString("admin"));
				dto.setTel(rs.getString("usertel"));
				dto.setEmail(rs.getString("useremail"));
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		StringBuffer sb = new StringBuffer();
	
		
		try {
			
			sb.append("INSERT ALL");
			sb.append("	INTO client(usernum, pwd, usertel, userip, useremail) values(CLIENT_SEQ.NEXTVAL,?,?,?,?)");
			sb.append("	INTO member(usernum, userid, username, admin) values(CLIENT_SEQ.CURRVAL,?,?,?)");
			sb.append("	SELECT  * FROM  dual"); 
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getUserPwd());
			pstmt.setString(2, dto.getTel());
			pstmt.setString(3, dto.getIp());
			pstmt.setString(4, dto.getEmail());
			pstmt.setString(5, dto.getUserId());
			pstmt.setString(6, dto.getUserName());
			pstmt.setString(7, dto.getUserRole());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public int testIdO(String id) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		
		try {
			sql = "SELECT COUNT(*) FROM member WHERE userid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count =  rs.getInt(1); 
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	} 
}
