package com.review;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
			reviewPage(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			reviewSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			deleteReview(req, resp);
		}
	}
	
	protected void reviewPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		ReviewDAO dao = new ReviewDAO();
		ReviewPaging rp = new ReviewPaging();
		
		
		// 넘어온 페이지
		String page = req.getParameter("page");
		int current_page=1;
		if(page!=null && page.length()!=0)
			current_page=Integer.parseInt(page);
		
		// 전체데이터 개수
		int dataCount = dao.reviewCount();
		
		// 전체 페이지 수 구하기
		int rows=4;
		int total_page = rp.pageCount(rows, dataCount);
		
		// 전체 페이지보다 표시할 페이지가 큰 경우
		if(total_page<current_page)
			current_page=total_page;
		
		// 가져올 데이터의 시작과 끝
		int start = (current_page-1)*rows+1;
		int end=current_page*rows;
		
		
		// 데이터 가져오기
		List<ReviewDTO> list = dao.listReview(start, end);
		
		Iterator<ReviewDTO> it = list.iterator();
		while (it.hasNext()) {
			ReviewDTO dto = it.next();
			
			dto.setReviewContent((dto.getReviewContent()).replaceAll(">", "&gt;"));
			dto.setReviewContent((dto.getReviewContent()).replaceAll("<", "&lt;"));
			dto.setReviewContent((dto.getReviewContent()).replaceAll("\n", "<br>"));
		}
		
		// 페이징 처리
		String query = "?page="+current_page;
		String strUrl=cp+"/review/review.do";
		String paging=rp.paging(current_page, total_page, strUrl);
		
		// review/list.jsp에 넘겨줄 데이터
		if (info==null) {
			req.setAttribute("admin", "logout");
		} else if (info.getUserRole().length()!=0) {
			String admin = info.getUserRole();
			String id = info.getUserId();
			req.setAttribute("admin", admin);
			req.setAttribute("id", id);
		}
		req.setAttribute("query", query);
		req.setAttribute("reviewList", list);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("paging", paging);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("mode", "create");
		
		forward(req, resp, "/WEB-INF/views/review/list.jsp");
	}
	
//	리뷰 저장
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
		int reviewNum=dao.checkReviewNum()+1;
		
		
		dto.setUserId(info.getUserId());
		dto.setUserName(info.getUserName());
		dto.setAdmin(info.getUserRole());
		
		dto.setOrderNo(0);
		dto.setListNum(0);
		dto.setParent(0);
		dto.setDepth(0);
		dto.setReviewNum(reviewNum);
		dto.setReviewContent(req.getParameter("review_inputText"));
		dao.insertReview(dto);
		
		resp.sendRedirect(cp+"/review/review.do");
	}
	
	protected void deleteReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		ReviewDAO dao = new ReviewDAO();
		
		int num=Integer.parseInt(req.getParameter("reviewNum"));
		
		System.out.println(num);
		
		dao.deleteReview(num);
		
		resp.sendRedirect(cp+"/review/review.do");
		
		
	}
	
}
