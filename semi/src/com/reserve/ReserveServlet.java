package com.reserve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.MyServlet;

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
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
				
		String uri = req.getRequestURI();
		
		if(uri.indexOf("reserve.do")!=-1) {
			reserve(req, resp);
		} else if(uri.indexOf("reserveSubmit.do")!=-1) {
			reserveSubmit(req, resp);
		} else if(uri.indexOf("reserve_confirm.do")!=-1) {
			reserve_confirm(req, resp);
		} else if(uri.indexOf("reserve_detail.do")!=-1) {
			reserve_detail(req, resp);
		}
	}
	
	protected void reserve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/reserve/reserve.jsp");
	}
	
	protected void reserveSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		ReserveDAO r_dao = new ReserveDAO();
		ReserveDTO r_dto = new ReserveDTO();
		MemberDTO m_dto = new MemberDTO();
		
		m_dto.setUserPwd(req.getParameter("reserve_pwd"));
		m_dto.setTel(req.getParameter("reserve_tel"));
		m_dto.setIp(req.getRemoteAddr());
		m_dto.setEmail(req.getParameter("reserve_email"));
		
		r_dto.setReserveName(req.getParameter("reserve_name"));
		r_dto.setReserveCount(Integer.parseInt(req.getParameter("adult"))+Integer.parseInt(req.getParameter("child")));
		r_dto.setReserveMemo(req.getParameter("reserve_memo"));
		r_dto.setStartDay(req.getParameter("startDay"));
		r_dto.setEndDay(req.getParameter("endDay"));
		r_dto.setBarbecue(req.getParameter("barbecue1"));
		r_dto.setPrice(Integer.parseInt(req.getParameter("barbecue1"))+Integer.parseInt(req.getParameter("room")));
		r_dto.setBank(req.getParameter("bank"));
		
		r_dao.insertReserve(r_dto, m_dto);
		
		resp.sendRedirect(cp+"/reserve/reserve_detail.do");
	}
	
	protected void reserve_confirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		} 
		
		resp.sendRedirect(cp+"/reserve/reserve_detail.do");
	}
	
	protected void reserve_detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 예약내역 보기
		//ReserveDAO dao = new ReserveDAO();
		// String cp = req.getContextPath();
		
		
	}
	
}
