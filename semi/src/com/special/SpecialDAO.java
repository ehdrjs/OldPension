package com.special;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	// �� �߰�
	public int insertSpecial(SpecialDTO dto) {
		int result = 0;
		PreparedStatement pstm = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT ALL");
			sb.append("	INTO imageFile(fileNum, imageFileName, imageFileSize)");
			sb.append("		VALUES(file_seq.NEXTVAL, ?, ?");
			sb.append("	INTO special(specialNum, specialSubject, specialContent, ");
			sb.append("		specialStart, specialEnd, userId) VALUES (special_seq.NEXTVAL, ?, ?, ?, ?, ?)");
			sb.append("SELECT * FROM dual");
			
			pstm = conn.prepareStatement(sb.toString());
			pstm.setString(1, dto.getImageFileName());
			pstm.setInt(2, dto.getImageFileSize());
			pstm.setString(3, dto.getSpecialSubject());
			pstm.setString(4, dto.getSpecialContent());
			pstm.setString(5, dto.getSpecialStart());
			pstm.setString(6, dto.getSpecialEnd());
			pstm.setString(7, dto.getUserId());
			
			pstm.executeUpdate();
			
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

	// �� ����
	public int updateSpecial(SpecialDTO dto, String userId) {
		int result = 0;
		return result;
	}

	// �� ����
	public int deleteSpecial(int specialNum, String userId) {
		int result = 0;

		return result;
	}

	// �޷� ����Ʈ
	public List<SpecialDTO> listSpecial(int start, int end) {
		List<SpecialDTO> list = new ArrayList<>();

		return list;
	}

	// �Խù� ����
	public SpecialDTO readSpecial(int specialNum) {
		SpecialDTO dto = null;
		return dto;
	}

	// ������ ����
	public int dataCount() {
		int result = 0;
		return result;
	}

	// ��ȸ�� ����
	public int hitCountUpdate(int specialNum) {
		int result = 0;

		return result;
	}

}
