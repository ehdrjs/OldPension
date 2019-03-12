package com.reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.member.MemberDTO;
import com.room.RoomDTO;
import com.util.DBConn;

public class ReserveDAO {
	private Connection conn = DBConn.getConnection();
	
	public void insertReserve(ReserveDTO r_dto, MemberDTO m_dto, RoomDTO rm_dto) {
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT ALL");
			sb.append(" INTO client(usernum, pwd, usertel, userip, useremail)");
			sb.append(" VALUES(client_seq.NEXTVAL, ?, ?, ?, ?)");
			sb.append(" INTO reserve(usernum, reservenum, reservename, reservecount, reservememo, startday, endday, barbecue, bank, price, roomprice)");
			sb.append(" VALUES(client_seq.CURRVAL, res_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			sb.append(" INTO reservedetail(reservenum, roomnum)");
			sb.append(" VALUES(res_seq.CURRVAL, ?)");
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
			pstmt.setInt(13, r_dto.getRoomprice());
			pstmt.setInt(14, rm_dto.getRoomNum());
			
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
	
	public ReserveDTO readReserve(String reservenum){
		ReserveDTO dto = null;
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
			pstmt.setString(1, reservenum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ReserveDTO();
				dto.setReserveNum(rs.getString("reservenum"));
				dto.setReserveDate(rs.getString("reservedate"));
				dto.setReserveCount(rs.getInt("reservecount"));
				dto.setBank(rs.getString("bank"));
				dto.setBarbecue(rs.getString("barbecue"));
				dto.setPrice(rs.getInt("priceAll"));
				dto.setStartDay(rs.getString("startDay"));
				dto.setEndDay(rs.getString("endDay"));
				dto.setReserveMemo(rs.getString("reserve_memo"));
				
				dto.setTel(rs.getString("reserve_tel"));
				dto.setEmail(rs.getString("reserve_email"));
				dto.setUserName(rs.getString("reserve_name"));
				
				dto.setRoomNum(rs.getInt("roomNum"));
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
		
		return dto  ;
	}
	
	public void insertReserveDetail(ReserveDTO dto){
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(" INTO reservedetail(reservenum, roomnum)");
			sb.append(" VALUES(?, ?)");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, dto.getReserveNum());
			pstmt.setInt(2, dto.getRoomNum());
			
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
	
}
