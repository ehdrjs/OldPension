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
	
	public NoticeDTO readNotice(int listNum) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
		try {
			sql = "SELECT noticenum, noticesubject, noticecontent, noticedate, savefilename, " + 
					"    originalfilename, filesize, noticecount, userid" + 
					"    FROM notice WHERE noticenum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, listNum);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new NoticeDTO();
				dto.setUserId(rs.getString("userid"));
				dto.setNoticeNum(rs.getInt("noticenum"));
				dto.setNoticeSubject(rs.getString("noticesubject"));
				dto.setNoticeContent(rs.getString("noticecontent"));
				dto.setNoticeDate(rs.getDate("noticedate").toString());
				dto.setOriginalFileName(rs.getString("originalfilename"));
				dto.setFileSize(rs.getLong("filesize"));
				dto.setNoticeCount(rs.getInt("noticecount"));
				dto.setSaveFileName(rs.getString("savefilename"));
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
	public void deleteFile(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {  
			sb.append("UPDATE notice SET originalfilename = ?,");
			sb.append("	savefilename=?, filesize=?");
			sb.append("	WHERE noticenum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
		
			pstmt.setString(1, dto.getOriginalFileName());
			pstmt.setString(2, dto.getSaveFileName());
			pstmt.setDouble(3, dto.getFileSize());
			pstmt.setInt(4, dto.getNoticeNum());
			
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
	public void updateNotice(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {  
			sb.append("UPDATE notice SET noticesubject=?, noticecontent=?, originalfilename = ?,");
			sb.append("	savefilename=?,noticedate=SYSDATE, filesize=?");
			sb.append("	WHERE noticenum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getNoticeSubject());
			pstmt.setString(2, dto.getNoticeContent());
			pstmt.setString(3, dto.getOriginalFileName());
			pstmt.setString(4, dto.getSaveFileName());
			pstmt.setDouble(5, dto.getFileSize());
			pstmt.setInt(6, dto.getNoticeNum());
			
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
	
	public void updateNoticeBasic(NoticeDTO dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {  
			sb.append("UPDATE notice SET noticesubject=?, noticecontent=?");
			sb.append("	WHERE noticenum = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getNoticeSubject());
			pstmt.setString(2, dto.getNoticeContent());
			pstmt.setInt(3, dto.getNoticeNum());
			
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
	
	public void deleteNotice(int listNum) {
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "DELETE FROM notice WHERE noticenum = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, listNum);
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
	
	public void noticeHitCount(int listNum) {
		PreparedStatement pstmt = null;
		String sql = "";
		
		try {
			sql = "UPDATE notice SET noticeCount = noticeCount + 1 WHERE noticeNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, listNum);
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
