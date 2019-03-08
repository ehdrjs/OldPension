package com.reserve;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		} else if(uri.indexOf("reserve_detail.do")!=-1) {
			reserve_detail(req, resp);
		}
	}
	
	protected void reserve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forward(req, resp, "/WEB-INF/views/reserve/reserve.jsp");
	}
	
	protected void reserve_detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/reserve/reserve.jsp");
			return;
		}
		
		ReserveDAO dao = new ReserveDAO();
		ReserveDTO dto = new ReserveDTO();
		
		dto.setReserveName(req.getParameter("reserve_name"));
		dto.setReserveCount(Integer.parseInt(req.getParameter("adult")+req.getParameter("child")));
		dto.setReserveMemo(req.getParameter("reserve_memo"));
		dto.setStartDay(req.getParameter("startDay"));
		dto.setEndDay(req.getParameter("endDay"));
		
		dao.insertReserve(dto);
		
		resp.sendRedirect(cp+"/reserve/reserve_detail.jsp");
	}
	
}
