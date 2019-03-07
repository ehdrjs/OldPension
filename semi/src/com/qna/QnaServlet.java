package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		String uri=req.getRequestURI();  
		
		if(uri.indexOf("qna.do")!=-1) {
			qna(req, resp);
		}else if(uri.indexOf("created.do")!=-1) {
			createdForm(req,resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}
	}

	private void qna(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		MyUtil util=new MyUtil();
		QnaDAO dao=new QnaDAO();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page !=null) {
			current_page = Integer.parseInt(page);
		}
		
		String searchKey = req.getParameter("qnaSearchKey");
		String searchValue = req.getParameter("qnaSearchValue");
		if(searchKey==null) {
			searchKey="qnaSubject";
			searchValue="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue=URLDecoder.decode(searchValue,"utf-8");
		}
		
		int dataCount;
		if(searchValue.length()==0)
			dataCount = dao.dataCount();
		else
			dataCount=dao.dataCount(searchKey, searchValue);
		
		int rows = 5;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int start = (current_page -1)* rows +1;
		int end = current_page * rows;
		
		List<QnaDTO> list;
		if(searchValue.length()==0) {
			list=dao.listQna(start, end);
		}else {
			list = dao.listQna(start, end, searchKey, searchValue);
		}
		
		forward(req, resp, "/WEB-INF/views/qna/qna.jsp");

	}
	
	private void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");

	}

	private void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/qna.do");
			return;
		}
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		QnaDAO dao=new QnaDAO();
		QnaDTO dto=new QnaDTO();
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setPwd(req.getParameter("pwd"));
		dto.setUserId(info.getUserId());
		
		dao.insertQna(dto, "created");
		resp.sendRedirect(cp+"/qna/qna.do");
		
	
	}

	
	
}
