package com.room;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/room/*")
public class RoomServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		
		if(uri.indexOf("room.do") != -1) {
			forward(req, resp, "/WEB-INF/views/room/room.jsp");
		} else if (uri.indexOf("list.do") != -1) {
			room(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			create(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			createdSubmit(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			update(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
		
		// 세션설정
		// userId , userName,
		/*
		HttpSession session = req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			
		}
		*/
	}
	
	protected void room(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao = new RoomDAO();
		List<RoomDTO> roomList;
		List<RoomDTO> priceList;
		int num;
		
		// 방 보기
		if( req.getParameter("roomNum")!=null ) {
			num = Integer.parseInt(req.getParameter("roomNum"));
		} else
			num = 1;
		 
		priceList = dao.viewPrice(num);
		RoomDTO dto = dao.viewRoom(num);
		dto.setRoomNum(num);
		
		// 룸 리스트
		int roomCount = dao.listRoom().size();
		roomList = dao.listRoom();
		
		req.setAttribute("priceList", priceList);
		req.setAttribute("roomDTO", dto);
		req.setAttribute("roomList", roomList);
		req.setAttribute("roomCount", roomCount);
		
		forward(req, resp, "/WEB-INF/views/room/list.jsp");
	}
	
	protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao = new RoomDAO();
		List<RoomDTO> roomList;
		
		// 룸 리스트
		roomList = dao.listRoom();
		req.setAttribute("roomList", roomList);
		req.setAttribute("mode", "create");
		forward(req, resp, "/WEB-INF/views/room/write.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao=new RoomDAO();
		String cp=req.getContextPath();
		
		RoomDTO dto=new RoomDTO();
	    dto.setRoomNum((dao.listRoom().size())+1);
	    dto.setRoomName(req.getParameter("roomName"));
	    dto.setRoomMin(Integer.parseInt(req.getParameter("roomMin")));
	    dto.setRoomMax(Integer.parseInt(req.getParameter("roomMax")));
	    dto.setIsRoomOK(req.getParameter("isRoomOK"));
	    dto.setRoomContent((req.getParameter("roomContent")).replaceAll("\n", "<br>"));
	    
	    dao.insertRoom(dto);
	    
	    int num = dto.getRoomNum();
	    int first = Integer.parseInt(req.getParameter("price_mid"));
	    int second = Integer.parseInt(req.getParameter("price_end"));
	    int third = Integer.parseInt(req.getParameter("price_high"));
	    
	    dao.insertPrice(num, 1, first);
	    dao.insertPrice(num, 2, second);
	    dao.insertPrice(num, 3, third);
	    
	    resp.sendRedirect(cp+"/room/list.do");
	}
	
	protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao = new RoomDAO();
		RoomDTO dto = new RoomDTO();
		List<RoomDTO> roomList;
		List<RoomDTO> priceList;
		int num=Integer.parseInt(req.getParameter("roomNum"));
		
		dto.setRoomNum(num);
		dto = dao.viewRoom(num);
		priceList = dao.viewPrice(num);
		roomList = dao.listRoom();
		dto.setRoomContent(dto.getRoomContent().replaceAll("<br>", "\n"));
		
		req.setAttribute("mode", "update");
		req.setAttribute("roomList", roomList);
		req.setAttribute("priceList", priceList);
		req.setAttribute("roomDTO", dto);
		forward(req, resp, "/WEB-INF/views/room/write.jsp");
	}
	
	
	
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao=new RoomDAO();
		String cp=req.getContextPath();
		
		RoomDTO dto=new RoomDTO();
	    dto.setRoomNum(Integer.parseInt(req.getParameter("roomNum")));
	    dto.setRoomName(req.getParameter("roomName"));
	    dto.setRoomMin(Integer.parseInt(req.getParameter("roomMin")));
	    dto.setRoomMax(Integer.parseInt(req.getParameter("roomMax")));
	    dto.setIsRoomOK(req.getParameter("isRoomOK"));
	    dto.setRoomContent((req.getParameter("roomContent")).replaceAll("\n", "<br>"));
	    
	    dao.updateRoom(dto);
	    
	    int num = dto.getRoomNum();
	    String price[] = req.getParameterValues("price");
	    for ( int i=0 ; i<price.length; i++) {
	    	int price_num =Integer.parseInt(price[i]);
	    	dao.updatePrice(num, i+1, price_num);
	    }
	    
	    String query = "?roomNum="+num;
	    
	    resp.sendRedirect(cp+"/room/list.do"+query);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RoomDAO dao=new RoomDAO();
		String cp=req.getContextPath();
		
	    int num=Integer.parseInt(req.getParameter("roomNum"));
	    
	    dao.deletePrice(num);
	    dao.deleteRoom(num);
	    
	    String query = "?roomNum="+1;
	    
	    resp.sendRedirect(cp+"/room/list.do"+query);
	}
	
}
