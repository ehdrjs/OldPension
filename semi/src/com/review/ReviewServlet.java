package com.review;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/review/*")
public class ReviewServlet extends MyServlet {
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
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		
		if(uri.indexOf("review.do") != -1) {
			forward(req, resp, "/WEB-INF/views/review/list.jsp");
		} else if (uri.indexOf("list.do") != -1) {
			
		} else if (uri.indexOf("review.do") != -1) {
			
		} else if (uri.indexOf("write.do") != -1) {
			
		} else if (uri.indexOf("write_ok.do") != -1) {
			
		} else if (uri.indexOf("update.do") != -1) {
			
		} else if (uri.indexOf("update_ok.do") != -1) {
			
		} else if (uri.indexOf("delete.do") != -1) {
			
		}
	}
	
	protected void reviewPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp = req.getContextPath();
		
		
		
	}
	
//	<리뷰 저장>
	protected void reviewSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		ReviewDAO dao = new ReviewDAO();
		ReviewDTO dto = new ReviewDTO();
		
		dto.setUserId(info.getUserId());
		dto.setUserName(info.getUserName());
		dto.setAdmin(info.getUserRole());
		
		dto.setOrderNo(1);
		dto.setListNum(1);
		dto.setParent(1);
		dto.setDepth(1);
		
		dao.insertReview(dto);
		
		resp.sendRedirect(cp+"/guest/guest.do");
		
		
	}
	
}
