package com.special;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

@WebServlet("/special/*")
public class SpecialServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	private String pathname;

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "photo";
		File f = new File(pathname);
		if (!f.exists()) {
			f.mkdirs();
		}

		if (uri.indexOf("s_calendar.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("s_created.do") != -1) {
			createdForm(req, resp);
		} else if (uri.indexOf("s_created_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("s_article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("s_update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("s_update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("s_delete.do") != -1) {
			delete(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cp = req.getContextPath();
		MyUtil util = new MyUtil();
		SpecialDAO dao = new SpecialDAO();

		int current_page = 1;
		String page = req.getParameter("page");
		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		int rows = 10;
		int dataCount, total_page;
		dataCount = dao.dataCount();
		total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}

		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;

		List<SpecialDTO> list;
		list = dao.listSpecial(start, end);

		int listNum = 0;
		int n = 0;

		Date date = new Date();
		Iterator<SpecialDTO> iter = list.iterator();
		while (iter.hasNext()) {
			SpecialDTO dto = iter.next();
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);

			try {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = sdf.parse(dto.getSpecialStart());
				Date endDate = sdf.parse(dto.getSpecialEnd());
				Calendar cal = Calendar.getInstance();
				cal.setTime(endDate);
				cal.add(Calendar.DATE, +1);
				cal.add(Calendar.SECOND, -1);
				endDate = cal.getTime();

				if (date.compareTo(endDate) < 0) { // 현재 날짜 마지막날짜 순일 때
					int gap = startDate.compareTo(date) < 0 ? 1 : 2; // 시작날짜 현재날짜 순이면 1 아니면 0값을 넣는다
					dto.setGap(gap);
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}

			dto.setSpecialDate(dto.getSpecialDate().substring(0, 10));
			dto.setSpecialStart(dto.getSpecialStart().substring(0, 10));
			dto.setSpecialEnd(dto.getSpecialEnd().substring(0, 10));

			n++;
		}

		String listUrl;
		String articleUrl;

		listUrl = cp + "/special/s_calendar.do";
		articleUrl = cp + "/special/s_article.do?page=" + current_page;

		String paging = util.paging(current_page, total_page, listUrl);

		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);

		forward(req, resp, "/WEB-INF/views/special/s_calendar.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/special/s_created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cp = req.getContextPath();
		String encType = "UTF-8";
		int maxFileSize = 10 * 1024 * 1024;

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SpecialDAO dao = new SpecialDAO();
		SpecialDTO dto = new SpecialDTO();
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxFileSize, encType,
				new DefaultFileRenamePolicy());

		if (info == null || !info.getUserRole().equals("admin")) {
			resp.sendRedirect(cp + "/special/s_calendar.do");
			return;
		}

		if (mreq.getFile("upload") != null) {
			dto.setUserId(info.getUserId());

			dto.setSpecialSubject(mreq.getParameter("subject"));
			dto.setSpecialContent(mreq.getParameter("content"));
			dto.setSpecialStart(mreq.getParameter("specialStart"));
			dto.setSpecialEnd(mreq.getParameter("specialEnd"));

			String saveFileName = mreq.getFilesystemName("upload");
			saveFileName = FileManager.doFilerename(pathname, saveFileName);
			dto.setImageFileName(saveFileName);
			dto.setImageFileSize(mreq.getFile("upload").length());

			dao.insertSpecial(dto);

		}

		resp.sendRedirect(cp + "/special/s_calendar.do");

	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		SpecialDAO dao = new SpecialDAO();

		int specialNum = Integer.parseInt(req.getParameter("specialNum"));
		String page = req.getParameter("page");

		dao.hitCountUpdate(specialNum);

		SpecialDTO dto = dao.readSpecial(specialNum);
		if (dto == null) {
			resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);
			return;
		}

		dto.setSpecialContent(dto.getSpecialContent().replaceAll("\n", "<br>"));

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		forward(req, resp, "/WEB-INF/views/special/s_article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SpecialDAO dao = new SpecialDAO();

		String page = req.getParameter("page");
		int specialNum = Integer.parseInt(req.getParameter("specialNum"));
		SpecialDTO dto = dao.readSpecial(specialNum);

		if (dto == null) {
			resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);
			return;
		}

		if (info == null || !info.getUserRole().equals("admin")) {
			resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);

		req.setAttribute("mode", "update");
		forward(req, resp, "/WEB-INF/views/special/s_created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cp = req.getContextPath();
		SpecialDAO dao = new SpecialDAO();
		SpecialDTO dto = new SpecialDTO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String encType = "UTF-8";
		int maxSize = 5 * 1024 * 1024;
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType, new DefaultFileRenamePolicy());

		String page = mreq.getParameter("page");
		if (info == null || !info.getUserRole().equals("admin")) {
			resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);
		}

		dto.setSpecialNum(Integer.parseInt(mreq.getParameter("specialNum")));
		dto.setSpecialSubject(mreq.getParameter("subject"));
		dto.setSpecialContent(mreq.getParameter("content"));
		dto.setSpecialStart(mreq.getParameter("specialStart"));
		dto.setSpecialEnd(mreq.getParameter("specialEnd"));
		// dto.setImageFileName(mreq.getParameter("imageFileName"));

		if (mreq.getFile("upload") != null) {
			FileManager.doFiledelete(pathname, dto.getImageFileName());

			String saveFileName = mreq.getFilesystemName("upload");

			saveFileName = FileManager.doFilerename(pathname, saveFileName);

			dto.setImageFileName(saveFileName);
		}

		dao.updateSpecial(dto);

		resp.sendRedirect(cp + "/special/s_article.do?page=" + page + "&specialNum=" + dto.getSpecialNum());

	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		SpecialDAO dao = new SpecialDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null || !info.getUserRole().equals("admin")) {
			resp.sendRedirect(cp + "/special/s_calendar.do");
			return;
		}

		int specialNum = Integer.parseInt(req.getParameter("specialNum"));
		String page = req.getParameter("page");

		SpecialDTO dto = dao.readSpecial(specialNum);
		if (dto == null) {
			resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);
			return;
		}

		FileManager.doFiledelete(pathname, dto.getImageFileName());

		dao.deleteSpecial(specialNum);

		resp.sendRedirect(cp + "/special/s_calendar.do?page=" + page);

	}

}