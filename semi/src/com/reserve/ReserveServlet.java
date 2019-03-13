package com.reserve;

import java.io.IOException;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.Iterator;
//import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.room.RoomDTO;
import com.util.MyServlet;
//import com.util.MyUtil;

@WebServlet("/reserve/*")
public class ReserveServlet extends MyServlet {
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
		} else if (uri.indexOf("reserve_detail.do") != -1) {
			reserve_detail(req, resp);
		}
	}

	protected void reserve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ���� ��
		forward(req, resp, "/WEB-INF/views/reserve/reserve.jsp");
	}

	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ���� ���
		// String cp = req.getContextPath();

		ReserveDAO r_dao = new ReserveDAO();
		ReserveDTO r_dto = new ReserveDTO();
		MemberDTO m_dto = new MemberDTO();
		RoomDTO rm_dto = new RoomDTO();

		m_dto.setUserPwd(req.getParameter("pwd"));
		m_dto.setTel(req.getParameter("usertel"));
		m_dto.setIp(req.getRemoteAddr());
		m_dto.setEmail(req.getParameter("useremail"));

		r_dto.setReserveNum(req.getParameter("reserveNum"));
		r_dto.setReserveName(req.getParameter("reservename"));
		r_dto.setReserveCount(Integer.parseInt(req.getParameter("adult")) + Integer.parseInt(req.getParameter("child")));
		r_dto.setReserveMemo(req.getParameter("reserve_memo"));
		r_dto.setStartDay(req.getParameter("startDay"));
		r_dto.setEndDay(req.getParameter("endDay"));
		r_dto.setBarbecue(Integer.parseInt(req.getParameter("barbecue1")) * 20000); // �ٺ�ť ����
		r_dto.setBbcCount(Integer.parseInt(req.getParameter("barbecue1"))); // �ٺ�ť ����
		r_dto.setPrice(Integer.parseInt(req.getParameter("barbecue1")) + Integer.parseInt(req.getParameter("room")));
		r_dto.setBank(req.getParameter("bank"));
		rm_dto.setRoomNum(Integer.parseInt(req.getParameter("room")));
		// r_dto.setRoomNum(Integer.parseInt(req.getParameter("room")));

		
		r_dao.insertReserve(r_dto, m_dto, rm_dto);

		forward(req, resp, "/WEB-INF/views/main/main.jsp");
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

		//ReserveDAO dao = new ReserveDAO();
		
		String reserveNum = req.getParameter("reserveNum");   // �޾ƿ� ���� ����...
		//ReserveDTO dto = dao.readReserve(reserveNum);
		String query = "reserveNum=" + reserveNum;

		resp.sendRedirect(cp + "/reserve/reserve_detail.do?" + query);
	}

	protected void reserve_detail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ����Ȯ�� ����
		ReserveDAO dao = new ReserveDAO();

		String reserveNum = req.getParameter("reserveNum");
		String query = "reserveNum=" + reserveNum;

		ReserveDTO r_dto = dao.readReserve(reserveNum);

		req.setAttribute("r_dto", r_dto);
		req.setAttribute("query", query);

		forward(req, resp, "/WEB-INF/views/reserve/reserve_detail.jsp");

	}

	/*
	 * protected void reserveList(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException, IOException { // ���� ����Ʈ MyUtil util = new MyUtil();
	 * ReserveDAO dao = new ReserveDAO(); String cp = req.getContextPath();
	 * 
	 * // �Ķ���ͷ� �Ѿ�� ������ ��ȣ String page = req.getParameter("page"); int current_page
	 * = 1; if (page != null) current_page = Integer.parseInt(page);
	 * 
	 * // �˻� String searchKey = req.getParameter("searchKey"); String searchValue =
	 * req.getParameter("searchValue"); if (searchKey == null) { searchKey =
	 * "reserve"; searchValue = ""; } // GET ����� �˻� ���ڿ��� ���ڵ��ؼ� �Ķ���Ͱ� �������� �ٽ� ���ڵ��� �ʿ�
	 * if (req.getMethod().equalsIgnoreCase("GET")) { searchValue =
	 * URLDecoder.decode(searchValue, "utf-8"); }
	 * 
	 * // ������ ���� int dataCount; if (searchValue.length() == 0) dataCount =
	 * dao.dataCount(); else dataCount = dao.dataCount(searchKey, searchValue);
	 * 
	 * // ��ü�������� int rows = 10; int total_page = util.pageCount(rows, dataCount); if
	 * (current_page > total_page) current_page = total_page;
	 * 
	 * // �Խù� �������� int start = (current_page - 1) * rows + 1; int end = current_page
	 * * rows;
	 * 
	 * List<ReserveDTO> list; if (searchValue.length() == 0) list =
	 * dao.listReserve(start, end);
	 * 
	 * // ����Ʈ �۹�ȣ ����� int listNum, n = 0; Iterator<ReserveDTO> it = list.iterator();
	 * while (it.hasNext()) { ReserveDTO dto = it.next(); listNum = dataCount -
	 * (start + n - 1); dto.setListNum(listNum); n++; }
	 * 
	 * String query = ""; if (searchValue.length() != 0) { query = "searchKey=" +
	 * searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "utf-8"); }
	 * 
	 * // ����¡ó�� String listUrl = cp + "/reserve/reserveList.do"; String articleUrl =
	 * cp + "/reserve/reserve_detail.do?page="+current_page; if (query.length() !=
	 * 0) { listUrl += "?" + query; articleUrl += "&" + query; }
	 * 
	 * String paging = util.paging(current_page, total_page, listUrl);
	 * 
	 * req.setAttribute("list", list); req.setAttribute("dataCount", dataCount);
	 * req.setAttribute("page", current_page); req.setAttribute("total_page",
	 * total_page); req.setAttribute("articleUrl", articleUrl);
	 * req.setAttribute("paging", paging);
	 * 
	 * forward(req, resp, "/WEB-INF/views/reserve/reserveList.jsp"); }
	 */

}
