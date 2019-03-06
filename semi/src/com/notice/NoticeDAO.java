package com.notice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class NoticeDAO {
	
	public List<NoticeDTO> readNotice(int start, int end){
		List<NoticeDTO> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}
}
