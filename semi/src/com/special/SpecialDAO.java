package com.special;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.util.DBConn;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	public int insertSpecial(SpecialDTO dto) { // 글, 이미지 insert
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT INTO special(specialNum, specialSubject, specialContent, ");
			sb.append("     specialStart, specialEnd, userId) VALUES (special_seq.NEXTVAL, ?, ?, ?, ?, ?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getSpecialSubject());
			pstmt.setString(2, dto.getSpecialContent());
			pstmt.setString(3, dto.getSpecialStart());
			pstmt.setString(4, dto.getSpecialEnd());
			pstmt.setString(5, dto.getUserId());

			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;

			Iterator<String> it = dto.getImageMap().keySet().iterator();
			String sql;
			sql = "INSERT INTO specialImageFile(fileNum, imageFileName, specialNum)";
			sql += "     VALUES (specialFile_seq.NEXTVAL, ?, special_seq.CURRVAL)";
			pstmt = conn.prepareStatement(sql);
			while (it.hasNext()) {
				String key = it.next();
				pstmt.setString(1, key);
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}



	public List<SpecialDTO> listSpecial(int start, int end) { // list에 
		List<SpecialDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM (");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("		SELECT specialNum, specialSubject, specialDate, specialStart, specialEnd, specialCount ");
			sb.append("		FROM special");
			sb.append("		ORDER BY specialNum DESC");
			sb.append("	) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SpecialDTO dto = new SpecialDTO();

				dto.setSpecialNum(rs.getInt("specialNum"));
				dto.setSpecialSubject(rs.getString("specialSubject"));
				dto.setSpecialDate(rs.getString("specialDate"));
				// dto.setSpecialDate(rs.getDate("specialDate").toString());
				// dto.setSpecialStart(rs.getDate("specialStart").toString());
				dto.setSpecialStart(rs.getString("specialStart"));
				// dto.setSpecialEnd(rs.getDate("specialEnd").toString());
				dto.setSpecialEnd(rs.getString("specialEnd"));
				dto.setSpecialCount(rs.getInt("specialCount"));

				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return list;
	}

	public int dataCount() { // 데이터개수
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM special";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return result;
	}

	public int deleteSpecial(int specialNum) { // 삭제
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM specialImageFile WHERE specialNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, specialNum);
			pstmt.executeUpdate();

			pstmt.close();
			pstmt = null;

			sql = "DELETE FROM special WHERE specialNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, specialNum);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return result;
	}



	public SpecialDTO readSpecial(int specialNum) { // 글 dto
		SpecialDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT specialNum, specialSubject, specialContent, specialDate, specialCount, ");
			sb.append("	specialStart, specialEnd, userId ");
			sb.append("FROM special WHERE specialNum = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, specialNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new SpecialDTO();

				dto.setSpecialNum(rs.getInt("specialNum"));
				dto.setSpecialSubject(rs.getString("specialSubject"));
				dto.setSpecialContent(rs.getString("specialContent"));
				dto.setSpecialDate(rs.getDate("specialDate").toString());
				dto.setSpecialCount(rs.getInt("specialCount"));
				dto.setSpecialStart(rs.getDate("specialStart").toString());
				dto.setSpecialEnd(rs.getDate("specialEnd").toString());
				dto.setUserId(rs.getString("userId"));
			
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return dto;
	}

	
	public List<SpecialDTO> readImage(int specialNum) { // 이미지 리스트 
		List<SpecialDTO> list = new ArrayList<SpecialDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT imageFileName FROM specialImageFile WHERE specialNum = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, specialNum);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				SpecialDTO dto = new SpecialDTO();
				dto.setImageFileName(rs.getString("imageFileName"));
				
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {

				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return list;
	}
	
	public int updateSpecial(SpecialDTO dto) { // 글, 사진 update
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {

			sb.append("UPDATE special SET specialSubject = ?, specialContent = ?, ");
			sb.append("specialStart = ?, specialEnd = ?  WHERE specialNum = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getSpecialSubject());
			pstmt.setString(2, dto.getSpecialContent());
			pstmt.setString(3, dto.getSpecialStart());
			pstmt.setString(4, dto.getSpecialEnd());
			pstmt.setInt(5, dto.getSpecialNum());

			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;

			sb = new StringBuffer();
			sb.append("UPDATE specialImageFile SET imageFileName = ? WHERE specialNum = ?");
			pstmt = conn.prepareStatement(sb.toString());	
			Iterator<String> it = dto.getImageMap().keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				pstmt.setString(1, key);
				pstmt.setInt(2, dto.getSpecialNum());
				pstmt.executeUpdate();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}

			}
		}

		return result;
	}
	
	public int hitCountUpdate(int specialNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE special SET specialCount = specialCount + 1 WHERE specialNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, specialNum);

			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
		}

		return result;
	}

}
