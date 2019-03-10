package com.special;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	// 글 추가
	public int insertSpecial(SpecialDTO dto) {
		int result = 0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("INSERT ALL");
			sb.append("	INTO imageFile(fileNum, imageFileName, imageFileSize)");
			sb.append("		VALUES(file_seq.NEXTVAL, ?, ?)");
			sb.append("	INTO special(specialNum, specialSubject, specialContent, ");
			sb.append(
					"		specialStart, specialEnd, userId, fileNum) VALUES (special_seq.NEXTVAL, ?, ?, ?, ?, ?, file_seq.CURRVAL)");
			sb.append("SELECT * FROM dual");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getImageFileName());
			pstmt.setLong(2, dto.getImageFileSize());
			pstmt.setString(3, dto.getSpecialSubject());
			pstmt.setString(4, dto.getSpecialContent());
			pstmt.setString(5, dto.getSpecialStart());
			pstmt.setString(6, dto.getSpecialEnd());
			pstmt.setString(7, dto.getUserId());

			result = pstmt.executeUpdate();

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

	// 리스트
	public List<SpecialDTO> listSpecial(int start, int end) {
		List<SpecialDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM (");
			sb.append("	SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("		SELECT specialSubject, specialDate, specialStart, specialEnd, specialCount ");
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

				dto.setSpecialSubject(rs.getString("specialSubject"));
				dto.setSpecialDate(rs.getString("specialDate"));
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


	// 데이터 개수
	public int dataCount() {
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

	
	// 글 수정
	public int updateSpecial(SpecialDTO dto, String userId) {
		int result = 0;
		return result;
	}

	// 글 삭제
	public int deleteSpecial(int specialNum, String userId) {
		int result = 0;

		return result;
	}

	// 게시물 보기
	public SpecialDTO readSpecial(int specialNum) {
		SpecialDTO dto = null;
		return dto;
	}

	// 조회수 증가
	public int hitCountUpdate(int specialNum) {
		int result = 0;

		return result;
	}

}
