package com.special;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

	private String pathname; // 서버에 저장될 경로

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		// 웹서버 환경에 저장
		// 이미지를 저장할 경로
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
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리스트
		String cp = req.getContextPath();
		MyUtil util = new MyUtil();
		SpecialDAO dao = new SpecialDAO();

		int current_page = 1;
		String page = req.getParameter("page");
		if (page != null) {
			current_page = Integer.parseInt(page);
		}

		// 전체 데이터 개수, 전체 페이지 수
		int rows = 10;
		int dataCount, total_page;
		dataCount = dao.dataCount();
		total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page) {
			current_page = total_page;
		}

		// 가져올 페이지의 시작과 끝
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;

		// 게시물 가져오기
		List<SpecialDTO> list;
		list = dao.listSpecial(start, end);

		Date date = new Date(); // 현재
		long gap;
		// 게시물 글번호 만들기
		int listNum = 0;
		int n = 0;

		Iterator<SpecialDTO> iter = list.iterator();
		while (iter.hasNext()) {
			SpecialDTO dto = iter.next();
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date endDate = sdf.parse(dto.getSpecialEnd()); // 축제마지막일자

				// 현재 - 종료일 > 1 축제 종료
				gap = (date.getTime() - endDate.getTime()) / (60 * 60 * 1000);
				dto.setGap(gap);
			

			} catch (Exception e) {

			}
			
			dto.setSpecialDate(dto.getSpecialDate().substring(0, 10));
			dto.setSpecialStart(dto.getSpecialStart().substring(0, 10));
			dto.setSpecialEnd(dto.getSpecialEnd().substring(0, 10));
			
			n++;
		}
		
		String listUrl;
		String articleUrl;
		
		listUrl = cp + "/special/s_calendar.do";
		articleUrl = cp + "/special/s_calendar.do?page=" + current_page;
		
		String paging = util.paging(current_page, total_page, listUrl);
		
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("page", page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		
		
		forward(req, resp, "/WEB-INF/views/special/s_calendar.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/special/s_created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 글 저장

		String cp = req.getContextPath();
		String encType = "UTF-8";
		int maxFileSize = 10 * 1024 * 1024;

		// 세션받아옴
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SpecialDAO dao = new SpecialDAO();
		SpecialDTO dto = new SpecialDTO();
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxFileSize, encType,
				new DefaultFileRenamePolicy());
		// request, 서버에 저장될 경로, 최대크기, 파라미터타입, 동일파일명보호

		// admin만 글을 등록할 수 있도록 함
		if (!info.getUserRole().equals("admin") ) {
			resp.sendRedirect(cp + "/special/s_calendar.do");
		} 

		if (mreq.getFile("upload") != null) {
			dto.setUserId(info.getUserId());

			String saveFileName = mreq.getFilesystemName("upload");
			saveFileName = FileManager.doFilerename(pathname, saveFileName);
			dto.setImageFileName(saveFileName);
			dto.setImageFileSize(mreq.getFile("upload").length());

			dto.setSpecialSubject(mreq.getParameter("subject"));
			dto.setSpecialContent(mreq.getParameter("content"));
			dto.setSpecialStart(mreq.getParameter("specialStart"));
			dto.setSpecialEnd(mreq.getParameter("specialEnd"));

			// 파라미터 넘김
			dao.insertSpecial(dto);
		}

		// 리스트로 리다이렉트
		resp.sendRedirect(cp + "/special/s_calendar.do");

	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// String cp = req.getContextPath();

	}
}