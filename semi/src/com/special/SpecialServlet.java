package com.special;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/special/*")
public class SpecialServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	/*@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path)
			throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
*/
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		if (uri.indexOf("s_calendar.do") != -1) {
			calendar(req, resp);
		} else if (uri.indexOf("") != -1) {
			
		}
	}

	protected void calendar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 날짜

		// String cp = req.getContextPath();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		// 클라이언트로부터 넘어온 연도와 월이 존재
		// 존재하지 않으면 시스템 연도와 월
		String sy = req.getParameter("year");
		String sm = req.getParameter("month");

		if (sy != null) {
			year = Integer.parseInt(sy);
		}
		if (sm != null) {
			month = Integer.parseInt(sm);
		}

		// 1일로 설정
		cal.set(year, month, 1);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1; // month에 +1
		// 요일...
		int week = cal.get(Calendar.DAY_OF_WEEK);
		int e = cal.getActualMaximum(Calendar.DATE); // 해당월의 마지막 날짜
		
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("week", week);
		req.setAttribute("e", e);
		
		forward(req, resp, "/WEB-INF/views/special/s_calendar.jsp");
	}
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/special/s_created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		
		String cp = req.getContextPath();
		
		resp.sendRedirect(cp+"/special/s_calendar.do");
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		
		
	}
}