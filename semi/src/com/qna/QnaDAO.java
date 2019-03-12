package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	public int insertQna(QnaDTO dto, String mode) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;

		try {
			sql = "SELECT qna_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			seq = 0;
			if (rs.next())
				seq = rs.getInt(1);
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;

			dto.setNum(seq);
			if (mode.equals("created")) {
				dto.setGroupNum(seq);
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0);
			}
			sql = "INSERT INTO qna(qnaNum, userId, qnaSubject, qnaContent, ";
			sql += " groupNum, depth, orderNo, parent, qnaPwd) ";
			sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getDepth());
			pstmt.setInt(7, dto.getOrderNo());
			pstmt.setInt(8, dto.getParent());
			pstmt.setString(9, dto.getPwd());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}

	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*),0) FROM qna";
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
				} catch (SQLException e) {
				}
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}

		return result;
	}

	public int dataCount(String searchKey, String searchValue) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			if (searchKey.equals("qnaDate")) {
				searchValue = searchValue.replaceAll("-", "");
				sql = "SELECT NVL(COUNT(*),0) FROM qna q JOIN member m ON q.userId=m.userId WHERE TO_CHAR(qnaDate, 'YYYYMMDD') = ? ";
			} else if (searchKey.equals("userId")) {
				sql = "SELECT NVL(COUNT(*),0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(q.userId, ?) =1 ";
			} else {
				sql = "SELECT NVL(COUNT(*),0) FROM qna q JOIN member m ON q.userId=m.userId WHERE INSTR(" + searchKey
						+ ", ?) >=1 ";
			}

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchValue);

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

	public List<QnaDTO> listQna(int start, int end) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM (");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("			SELECT qnaNum, q.userId, qnaPwd, ");
			sb.append("			qnaSubject, groupNum, orderNo, depth, qnaCount,");
			sb.append("			TO_CHAR(qnaDate, 'YYYY-MM-DD') qnaDate");
			sb.append("			FROM qna q");
			sb.append("			ORDER BY groupNum DESC, orderNo ASC");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setPwd(rs.getString("qnaPwd"));
				dto.setSubject(rs.getString("qnaSubject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setCount(rs.getInt("qnaCount"));
				dto.setCreated(rs.getString("qnaDate"));

				list.add(dto);
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

	public List<QnaDTO> listQna(int start, int end, String searchKey, String searchValue) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT * FROM (");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("			SELECT qnaNum, q.userId, qnaPwd, ");
			sb.append("			qnaSubject, groupNum, orderNo, depth, qnaCount, ");
			sb.append("			TO_CHAR(qnaDate, 'YYYY-MM-DD') qnaDate ");
			sb.append("			FROM qna q");
			sb.append("			JOIN member m ON q.userId=m.userId");
			if (searchKey.equals("qnaDate")) {
				searchValue = searchValue.replaceAll("-", "");
				sb.append("		WHERE TO_CHAR(qnaDate, 'YYYYMMDD')= ? ");
			} else if (searchKey.equals("userId")) {
				sb.append("		WHERE INSTR(q.userId, ?) = 1 ");
			} else {
				sb.append("		WHERE INSTR(" + searchKey + ", ?) >=1 ");
			}
			sb.append("			ORDER BY groupNum DESC, orderNo ASC ");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, searchValue);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();
				dto.setNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setPwd(rs.getString("qnaPwd"));
				dto.setSubject(rs.getString("qnaSubject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setCount(rs.getInt("qnaCount"));
				dto.setCreated(rs.getString("qnaDate"));

				list.add(dto);
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

	public QnaDTO readQna(int qnaNum) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT q.qnaNum, q.userId, qnaPwd, qnaSubject, qnaContent, groupNum, orderNo, depth, ");
			sb.append("qnaCount, TO_CHAR(qnaDate, 'YYYYMMDD') qnaDate FROM qna q JOIN member m ON q.userId=m.userId ");
			sb.append(" WHERE qnaNum= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qnaNum);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto=new QnaDTO();
				dto.setNum(rs.getInt("qnaNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setPwd(rs.getString("qnaPwd"));
				dto.setSubject(rs.getString("qnaSubject"));
				dto.setContent(rs.getString("qnaContent"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setCount(rs.getInt("qnaCount"));
				dto.setDate(rs.getString("qnaDate"));
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

		return dto;
	}

	public int updateQnaCount(int qnaNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		sql = "UPDATE qna SET qnaCount=qnaCount+1 WHERE qnaNum=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qnaNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	public QnaDTO preReadQna(int groupNum, int orderNo, String searchKey, String searchValue) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (searchValue != null && searchValue.length() != 0) {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append(" SELECT qnaNum, qnaSubject ");
				sb.append("		FROM qna q");
				sb.append("		JOIN memeber m ON q.userId=m.userId	");
				if (searchKey.equals("qnaDate")) {
					searchValue = searchValue.replaceAll("-", "");
					sb.append("		WHERE (TO_CHAR(qnaDate, 'YYYYMMDD') = ? ) AND ");
				} else if (searchKey.equals("userId")) {
					sb.append("		WHERE (INSTR(userId,?) =1 ) AND ");
				} else {
					sb.append("		WHERE (INSTR(" + searchKey + ", ? >=1 ) AND ");
				}
				sb.append("		((groupNum= ? AND orderNo < ? ) ");
				sb.append("		OR (groupNum > ? )) ");
				sb.append("		ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM =1 ");

				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, searchValue);
				pstmt.setInt(2, groupNum);
				pstmt.setInt(3, orderNo);
				pstmt.setInt(4, groupNum);
			} else {
				sb.append("SELECT ROWNUM, tb.* FROM (");
				sb.append("		SELECT qnaNum, qnaSubject FROM qna q JOIN member m ON q.userId=m.userId ");
				sb.append("		WHERE (groupNum= ? AND orderNo < ? ) ");
				sb.append("		OR (groupNum > ? ) ");
				sb.append("		ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM =1 ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setInt(2, orderNo);
				pstmt.setInt(3, groupNum);
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("qnaNum"));
				dto.setSubject(rs.getString("qnaSubject"));
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
		return dto;
	}
	
	public QnaDTO nextReadQna(int groupNum, int orderNo, String searchKey, String searchValue) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			if (searchValue != null && searchValue.length() != 0) {
				sb.append("SELECT ROWNUM, tb.* FROM ( ");
				sb.append(" SELECT qnaNum, qnaSubject ");
				sb.append("		FROM qna q");
				sb.append("		JOIN memeber m ON q.userId=m.userId	");
				if (searchKey.equals("qnaDate")) {
					searchValue = searchValue.replaceAll("-", "");
					sb.append("		WHERE (TO_CHAR(qnaDate, 'YYYYMMDD') = ? ) AND ");
				} else if (searchKey.equals("userId")) {
					sb.append("		WHERE (INSTR(userId,?) =1 ) AND ");
				} else {
					sb.append("		WHERE (INSTR(" + searchKey + ", ? >=1 ) AND ");
				}
				sb.append("		((groupNum= ? AND orderNo > ? ) ");
				sb.append("		OR (groupNum < ? )) ");
				sb.append("		ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM =1 ");

				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, searchValue);
				pstmt.setInt(2, groupNum);
				pstmt.setInt(3, orderNo);
				pstmt.setInt(4, groupNum);
			} else {
				sb.append("SELECT ROWNUM, tb.* FROM (");
				sb.append("		SELECT qnaNum, qnaSubject FROM qna q JOIN member m ON q.userId=m.userId ");
				sb.append("		WHERE (groupNum= ? AND orderNo > ? ) ");
				sb.append("		OR (groupNum < ? ) ");
				sb.append("		ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM =1 ");

				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, groupNum);
				pstmt.setInt(2, orderNo);
				pstmt.setInt(3, groupNum);
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setNum(rs.getInt("qnaNum"));
				dto.setSubject(rs.getString("qnaSubject"));
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
		return dto;
	}
	
	public int updateQna(QnaDTO dto, String userId) {
		int result = 0;
		PreparedStatement pstmt=null;
		String sql;
		
		sql="UPDATE qna SET qnaSubject=?, qnaContent=? ";
		sql+=" WHERE qnaNum =? AND userId=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getNum());
			pstmt.setString(4, userId);
			result=pstmt.executeUpdate();
		}catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return result;
	}
}