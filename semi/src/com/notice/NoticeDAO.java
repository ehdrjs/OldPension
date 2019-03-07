package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	public List<NoticeDTO> readNotice(int start, int end){
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {

			sb.append(" SELECT rnum, noticenum, noticesubject, noticecontent, ");
			sb.append(" noticedate, savefilename, noticecount, userid ");
			sb.append(" FROM (SELECT ROWNUM rnum, tb.* ");
			sb.append(" FROM (SELECT noticenum, noticesubject, noticecontent,");
			sb.append(" noticedate, savefilename, noticecount, userid ");
			sb.append(" FROM notice ORDER BY noticenum DESC");
			sb.append(" )tb WHERE ROWNUM <= ?");
			sb.append(" )WHERE rnum >= ? ");
			
		    pstmt = conn.prepareStatement(sb.toString());
		    pstmt.setInt(1, end);
		    pstmt.setInt(2, start);
		    rs = pstmt.executeQuery();
		    
		    while(rs.next()) {
		    	NoticeDTO dto = new NoticeDTO();
		    	dto.setRnum(rs.getInt("rnum"));
		    	dto.setNoticeContent(rs.getString("noticecontent"));
		    	dto.setNoticeCount(rs.getInt("noticecount"));
		    	dto.setNoticeNum( rs.getInt("noticenum"));
		    	dto.setNoticeSubject(rs.getString("noticesubject"));
		    	dto.setSaveFileName(rs.getString("savefilename"));
		    	dto.setUserId(rs.getString("userid"));
		    	dto.setNoticeDate(rs.getDate("noticedate").toString());
		    	
		    	list.add(dto);
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
		return list;
	}
	
	public int dataCount() {
		int count = 0;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		
		try {
			sql = "SELECT NVL(COUNT(*),0) FROM notice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return count;
	}
	
	public void insertNotice(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO notice(noticenum, noticesubject, noticecontent, noticedate, noticecount, userid");
			if(dto.getSaveFileName() == null) {
				sb.append(") VALUES(NOTICE_SEQ.NEXTVAL ,?,?, SYSDATE,?,?)");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getNoticeSubject());
				pstmt.setString(2, dto.getNoticeContent());
				pstmt.setInt(3, dto.getNoticeCount());
				pstmt.setString(4, dto.getUserId());

			}else {
				sb.append(" ,savefilename, originalfilename, filesize)");
				sb.append(" VALUES(NOTICE_SEQ.NEXTVAL,?,?,SYSDATE,?,?,?,?,?)");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setString(1, dto.getNoticeSubject());
				pstmt.setString(2, dto.getNoticeContent());
				pstmt.setInt(3, dto.getNoticeCount());
				pstmt.setString(4, dto.getUserId());
				pstmt.setString(5, dto.getSaveFileName());
				pstmt.setString(6, dto.getOriginalFileName());
				pstmt.setLong(7, dto.getFileSize());
				
			}
	
			pstmt.executeUpdate();
		  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
	}
}
