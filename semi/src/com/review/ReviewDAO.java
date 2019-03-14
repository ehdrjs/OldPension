package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ReviewDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertReview(ReviewDTO dto) {
		int result=0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		
		try {
			sb.append(" INSERT INTO review( ");
			sb.append(" reviewNum, reviewContent, parent, orderNo, depth, groupNum, userId ) ");
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
	
	
	public int reviewCount() {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT NVL(COUNT(*), 0) FROM review WHERE reviewNum>=1 ";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	
	public int checkReviewNum() {
		int result=0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT MAX(reviewNum) FROM review ";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next())
				result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public String isAdmin(String userId) {
		String result="";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT admin FROM member WHERE userId=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs=pstmt.executeQuery();
			if(rs.next())
				result = rs.getString("admin");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public List<ReviewDTO> listReview(int start, int end) {
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append(" 	SELECT ROWNUM rnum, tb.* FROM( ");
			sb.append("			SELECT reviewNum, reviewContent, parent, orderNo, depth, groupNum, userId");
			sb.append("				   ,TO_CHAR(reviewDate, 'YYYY-MM-DD HH:mm') reviewDate ");
			sb.append("			FROM review ");
			sb.append("			ORDER BY reviewNum DESC, orderNo ASC ");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setReviewNum(rs.getInt("reviewNum"));
				dto.setReviewContent(rs.getString("reviewContent"));
				dto.setParent(rs.getInt("parent"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setReviewDate(rs.getString("reviewDate"));
				
				String admin = this.isAdmin(rs.getString("userId"));
				dto.setAdmin(admin);
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	
	
	public int deleteReview(int reviewNum) {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		
		try {
			sql = "DELETE FROM review WHERE reviewNum=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, reviewNum);
			
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
