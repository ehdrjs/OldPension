package com.reserve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			
			if(m_dto.getUserNum()==null) {		// 비회원
				sb.append(" INTO client(pwd, usertel, userip, useremail, usernum)");
				sb.append(" VALUES(?, ?, ?, ?, client_seq.NEXTVAL)");
			} 			
			sb.append(" INTO reserve(reservenum, reservename, reservecount, reservememo, startday, ");
			sb.append(" 			 endday, barbecue, bank, priceall, roomprice, bbccount, usernum)");
			if(m_dto.getUserNum()!=null) {	//회원
				sb.append(" 	VALUES(res_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			} else {		// 비회원
				sb.append(" 	VALUES(res_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, client_seq.CURRVAL)");
			}
			sb.append(" INTO reservedetail(reservenum, roomnum)");
			sb.append(" 	VALUES(res_seq.CURRVAL, ?)");
			sb.append(" select * from dual");
			
			pstmt = conn.prepareStatement(sb.toString());
			System.out.println(sb);
			if(m_dto.getUserNum()!=null) {	//회원	
				
				pstmt.setString(1, r_dto.getReserveName());
				pstmt.setInt(2, r_dto.getReserveCount());
				pstmt.setString(3, r_dto.getReserveMemo());
				pstmt.setString(4, r_dto.getStartDay());
				pstmt.setString(5, r_dto.getEndDay());
				pstmt.setInt(6, r_dto.getBarbecue());
				pstmt.setString(7, r_dto.getBank());
				pstmt.setInt(8, r_dto.getPrice());
				pstmt.setInt(9, r_dto.getRoomPrice());
				pstmt.setInt(10,  r_dto.getBbcCount());
				pstmt.setString(11, m_dto.getUserNum());
				pstmt.setInt(12, rm_dto.getRoomNum());

			} else {
				pstmt.setString(1, m_dto.getUserPwd());
				pstmt.setString(2, m_dto.getTel());
				pstmt.setString(3, m_dto.getIp());
				pstmt.setString(4, m_dto.getEmail());
				
				pstmt.setString(5, r_dto.getReserveName());
				pstmt.setInt(6, r_dto.getReserveCount());
				pstmt.setString(7, r_dto.getReserveMemo());
				pstmt.setString(8, r_dto.getStartDay());
				pstmt.setString(9, r_dto.getEndDay());
				pstmt.setInt(10, r_dto.getBarbecue());
				pstmt.setString(11, r_dto.getBank());
				pstmt.setInt(12, r_dto.getPrice());
				pstmt.setInt(13, r_dto.getRoomPrice());
				pstmt.setInt(14,  r_dto.getBbcCount());
				
				pstmt.setInt(15, rm_dto.getRoomNum());
				
			}
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	// 비회원 예약조회
	public ReserveDTO readReserve(String reservenum){
		ReserveDTO r_dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.reservenum, rd.roomnum, rm.roomname, r.priceall, rp.price, r.reservedate, r.reservecount, r.startday, r.endday, r.reservememo, r.barbecue, r.bbccount, r.bank, r.reservename, c.usertel, c.useremail");
			sb.append(" FROM reserve r");
			sb.append(" JOIN client c ON r.usernum = c.usernum");
			sb.append(" JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append(" JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append(" JOIN roomprice rp ON rp.roomnum = rm.roomnum");
			sb.append(" WHERE r.reservenum=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, reservenum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r_dto = new ReserveDTO();
				r_dto.setReserveNum(rs.getString("reservenum"));
				r_dto.setReserveDate(rs.getString("reservedate"));
				r_dto.setReserveCount(rs.getInt("reservecount"));
				r_dto.setBank(rs.getString("bank"));
				r_dto.setBarbecue(rs.getInt("barbecue"));
				r_dto.setBbcCount(rs.getInt("bbcCount"));
				r_dto.setPrice(rs.getInt("barbecue")+rs.getInt("price"));
				r_dto.setRoomPrice(rs.getInt("price"));
				r_dto.setStartDay(rs.getDate("startday").toString());
				r_dto.setEndDay(rs.getDate("endday").toString());
				r_dto.setReserveMemo(rs.getString("reservememo"));
				
				r_dto.setTel(rs.getString("usertel"));
				r_dto.setEmail(rs.getString("useremail"));
				r_dto.setReserveName(rs.getString("reservename"));
				
				r_dto.setRoomNum(rs.getInt("roomnum"));
				r_dto.setRoomName(rs.getString("roomname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	// 회원 예약조회
	public ReserveDTO readMemReserve(String usernum){
		ReserveDTO r_dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("SELECT r.reservenum, rd.roomnum, rm.roomname, r.priceall, rp.price, r.reservedate, r.reservecount, r.startday, r.endday, r.reservememo, r.barbecue, r.bbccount, r.bank, r.reservename, c.usertel, c.useremail");
			sb.append(" FROM reserve r");
			sb.append(" JOIN client c ON r.usernum = c.usernum");
			sb.append(" JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append(" JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append(" JOIN roomprice rp ON rp.roomnum = rm.roomnum");
			sb.append(" WHERE r.usernum=?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, usernum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r_dto = new ReserveDTO();
				r_dto.setReserveNum(rs.getString("reservenum"));
				r_dto.setReserveDate(rs.getString("reservedate"));
				r_dto.setReserveCount(rs.getInt("reservecount"));
				r_dto.setBank(rs.getString("bank"));
				r_dto.setBarbecue(rs.getInt("barbecue"));
				r_dto.setBbcCount(rs.getInt("bbcCount"));
				r_dto.setPrice(rs.getInt("barbecue")+rs.getInt("price"));
				r_dto.setRoomPrice(rs.getInt("price"));
				r_dto.setStartDay(rs.getDate("startday").toString());
				r_dto.setEndDay(rs.getDate("endday").toString());
				r_dto.setReserveMemo(rs.getString("reservememo"));
				
				r_dto.setTel(rs.getString("usertel"));
				r_dto.setEmail(rs.getString("useremail"));
				r_dto.setReserveName(rs.getString("reservename"));
				
				r_dto.setRoomNum(rs.getInt("roomnum"));
				r_dto.setRoomName(rs.getString("roomname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM reserve";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	public List<ReserveDTO> listReserve(String usernum) { // 예약번호에 따른 예약 리스트
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {

			sb.append("SELECT r.reservenum, r.reservename, c.usernum, rd.roomnum, rm.roomname, r.priceall,");
			sb.append("     rp.price, r.reservedate, r.reservecount, r.startday, r.endday,");
			sb.append("     r.reservememo, r.barbecue, r.bbccount, r.bank, ");
			sb.append("     c.usertel, c.useremail");
			sb.append("  FROM reserve r");
			sb.append("  JOIN client c ON r.usernum = c.usernum");
			sb.append("  JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append("  JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append("  JOIN roomprice rp ON rp.roomnum = rm.roomnum");
			sb.append("  WHERE r.usernum = ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, usernum);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReserveDTO r_dto = new ReserveDTO();
				
				r_dto.setReserveNum(rs.getString("reservenum"));
				r_dto.setReserveDate(rs.getDate("reservedate").toString());
				r_dto.setReserveCount(rs.getInt("reservecount"));
				r_dto.setBank(rs.getString("bank"));
				r_dto.setBarbecue(rs.getInt("barbecue"));
				r_dto.setBbcCount(rs.getInt("bbcCount"));
				r_dto.setPrice(rs.getInt("barbecue")+rs.getInt("price"));
				r_dto.setRoomPrice(rs.getInt("price"));
				r_dto.setStartDay(rs.getDate("startday").toString());
				r_dto.setEndDay(rs.getDate("endday").toString());
				r_dto.setReserveMemo(rs.getString("reservememo"));
				
				r_dto.setTel(rs.getString("usertel"));
				r_dto.setEmail(rs.getString("useremail"));
				r_dto.setReserveName(rs.getString("reservename"));
				
				r_dto.setRoomNum(rs.getInt("roomnum"));
				r_dto.setRoomName(rs.getString("roomname"));
				
				list.add(r_dto);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
	
	public List<ReserveDTO> listReserve() { // 예약번호에 따른 예약 리스트
		List<ReserveDTO> list = new ArrayList<ReserveDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {

			sb.append("SELECT r.reservenum, r.reservename, c.usernum, rd.roomnum, rm.roomname, r.priceall,");
			sb.append("     rp.price, r.reservedate, r.reservecount, r.startday, r.endday,");
			sb.append("     r.reservememo, r.barbecue, r.bbccount, r.bank, ");
			sb.append("     c.usertel, c.useremail");
			sb.append("  FROM reserve r");
			sb.append("  JOIN client c ON r.usernum = c.usernum");
			sb.append("  JOIN reservedetail rd ON r.reservenum = rd.reservenum");
			sb.append("  JOIN room rm ON rd.roomnum = rm.roomnum");
			sb.append("  JOIN roomprice rp ON rp.roomnum = rm.roomnum");


			pstmt = conn.prepareStatement(sb.toString());


			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReserveDTO r_dto = new ReserveDTO();
				
				r_dto.setReserveNum(rs.getString("reservenum"));
				r_dto.setReserveDate(rs.getDate("reservedate").toString());
				r_dto.setReserveCount(rs.getInt("reservecount"));
				r_dto.setBank(rs.getString("bank"));
				r_dto.setBarbecue(rs.getInt("barbecue"));
				r_dto.setBbcCount(rs.getInt("bbcCount"));
				r_dto.setPrice(rs.getInt("barbecue")+rs.getInt("price"));
				r_dto.setRoomPrice(rs.getInt("price"));
				r_dto.setStartDay(rs.getDate("startday").toString());
				r_dto.setEndDay(rs.getDate("endday").toString());
				r_dto.setReserveMemo(rs.getString("reservememo"));
				
				r_dto.setTel(rs.getString("usertel"));
				r_dto.setEmail(rs.getString("useremail"));
				r_dto.setReserveName(rs.getString("reservename"));
				
				r_dto.setRoomNum(rs.getInt("roomnum"));
				r_dto.setRoomName(rs.getString("roomname"));
				
				list.add(r_dto);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}
	
}
