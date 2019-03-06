package com.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		
		String uri=req.getRequestURI();  //http://localhost:8080/project/list.jsp 중에 /project/list.jsp를 가져옴
		
		if(uri.indexOf("qna.do")!=-1) {
		qna(req, resp);
		}
	}

	private void qna(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String cp=req.getContextPath();
		
		forward(req, resp, "/WEB-INF/views/qna/qna.jsp");

	}



	
	
}
