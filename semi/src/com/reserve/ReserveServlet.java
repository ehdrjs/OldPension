package com.reserve;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDAO;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.room.RoomDTO;
import com.util.DBConn;
import com.util.MyServlet;

@WebServlet("/reserve/*")
public class ReserveServlet extends MyServlet {
	private Connection conn = DBConn.getConnection();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		if (uri.indexOf("reserve.do") != -1) {
			reserve(req, resp);
		} else if (uri.indexOf("reserveSubmit.do") != -1) {
			reserveSubmit(req, resp);
		} else if (uri.indexOf("reserve_confirm.do") != -1) {
			reserve_confirm(req, resp);
		} else if (uri.indexOf("reserve_confirm2.do") != -1) {
			reserve_confirm2(req, resp);
		} else if (uri.indexOf("reserve_detail.do") != -1) {
			reserve_detail(req, resp);
		} else if (uri.indexOf("reserve_detailMem.do") != -1) {
			reserve_detailMem(req, resp);
		} else if (uri.indexOf("reserveList.do") != -1) {
			reserveList(req, resp);
		}
	}

	protected void reserve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� ��
		forward(req, resp, "/WEB-INF/views/reserve/reserve.jsp");
	}

	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ���� ���
		String cp = req.getContextPath();

		ReserveDAO r_dao = new ReserveDAO();
		ReserveDTO r_dto = new ReserveDTO();
		MemberDTO m_dto = new MemberDTO();
		RoomDTO rm_dto = new RoomDTO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		r_dto.setReserveNum(req.getParameter("reserveNum"));
		r_dto.setReserveName(req.getParameter("reservename"));
		r_dto.setReserveCount(
				Integer.parseInt(req.getParameter("adult")) + Integer.parseInt(req.getParameter("child")));
		r_dto.setReserveMemo(req.getParameter("reserve_memo"));
		r_dto.setStartDay(req.getParameter("startDay"));
		r_dto.setEndDay(req.getParameter("endDay"));
		r_dto.setBarbecue(Integer.parseInt(req.getParameter("barbecue1")) * 20000); // �ٺ�ť ����
		r_dto.setBbcCount(Integer.parseInt(req.getParameter("barbecue1"))); // �ٺ�ť ����
		r_dto.setPrice(Integer.parseInt(req.getParameter("barbecue1")) + Integer.parseInt(req.getParameter("room")));
		r_dto.setBank(req.getParameter("bank"));
		rm_dto.setRoomNum(Integer.parseInt(req.getParameter("room")));

		if (info != null) { // ȸ��
			// r_dto.setTel(req.getParameter("usertel"));
			// r_dto.setEmail(req.getParameter("useremail"));

		} else { // ��ȸ��
			m_dto.setUserPwd(req.getParameter("pwd"));
			m_dto.setTel(req.getParameter("usertel"));
			m_dto.setIp(req.getRemoteAddr());
			m_dto.setEmail(req.getParameter("useremail"));
		}

		r_dao.insertReserve(r_dto, m_dto, rm_dto);

		resp.sendRedirect(cp + "/main.do");
	}

	protected void reserve_confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ����Ȯ�� ���� �� �α��� ���� Ȯ��
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		ReserveDAO dao = new ReserveDAO();

		/*
		 * if(info!=null) { String usernum = info.getUserNum();
		 * System.out.print(usernum); String query2 = "usernum="+usernum;
		 * 
		 * resp.sendRedirect(cp + "/reserve/reserve_detail.do?"+ query2); }
		 */

		String reserveNum = "";
		ReserveDTO dto1 = dao.readReserve(reserveNum);
		reserveNum = dto1.getReserveNum();
		String query1 = "reserveNum=" + reserveNum;

		resp.sendRedirect(cp + "/reserve/reserve_detail.do?" + query1);
	}

	protected void reserve_confirm2(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ����Ȯ�� ���� �� �α��� ���� Ȯ��
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		ReserveDAO r_dao = new ReserveDAO();
		
		if (info != null) {
			String usernum = info.getUserNum();
			System.out.print(usernum);
			String query2 = "usernum=" + usernum;

			List<ReserveDTO> list = r_dao.listReserve(usernum); 
			  
			  req.setAttribute("list", list); 
			  req.setAttribute("query2", query2);
			  
			resp.sendRedirect(cp + "/reserve/reserveList.do?" + query2);
		}

	}

	protected void reserve_detail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ����Ȯ�� ����
		ReserveDAO r_dao = new ReserveDAO();
		String reserveNum = req.getParameter("reserveNum");
		String query = "reserveNum=" + reserveNum;

		ReserveDTO r_dto = r_dao.readReserve(reserveNum);
		
		req.setAttribute("r_dto", r_dto);
		req.setAttribute("query", query);

		forward(req, resp, "/WEB-INF/views/reserve/reserve_detail.jsp");

	}

	protected void reserve_detailMem(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ����Ȯ�� ����
		ReserveDAO r_dao = new ReserveDAO();
		MemberDAO m_dao = new MemberDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String userId = info.getUserId();
		String usernum = info.getUserNum();
		String query = "usernum=" + usernum;

		ReserveDTO r_dto = r_dao.readMemReserve(userId);
		MemberDTO m_dto = m_dao.readMember(userId);

		req.setAttribute("r_dto", r_dto);
		req.setAttribute("m_dto", m_dto);
		req.setAttribute("query", query);

		forward(req, resp, "/WEB-INF/views/reserve/reserve_detail.jsp");
	}

	protected void reserveList(HttpServletRequest req, HttpServletResponse resp)
	  throws ServletException, IOException { 
		// ���� ����Ʈ
	  ReserveDAO r_dao = new ReserveDAO();
	  //MemberDAO m_dao = new MemberDAO();
	  MemberDTO m_dto = new MemberDTO();
	  //String cp = req.getContextPath();
	  
	  HttpSession session = req.getSession();
	  //SessionInfo info = (SessionInfo) session.getAttribute("member");
	  String usernum = m_dto.getUserNum();
	  
	  List<ReserveDTO> list = r_dao.listReserve(usernum); 
	  
	  String query = "usernum=" + usernum; 
	  
	  
	  //String paging = util.paging(current_page, total_page, listUrl);
	  
	  req.setAttribute("list", list); 
	  req.setAttribute("query", query);
	  //req.setAttribute("page", current_page); req.setAttribute("total_page", total_page); 
	  //req.setAttribute("articleUrl", articleUrl);
	  //req.setAttribute("paging", paging);
	  
	  forward(req, resp, "/WEB-INF/views/reserve/reserveList.jsp"); 
	  }

}
