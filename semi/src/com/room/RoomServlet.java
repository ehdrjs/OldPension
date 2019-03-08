package com.room;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/room/*")
public class RoomServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		
		if(uri.indexOf("room.do") != -1) {
			forward(req, resp, "/WEB-INF/views/room/room.jsp");
		} else if (uri.indexOf("list.do") != -1) {
			forward(req, resp, "/WEB-INF/views/room/list.jsp");
		} else if (uri.indexOf("review.do") != -1) {
			forward(req, resp, "/WEB-INF/views/review/list.jsp");
		} else if (uri.indexOf("write.do") != -1) {
			forward(req, resp, "/WEB-INF/views/room/write.jsp");
		}
		// 세션설정
		// userId , userName,
		/*
		HttpSession session = req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			
		}
		*/
	}

	protected void room(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "room");
	}
	
	protected void created(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// article_c
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// article_c_
	}
	
}
