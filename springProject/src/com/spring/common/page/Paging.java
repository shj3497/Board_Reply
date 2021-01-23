package com.spring.common.page;

import com.spring.common.util.Util;
import com.spring.common.vo.CommonVO;

public class Paging {

	/*
	 * 페이징을 위한 설정 작업
	 * @param cvo
	 * */
	
	public static void setPage(CommonVO cvo) {
		
		int page = Util.nvl(cvo.getPage(), 1); // object형?
		int pageSize = Util.nvl(cvo.getPageSize(), 10);
		
		if(cvo.getPage()==null) {
			//cvo.setPage(page + "");
			cvo.setPage(String.valueOf(page));
		}
		if(cvo.getPageSize()==null) {
			cvo.setPageSize(String.valueOf(pageSize));
		}
		
		int start_row = (page-1)*pageSize + 1;		// page가 1이면 start_row는 1
		int end_row = (page-1)*pageSize + pageSize;	// page가 1이면 end_row는 pageSize, 2이면 2*pageSize
		
		cvo.setStart_row(String.valueOf(start_row));
		cvo.setEnd_row(String.valueOf(end_row));
	}
	
}
