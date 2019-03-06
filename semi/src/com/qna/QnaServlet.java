package com.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		String uri=req.getRequestURI();  //http://localhost:8080/project/list.jsp 以묒뿉 /project/list.jsp瑜� 媛��졇�샂
		
		if(uri.indexOf("qna.do")!=-1) {
			qna(req, resp);
		}else if(uri.indexOf("created.do")!=-1) {
			createdForm(req,resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}
	}

	private void qna(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String cp=req.getContextPath();
		
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
		dto.setSubject(req.getParameter("content"));
		dto.setUserId(info.getUserId());
		
		dao.insertQna(dto, "created");
		resp.sendRedirect(cp+"/qna/qna.do");
		
	
	}

	
	
}
