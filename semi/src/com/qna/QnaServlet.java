package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
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
		}else if(uri.indexOf("article.do")!=-1) {
			article(req,resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateForm(req,resp);
		}else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req,resp);
		}
	}

	protected void qna(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
		
		int listNum, n=0;
		Iterator<QnaDTO> it = list.iterator();
		while(it.hasNext()) {
			QnaDTO	dto = it.next();
			listNum = dataCount - (start + n -1);
			dto.setListNum(listNum);
			n++;
		}
		
		String query ="";
		if(searchValue.length() !=0) {
			query ="searchKey=" + searchKey + "&searchValue="+ URLEncoder.encode(searchValue, "utf-8"); 
		}
		
		String listUrl = cp + "/qna/qna.do";
		String articleUrl= cp+ "/qna/article.do?page=" + current_page;
		if(query.length()!=0) {
			listUrl += "?" + query;
			articleUrl += "&" + query;
		}
		String paging = util.paging(current_page, total_page, listUrl);
		req.setAttribute("list", list);
		req.setAttribute("datacount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		
		forward(req, resp, "/WEB-INF/views/qna/qna.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");

	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
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
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		QnaDAO dao = new QnaDAO();
		String cp = req.getContextPath();
		
		int num= Integer.parseInt(req.getParameter("qnaNum"));
		String page=req.getParameter("page");
		
		String searchKey=req.getParameter("qnaSearchKey");
		String searchValue=req.getParameter("qnaSearchValue");
		if(searchKey==null) {
			searchKey="qnaSubject";
			searchValue="";
		}
		searchValue=URLDecoder.decode(searchValue, "utf-8");
		
		String query="page="+page;
		if(searchValue.length()!=0) {
			query+="&qnaSearchKey="+searchKey+"&qnaSearchValue="+URLEncoder.encode(searchValue,"utf-8");
		}
		
		dao.updateQnaCount(num);
		QnaDTO dto = dao.readQna(num);
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/qna.do?"+query);
			return;
		}
		
		MyUtil util= new MyUtil();
		dto.setContent(util.htmlSymbols(dto.getContent()));
		
		QnaDTO	preReadDto=dao.preReadQna(dto.getGroupNum(), dto.getOrderNo(), searchKey, searchValue);
		QnaDTO	nextReadDto=dao.nextReadQna(dto.getGroupNum(), dto.getOrderNo(), searchKey, searchValue);
		
		req.setAttribute("dto", dto);
		req.setAttribute("preReadDto", preReadDto);
		req.setAttribute("nextReadDto", nextReadDto);
		req.setAttribute("query", query);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/qna/article.jsp");
	
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAO();
		
		String page=req.getParameter("page");
		String searchKey=req.getParameter("qnaSearchKey");
		String searchValue=req.getParameter("qnaSearchValue");
		if(searchKey==null) {
			searchKey="subject";
			searchValue="";
		}
		
		searchValue=URLDecoder.decode(searchValue,"utf-8");
		String query="page="+page;
		if(searchValue.length()!=0) {
			query+="&qnaSearchKey="+searchKey+"&qnasearchValue="+URLEncoder.encode(searchValue,"utf-8");
		}
		
		int num=Integer.parseInt(req.getParameter("qnaNum"));
		QnaDTO dto=dao.readQna(num);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/qna/qna.do?"+query);
			return;
		}
		
		if(! dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp+"/qna/qna.do?"+query);
			return;
		}
		
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("qnaSearchKey", searchKey);
		req.setAttribute("qnaSearchValue", searchValue);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session=req.getSession();
		SessionInfo info= (SessionInfo)session.getAttribute("member");
		
		String cp=req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET"))	{
			resp.sendRedirect(cp+"/qna/qna.do");
			return;
		}
		
		QnaDAO dao=new QnaDAO();
		
		String page=req.getParameter("page");
		String searchKey=req.getParameter("qnaSearchKey");
		String searchValue=req.getParameter("qnaSearchValue");
		String query="page="+page;
		if(searchValue.length()!=0) {
			query+="&qnaSearchKey="+searchKey+"&qnaSearchValue="+URLEncoder.encode(searchValue, "utf-8");
		}
		
		QnaDTO dto= new QnaDTO();
		dto.setNum(Integer.parseInt(req.getParameter("qnaNum")));
		dto.setSubject(req.getParameter("qnaSubject"));
		dto.setContent(req.getParameter("qnaContent"));
		
		dao.updateQna(dto, info.getUserId());
		resp.sendRedirect(cp+"/qna/qna.do?"+query);
		
		
	}
	
}
