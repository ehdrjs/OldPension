package com.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class RoomDAO {
	private Connection conn = DBConn.getConnection();
	

	
// 	<방 등록>
	public int insertRoom(RoomDTO dto) {
		int result = 0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		int count = (listRoom().size()) + 1;
		
		try {
			sb.append("INSERT INTO room(");
			sb.append("roomNum, roomName, roomContent, isRoomOK, roomMin, roomMax");
			sb.append(") VALUES ("+count+", ?, ?, ?, ?, ?)");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getRoomName());
			pstmt.setString(2, dto.getRoomContent());
			pstmt.setString(3, dto.getIsRoomOK());
			pstmt.setInt(4, dto.getRoomMin());
			pstmt.setInt(5, dto.getRoomMax());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
// 	<방 가격 등록>
	public int insertPrice(int num, int weeknum, int price) {
		int result = 0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("INSERT INTO roomPrice(");
			sb.append("roomNum, week, price");
			sb.append(") VALUES ("+num+", ?, ? ) ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, weeknum);
			pstmt.setInt(2, price);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
	
// 	<방 수정>
	public int updateRoom(RoomDTO dto) {
		int result = 0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE room SET ");
			sb.append("roomName=?, roomContent=?, isRoomOK=?, roomMin=?, roomMax=? ");
			sb.append("WHERE roomNum=? ");
	
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getRoomName());
			pstmt.setString(2, dto.getRoomContent());
			pstmt.setString(3, dto.getIsRoomOK());
			pstmt.setInt(4, dto.getRoomMin());
			pstmt.setInt(5, dto.getRoomMax());
			pstmt.setInt(6, dto.getRoomNum());
			
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
// 	<방 가격 수정>
	public int updatePrice(int num, int week, int price) {
		int result = 0;
		PreparedStatement pstmt=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("UPDATE roomPrice SET ");
			sb.append("price=?");
			sb.append("WHERE roomNum=? AND week=? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, price);
			pstmt.setInt(2, num);
			pstmt.setInt(3, week);
			result=pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
	
	
//	<방 리스트>
	public List<RoomDTO> listRoom() {
		List<RoomDTO> list = new ArrayList<RoomDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb=new StringBuffer();
		
		try {
			// sb.append(" ");
			sb.append(" SELECT roomNum, roomName, roomContent, isRoomOK, roomMin, roomMax  ");
			sb.append(" FROM room ORDER BY roomNum ASC");
			
			pstmt = conn.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				RoomDTO dto = new RoomDTO();
				
				dto.setRoomNum(rs.getInt("roomNum"));
				dto.setRoomName(rs.getString("roomName"));
				dto.setRoomContent(rs.getString("roomContent"));
				dto.setIsRoomOK(rs.getString("isRoomOK"));
				dto.setRoomMin(rs.getInt("roomMin"));
				dto.setRoomMax(rs.getInt("roomMax"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
//	<글보기>
	public RoomDTO viewRoom(int roomNum) {
		RoomDTO dto = new RoomDTO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// sb.append(" ");
			sql = "SELECT roomName, roomContent, isRoomOK, roomMin, roomMax FROM room WHERE roomNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setRoomName(rs.getString("roomName"));
				dto.setRoomContent(rs.getString("roomContent"));
				if (rs.getString("isRoomOK").equals("OK")) {
					dto.setIsRoomOK("가능");
				} else if(rs.getString("isRoomOK").equals("가능")) { 
					dto.setIsRoomOK("가능");
				} else {
					dto.setIsRoomOK("불가");
				}
				dto.setRoomMin(rs.getInt("roomMin"));
				dto.setRoomMax(rs.getInt("roomMax"));
				dto.setRoomNum(roomNum);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
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
		
		return dto;
	}
	
//	<방 가격리스트>
	public List<RoomDTO> viewPrice(int roomNum) {
		List<RoomDTO> list = new ArrayList<RoomDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			// sb.append(" ");
			sql = "	SELECT week, price FROM roomPrice WHERE roomNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				RoomDTO dto = new RoomDTO();
				
				dto.setPrice(rs.getInt("price"));
				
				if (rs.getInt("week")==1) {
					dto.setWeek("주중");
				} else if (rs.getInt("week")==2) {
					dto.setWeek("주말");
				} else if (rs.getInt("week")==3) {
					dto.setWeek("성수기");
				} else {
					dto.setWeek("미정");
				}
				
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
	public int deleteRoom(int roomNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM room WHERE roomNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
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
	
	public int deletePrice(int roomNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM roomPrice WHERE roomNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
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
