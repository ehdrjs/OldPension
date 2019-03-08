package com.reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.member.MemberDTO;
import com.util.DBConn;

public class ReserveDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertReserve(ReserveDTO r_dto, MemberDTO m_dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT ALL");
			sb.append(" INTO client(usernum, pwd, usertel, userip, useremail)");
			sb.append(" VALUES(client_seq.NEXTVAL, ?, ?, ?, ?)");
			sb.append(" INTO reserve(usernum, reservenum, reservename, reservecount, reservememo, startday, endday, barbecue, bank, price)");
			sb.append(" VALUES(client_seq.CURRVAL, res_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");
			sb.append(" select * from dual");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, m_dto.getUserPwd());
			pstmt.setString(2, m_dto.getTel());
			pstmt.setString(3, m_dto.getIp());
			pstmt.setString(4, m_dto.getEmail());
			
			pstmt.setString(5, r_dto.getReserveName());
			pstmt.setInt(6, r_dto.getReserveCount());
			pstmt.setString(7, r_dto.getReserveMemo());
			pstmt.setString(8, r_dto.getStartDay());
			pstmt.setString(9, r_dto.getEndDay());
			pstmt.setString(10, r_dto.getBarbecue());
			pstmt.setString(11, r_dto.getBank());
			pstmt.setInt(12, r_dto.getPrice());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public ReserveDTO readReserve_r(int reservenum){
		ReserveDTO r_dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.reservenum, rd.roomnum, r.price, r.reservedate, r.reservecount, r.startday, r.endday, r.reservememo, r.barbecue, r.bank, c.usertel, c.useremail");
			sb.append(" FROM reserve r");
			sb.append(" JOIN client c ON r.usernum = c.usernum");
			sb.append(" JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append(" JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append(" WHERE r.reservenum=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservenum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r_dto = new ReserveDTO();
				r_dto.setReserveNum(rs.getString("reservenum"));
				r_dto.setReserveDate(rs.getString("reservedate"));
				r_dto.setReserveCount(rs.getInt("reservecount"));
				r_dto.setBank(rs.getString("bank"));
				r_dto.setBarbecue(rs.getString("barbecue"));
				r_dto.setPrice(rs.getInt("price"));
				r_dto.setStartDay(rs.getString("startday"));
				r_dto.setEndDay(rs.getString("endday"));
				r_dto.setReserveMemo(rs.getString("reservememo"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return r_dto  ;
	}
	
	public MemberDTO readReserve_m(int reservenum){
		MemberDTO m_dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.reservenum, rm.roomnum, rd.roomnum, r.price, r.reservedate, r.reservecount, r.startday, r.endday, r.reservememo, r.barbecue, r.bank, c.usertel, c.useremail");
			sb.append(" FROM reserve r");
			sb.append(" JOIN client c ON r.usernum = c.usernum");
			sb.append(" JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append(" JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append(" WHERE r.reservenum=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, reservenum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				m_dto = new MemberDTO();
				m_dto.setTel(rs.getString("usertel"));
				m_dto.setEmail(rs.getString("useremail"));
				m_dto.setUserName(rs.getString("username"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return m_dto;
	}
	
}
