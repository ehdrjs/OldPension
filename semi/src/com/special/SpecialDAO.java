package com.special;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SpecialDAO {

	private Connection conn = DBConn.getConnection();

	// �� �߰�
	public int insertSpecial(SpecialDTO dto) {
		int result = 0;

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
