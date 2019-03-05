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
		}else if(uri.indexOf("member_ok.do")!=-1) {
			signIn(req, resp);
		}
	}
	
	// 로그인  폼
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path="/WEB-INF/views/member/login.jsp";
	
		forward(req, resp, path);
	}
	//로그인 실행
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		
		String userId = req.getParameter("userId");
		String userPwd = req.getParameter("userPwd");
		
		MemberDTO dto = dao.readMember(userId);
		if(dto != null) {
			if(userPwd.equals(dto.getUserPwd())) {
				session.setMaxInactiveInterval(60*60);
				
				SessionInfo info = new SessionInfo();
				info.setUserId(dto.getUserId());
				info.setUserName(dto.getUserName());
				info.setUserRole(dto.getUserRole());
				
				session.setAttribute("member", info);
				
				resp.sendRedirect(cp);
				return;
			}
		}
			
			String msg="아이디 또는 패스워드가 일치하지 않습니다.";
			req.setAttribute("message", msg);
			
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
		
	}
	//로그아웃
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		session.removeAttribute("member");
		session.invalidate();
		
		resp.sendRedirect(cp);
	}
	//회원가입 폼
	private void signInForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/member.jsp";
		
		req.setAttribute("title", "회원가입");
		req.setAttribute("mode", "created");
		
		forward(req, resp, path);
	}
	//회원가입
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
			req.setAttribute("message", "회원가입에 실패했습니다.");
		}else {
			req.setAttribute("message", "회원가입에 성공했습니다.");
		}

		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}


}
