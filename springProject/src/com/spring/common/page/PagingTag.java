package com.spring.common.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PagingTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	/**
	* @param page 현제 페이지 번호
	* @param total 전체 조회된 Row 수
	* @param list_size 페이지에 보여주는 레코드수
	* @param page_size 페이지 네비게이터에 표시되는 페이지 버튼의 갯수
	*/
	
	private int page = 1;		// 현재 페이지번호
	private int total = 1;		// 전제 조회된 행수
	private int list_size = 10;	// 한 페이지에 보여주는 행의 수
	private int page_size = 10;	// 한번에 보여줄 버튼으로 만들 1~10 페이지의 수
	
	@Override
	public int doStartTag() throws JspException{
		try {
			System.out.println("(PagingTag) doStartTag() 진입");
			pageContext.getOut().println(getPaging());
		}catch(IOException e) {
			System.out.println("e >>> : " + e);
		}
		return super.doStartTag();
	}
	
	public String getPaging() {
		
		String ret = "";
		if(page <1 ) {
			page = 1;
		}
		if(total <1 ) {
			return "";
		}
		/* page가 1페이이고 page_size가 2이면 */
		/* currentFirst 는 1 */
		int currentFirst = ((page-1)/page_size) * page_size + 1; // >> page
		
		/* currentlast >>> 한페이지당 보여줄 버튼(page_size)가 조정됨 */
		int currentlast = ((page-1)/page_size) * page_size + page_size; // >> 1이면 10, 2이면 11? >> 20이 나와야할텐데
		
		/* nextFirst는 3 */
		int nextFirst = (((page-1)/page_size) + 1) * page_size + 1; // 11(다음 페이지 번호 숫자?) 1이면 11, 2이면
		
		/* prevLast >>> (다음) 버튼을 누르면 10페이지가 넘어가서 (page_size)가 조정됨 */
		int prevLast = (((page-1)/page_size)-1) * page_size + 1; // 
		
		// lastPage >>> 데이터의 총개수(행의개수)/list_size로 해서 마지막 페이지의 숫자를 지정(반올림해줘야할듯)
		int lastPage = 1;
		lastPage = total/list_size;
		/*
		 * lastPage(총 페이지수)는 total이 11이고, list_size가 10이면 1을 가진다.
		 * 그래서 총 페이지 수가 나눙 ㅓ떨어지지 않으면 나머지 레코드를 출력할 페이지가 필요하다는 의미
		 * */
		
		if(total % list_size!=0) {
			lastPage=lastPage+1; // 총페이지에서 list_size로 나눠준 나머지가 0이 아니면 lastPage를 +1 한다.
		}
		/* currentlast가 lastPage(총페이지수) 보다 크면 마지막 페이지로 수정 */
		currentlast = (currentlast > lastPage)?lastPage:currentlast;
		
		ret += "<div class='paginate'>";
		
		// page가 1이상이면 <a href>태그로 경로를 활성화 시켜주고, 1이상이 아니면(1페이지이면) 모양만 나오게 <img>태그로만 설정해줌
		if(page > 1) {
			ret += "<a href=\"javascript:goPage('1')\"><span><img src='../images/common/btn_paginate_first.gif' alt='처음' /></span></a>";
			System.out.println("(PagingTag) ret >>> : " + ret);
		}else {
			ret += "<span><img src='../images/common/btn_paginate_first.gif' alt='처음' /></span>"; 
		}
		
		// 10페이지 이상이면 goPage(숫자)로 마지막 페이지로 이동하는 버튼 활성화
		if(prevLast >0) {
			ret += "<a href\"javascript:goPage('"+prevLast+"');\"><span><img src='../images/common/btn_paginate_prev.gif' alt='이전'/></span></a>";
			System.out.println("(PagingTag)d ret >>> : " + ret);
		}else {
			ret += "<span><img src='../images/common/btn_paginate_prev.gif' alt='이전'/></span>";
		}
		
		for(int j=currentFirst; j<currentFirst + page_size && j<=lastPage; j++) {
			if(j<=currentlast) {
				if(j==page) { // 그러니까 currentFirst 는 무조건 page와 같을텐데..
					ret += "<a href='#' class='on textAn'>"+j+"</a>"; // 그러니까 이게 항상 참이니까 현재페이지의 기능은 비활성화
				}else {
					ret += "<a href=\"javascript:goPage('"+j+"');\" class='textAn'>"+j+"</a>"; // 다른 페이지의 버튼을 활성화 시켜줌
				}
			}
		}
		
		if(nextFirst <= lastPage) {
			ret += "<a href=\"javascript:goPage('"+nextFirst+"')\"><span><img src = '../images/common/btn_paginate_next.gif' alt='다음' /></span></a>";
		}else {
			ret += "<span><img src = '../images/common/btn_paginate_next.gif' alt='다음' /></span>";
			
		}
		
		if ( page<lastPage ) {
			 ret += "<a href=\"javascript:goPage('"+lastPage+"')\"><span><img src='../images/common/btn_paginate_last.gif' alt='마지막' /></span></a> ";
		}else{
			 ret += "<span><img src='../images/common/btn_paginate_last.gif' alt='마지막' /></span> ";
		}
		
		ret += " </div> ";
		
		
		return ret;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setList_size(int list_size) {
		this.list_size = list_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}
	
}
