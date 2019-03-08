package com.special;

import java.io.File;
import java.io.IOException;

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

@WebServlet("/special/*")
public class SpecialServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	private String pathname; // ������ ����� ���

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		HttpSession session = req.getSession();

		// ������ ȯ�濡 ����
		// �̹����� ������ ���
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
		// ����Ʈ
		forward(req, resp, "/WEB-INF/views/special/s_calendar.jsp");
	}	
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �۾��� ��
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/special/s_created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// �� ����

		String cp = req.getContextPath();
		String encType = "UTF-8";
		int maxFileSize = 10 * 1024 * 1024;

		// ���ǹ޾ƿ�
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		SpecialDAO dao = new SpecialDAO();
		SpecialDTO dto = new SpecialDTO();
		MultipartRequest mreq = new MultipartRequest(req, pathname, maxFileSize, encType,
				new DefaultFileRenamePolicy());
		// request, ������ ����� ���, �ִ�ũ��, �Ķ����Ÿ��, �������ϸ�ȣ

		// admin�� ���� ����� �� �ֵ��� ��
		if (!info.getUserRole().equals("admin")) {
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

			// �Ķ���� �ѱ�
			dao.insertSpecial(dto); 
		}

		// ����Ʈ�� �����̷�Ʈ
		resp.sendRedirect(cp + "/special/s_calendar.do");

	}
	
	
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// String cp = req.getContextPath();

	}
}