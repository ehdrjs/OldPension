package com.notice;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

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
			deleteFile(req, resp);
		}else if(uri.indexOf("download_ok.do") != -1) {
			download(req, resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDTO dto = new NoticeDTO();
		NoticeDAO dao = new NoticeDAO();
		
		
		
		
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
	}
	protected void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void createSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
}
