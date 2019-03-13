package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;


@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	private String pathname;
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + File.separator + "uploaded" + File.separator + "notice";
		File f = new File(pathname);
		if(! f.exists()) {
			f.mkdirs();
		}
		
		if(uri.indexOf("list.do") != -1) {
			list(req, resp);
		}else if(uri.indexOf("created.do") != -1) {
			createForm(req, resp);
		}else if(uri.indexOf("created_ok.do") != -1) {
			createSubmit(req, resp);
		}else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		}else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);	
		}else if(uri.indexOf("download_ok.do") != -1) {
			download(req, resp);
		}else if(uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		NoticeDAO dao = new NoticeDAO();
		String cp = req.getContextPath();
		
		MyUtil util = new MyUtil();
		
		int dataCount = dao.dataCount();
		int rows = 2;
		int total_page = util.pageCount(rows, dataCount);
		int current_page = 1;
		if(req.getParameter("page") != null) {
			current_page = Integer.parseInt(req.getParameter("page"));
		}
		
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		String list_url = cp + "/notice/list.do";
		String paging = util.paging(current_page, total_page, list_url);
		
		int start = rows * (current_page - 1) + 1;
		int end = current_page * rows;
		
		list = dao.readNotice(start, end);
		
		req.setAttribute("paging", paging);
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("dataCount", dataCount);

		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
	}
	protected void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		
		req.setAttribute("page", page);
		req.setAttribute("mode", "created");
		
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
	}
	
	protected void createSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = new NoticeDTO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		if(info == null) {
			resp.sendRedirect("/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String encType = "utf-8";
		int maxFilesize = 10*1024*1024;
		
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxFilesize, encType, new DefaultFileRenamePolicy());
		
		dto.setUserId(info.getUserId());
		
		String content = mreq.getParameter("content");
		//content = util.htmlSymbols(content);
		dto.setNoticeContent(content);
		dto.setNoticeCount(0); 
		dto.setNoticeSubject(mreq.getParameter("subject"));
		if(mreq.getFile("upload") != null) {
			dto.setOriginalFileName(mreq.getOriginalFileName("upload")); 
			dto.setFileSize(mreq.getFile("upload").length()); 
			dto.setSaveFileName(mreq.getFilesystemName("upload"));
		}
		
		dao.insertNotice(dto);  

		resp.sendRedirect(cp + "/notice/list.do?page="+page);
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String page = req.getParameter("page");
		int listNum = Integer.parseInt(req.getParameter("listNum")); 
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = dao.readNotice(listNum);
		MyUtil util = new MyUtil();
		
		dto.setNoticeContent(util.htmlSymbols(dto.getNoticeContent()));
		req.setAttribute("page", page);
		req.setAttribute("dto", dto);
		req.setAttribute("listNum", listNum);
		String image =  ".+\\.(jpg|bmp|gif|jpeg|png)$";
		boolean isImage = false;
		
		//정규식 비교
		if(dto.getOriginalFileName() != null ) {
			Pattern p = Pattern.compile(image);
			Matcher m = p.matcher(dto.getOriginalFileName());
			isImage = m.matches();
			
		}
		
		//이미지 이냐?
		if(isImage) {
			req.setAttribute("image", dto.getOriginalFileName());
		}
		
		forward(req, resp, "/WEB-INF/views/notice/article.jsp");
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String page = req.getParameter("page");
		int listNum = Integer.parseInt(req.getParameter("listNum")); 
		NoticeDTO dto = dao.readNotice(listNum);
		req.setAttribute("mode", "update");
		req.setAttribute("dto", dto);
		req.setAttribute("listNum", listNum);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		NoticeDAO dao = new NoticeDAO();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		int listNum = Integer.parseInt(req.getParameter("listNum"));
		String page = req.getParameter("page");
		NoticeDTO dto = dao.readNotice(listNum);
		
		if(!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/notice/list.do?page=" + page );
			return;
		}
		
		dto = new NoticeDTO();
		
		String encType = "utf-8";
		int maxFileSize = 10*1024*1024;
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxFileSize, encType, new DefaultFileRenamePolicy());

		String subject = mreq.getParameter("subject");
		String content = mreq.getParameter("content");
		dto.setNoticeSubject(subject);
		dto.setNoticeContent(content);
		dto.setNoticeNum(listNum);
		if(mreq.getFile("upload") != null) {
			dto.setOriginalFileName(mreq.getOriginalFileName("upload")); 
			dto.setFileSize(mreq.getFile("upload").length()); 
			dto.setSaveFileName(mreq.getFilesystemName("upload"));
			
			dao.updateNotice(dto);
		}else if(mreq.getFile("upload") == null) {
			dao.updateNoticeBasic(dto);
		}
		
		resp.sendRedirect(cp + "/notice/article.do?page=" + page+"&listNum=" + listNum);
	}
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int listNum = Integer.parseInt(req.getParameter("listNum"));
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao = new NoticeDAO();
		NoticeDTO dto = dao.readNotice(listNum);
		
		
		//쓴사람이 아니면
		if(!info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp + "/notice/article.do?listNum="+listNum);
			return;
		}
		
		if(dto.getOriginalFileName() != null) {
			FileManager.doFiledelete(pathname, dto.getSaveFileName());
		}

		dao.deleteNotice(listNum);
		
		resp.sendRedirect(cp + "/notice/list.do?page=" + page );
	}
	//파일 삭제 했을 때
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
		
		int listNum=Integer.parseInt(req.getParameter("listNum"));
		String page=req.getParameter("page");
		
		NoticeDTO dto=dao.readNotice(listNum);
		
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do?page="+page);
			return;
		}
		
		if(info==null || ! info.getUserId().equals(dto.getUserId())) {
			resp.sendRedirect(cp+"/notice/list.do?page="+page);
			return;
		}
		
		FileManager.doFiledelete(pathname, dto.getSaveFileName());
		dto.setOriginalFileName("");
		dto.setSaveFileName("");
		dto.setFileSize(0);
		dao.deleteFile(dto);
		
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");
		req.setAttribute("listNum", listNum);
		req.setAttribute("dto", dto);
		
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
		
	}
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao=new NoticeDAO();
		String cp=req.getContextPath();
		
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		int listNum= Integer.parseInt(req.getParameter("listNum"));
		String page=req.getParameter("page");
		
		NoticeDTO dto=dao.readNotice(listNum);
		if(dto==null) {
			resp.sendRedirect(cp+"/notice/list.do"+page);
			return;
		}
		
		boolean b = FileManager.doFiledownload(dto.getSaveFileName(), dto.getOriginalFileName(), pathname, resp);
		
		if(!b) {
			resp.setContentType("text/html);charset=utf-8");
			PrintWriter pw = resp.getWriter();
			pw.print("<script>alert('파일다운로드실패')</script>");
	    	return;
		}
	}
	
	
	
}
