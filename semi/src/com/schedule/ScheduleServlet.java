package com.schedule;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/schedule/*")
public class ScheduleServlet extends MyServlet {

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

		if (uri.indexOf("calendar.do") != -1) {
			calendar(req, resp);
		} else if (uri.indexOf("calendar_ok.do") != -1) {
			disableCalendar(req, resp);
		}
	}

	protected void calendar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��¥

		// String cp = req.getContextPath();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);

		// Ŭ���̾�Ʈ�κ��� �Ѿ�� ������ ���� ����
		// �������� ������ �ý��� ������ ��
		String sy = req.getParameter("year");
		String sm = req.getParameter("month");

		if (sy != null) {
			year = Integer.parseInt(sy);
		}
		if (sm != null) {
			month = Integer.parseInt(sm);
		}

		// 1�Ϸ� ����
		cal.set(year, month, 1);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1; // month�� +1
		// ����...
		int week = cal.get(Calendar.DAY_OF_WEEK);
		int e = cal.getActualMaximum(Calendar.DATE); // �ش���� ������ ��¥
		
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("week", week);
		req.setAttribute("e", e);
		
		forward(req, resp, "/WEB-INF/views/schedule/calendar.jsp");
	}

	protected void disableCalendar(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ��¥ ��Ȱ��ȭ
	}
}
