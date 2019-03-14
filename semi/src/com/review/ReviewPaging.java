package com.review;

public class ReviewPaging {
	/* -------------------------------------------------------------
		페이지 처리 
		: 기준이 되는 앞 뒤로 5개씩 보여주는 페이지를 처리한다.
		: 페이지의 모든 버튼은 HTML5의 button형을 사용한다.
		: 마우스 오버가 되면 색깔이 바뀐다. 현재 페이지는 다른 색상으로 표현한다.
		: 형식은 현재페이지가 8이라면, [<<First |<Prev | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | Next>| Last>>
		: [prev]는 표시된 페이지의 가장 작은 숫자의 이전 수를 기준으로, [next]는 표시된 페이지의 가장 높은 숫자의 다음 수를 기준으로 페이지를 표시한다. 
	  
	------------------------------------------------------------- */
	
	/**
	 * 페이지의 숫자를 확인하는 메소드
	 * @param numPerPage	페이지에 표시할 해당 속성의 개수
	 * @param dataCount		값이 존재하는 전체 속성의 개수
	 * @return				해당 속성을 표시할 게시판의 개수
	 */
	public int pageCount(int numPerPage, int dataCount) {
		int result = 0;
		
		if(dataCount<=0) {
			return result;
		}
		
		result = dataCount/numPerPage+(dataCount%numPerPage>0?1:0);
		
		return result;
	}
	
	
	public String paging(int current_page, int total_page, String list_url) {
		StringBuffer sb = new StringBuffer();
		
		int numPerBlock = 5; // 화면에 표시하는 페이지 개수
		int currentPageSetup; // 현재 설정할 페이지의 첫 변수 설정
		int n, page;		  // 기본 수 설정
		
		// 페이지가 없을 경우 빈 값 처리
		if(current_page<1||total_page<1) return "";
		
		// 해당 페이지에서 검색한 문자나, 페이지 처리된 것을 변경할 수 있도록 설정한다.
		if(list_url.indexOf("?")!=-1) {
			list_url+="&";
		} else {
			list_url+="?";
		}
		
		// currentPageSetup : 기준으로 잡을 첫 페이지
		// 30 ~ 34 | 35 | 36 ~ 40
		currentPageSetup = current_page-(numPerBlock+1)/2;
		
		// 기준 값이 0미만의 값은 그대로 가져간다.
		
		sb.append("<div class=\"sidebar\" style=\"float: left\">");
		sb.append("<div class=\"review_page_none\"> </div>");
		n=currentPageSetup;
		
		// 모든 페이지 숫자가 표시할 페이지 개수를 넘지 않으면 이전, 맨 처음을 표시하지 않는다.
		if(currentPageSetup>0 && total_page>(numPerBlock+1)/2) {
			sb.append("<div class='review_page_np'><a class='btn_noDeco' href='#' onclick=\"location.href='"+list_url+"page="+n+"'\"; return false;>▲</a></div>");
		} 
	
		page = currentPageSetup + 1;
		while(page<=(currentPageSetup+numPerBlock)) {
			if (page == current_page) {
				sb.append("<div class='review_page_on'><a class='btn_noDeco_on' href='#' onclick=\"location.href='"+list_url+"page="+page+"'\">"+page+"</a></div>");
			} else if (page<1) {
			} else if (page>total_page)  {
			} else {
				sb.append("<div class='review_page'><a class='btn_noDeco' href='#' onclick=\"location.href='"+list_url+"page="+page+"'\">"+page+"</a></div>");
			}
			page++;
		}
		
		n=currentPageSetup+numPerBlock+1; 	// 다음 페이지에서 가장 첫 페이지
		if(n>total_page) n=total_page;	 	// 만약 n이 total
		if(total_page-currentPageSetup>numPerBlock) {
			sb.append("<div class='review_page_np'><a class='btn_noDeco' href='#' onclick=\"location.href='"+list_url+"page="+n+"'\">▼</a></div>");
		} 
		sb.append("<div class=\"review_page_none\"> </div>");
		sb.append("</div>");
		
		return sb.toString();
	}	

}

