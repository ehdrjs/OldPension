package com.member;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		}else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		}else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		}else if(uri.indexOf("member.do")!=-1) {
			signInForm(req, resp);
		}else if(uri.indexOf("member.do")!=-1) {
			signIn(req, resp);
		}
	}
	
	// �α���  ��
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path="/WEB-INF/views/member/login.jsp";
	
		forward(req, resp, path);
	}
	//�α��� ����
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		String userRole = req.getParameter("userRole");
		
		MemberDTO dto = dao.readMember(userId);
		if(dto != null) {
			if(userPwd.equals(dto.getUserPwd()) && userRole.equals(dto.getUserRole())) {
				session.setMaxInactiveInterval(60*60);
				
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setUserRole(dto.getUserRole());
				
				session.setAttribute("member", info);
				
				resp.sendRedirect(cp);
				return;
			}
			
			String msg="���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.";
			req.setAttribute("message", msg);
			
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
		}
	}
	//�α׾ƿ�
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		session.removeAttribute("member");
		session.invalidate();
		
		resp.sendRedirect(cp);
	}
	//ȸ������ ��
	private void signInForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/member.jsp";
		
		req.setAttribute("title", "ȸ������");
		req.setAttribute("mode", "created");
		
		forward(req, resp, path);
	}
	//ȸ������
	private void signIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		
		InetAddress local = InetAddress.getLocalHost();
		String ip = local.getHostAddress();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setUserPwd(req.getParameter("userPwd"));
		dto.setUserName(req.getParameter("userName"));
		dto.setTel(req.getParameter("tel"));
		dto.setEmail(req.getParameter("email"));
		dto.setIp(ip);
		dto.setUserRole(req.getParameter("userRole"));
		
		int result = dao.insertMember(dto);
		if(result == 0) {
			req.setAttribute("message", "ȸ�����Կ� �����߽��ϴ�.");
		}else {
			req.setAttribute("message", "ȸ�����Կ� �����߽��ϴ�.");
		}

		forward(req, resp, "/WEB-INF/member/login.do");
	}


}
