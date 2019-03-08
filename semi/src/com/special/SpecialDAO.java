package com.special;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

import oracle.net.aso.p;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	// 글 추가
	public int insertSpecial(SpecialDTO dto) {
		int result = 0;
		PreparedStatement pstm = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT ALL");
			sb.append("	INTO imageFile(fileNum, imageFileName, imageFileSize)");
			sb.append("		VALUES(file_seq.NEXTVAL, ?, ?)");
			sb.append("	INTO special(specialNum, specialSubject, specialContent, ");
			sb.append("		specialStart, specialEnd, userId, fileNum) VALUES (special_seq.NEXTVAL, ?, ?, ?, ?, ?, file_seq.CURRVAL)");
			sb.append("SELECT * FROM dual");
			
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getImageFileName());
			pstm.setLong(2, dto.getImageFileSize());
			pstm.setString(3, dto.getSpecialSubject());
			pstm.setString(4, dto.getSpecialContent());
			pstm.setString(5, dto.getSpecialStart());
			pstm.setString(6, dto.getSpecialEnd());
			pstm.setString(7, dto.getUserId());
			
			result=pstm.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstm!=null) {
				try {
					pstm.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}
	
	// 리스트
		public List<SpecialDTO> listSpecial(int start, int end) {
			List<SpecialDTO> list = new ArrayList<>();
			PreparedStatement pstm = null;
			ResultSet rs = null;
			StringBuffer sb = new StringBuffer();
			
			try {
				sb.append("SELECT * FROM (");
				sb.append("	SELECT ROWNUM rnum, tb.* FROM (");
				sb.append("		SELECT specialSubject, specialDate, specialStart, specialEnd ");
				sb.append("		FROM special");
				sb.append("		ORDER BY specialNum DESC");
				sb.append("	) tb WHERE ROWNUM <= ?");
				sb.append(") WHERE rnum >= ?");
				
				pstm = conn.prepareStatement(sb.toString());
				pstm.setInt(1, end);
				pstm.setInt(2, start);
				
				rs = pstm.executeQuery();
				
				while(rs.next()) {
					SpecialDTO dto = new SpecialDTO();
					
					dto.setSpecialSubject(rs.getString("specialSubject"));
					dto.setSpecialDate(rs.getString("specialDate"));
					dto.setSpecialStart(rs.getString("specialStart"));
					dto.setSpecialEnd(rs.getString("specialEnd"));

					list.add(dto);
				}
				
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				if(rs !=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				if(pstm != null) {
					try {
						pstm.close();
					} catch (Exception e2) {
						
					}
				}
			}
			
			return list;
		}
	
	// 공지글
		public List<SpecialDTO> listSpecial(){
			List<SpecialDTO> list = new ArrayList<>();
			
			return list;
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

	// 데이터 개수
	public int dataCount() {
		int result = 0;
		return result;
	}

	// 조회수 증가
	public int hitCountUpdate(int specialNum) {
		int result = 0;

		return result;
	}

}
