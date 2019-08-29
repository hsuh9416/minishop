package salesInfo.bean;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 게시판 페이징 관련 클래스
 */
@Component
@Data
public class SalesInfoPaging {
	private int currentPage;
	private int pageBlock;
	private int pageSize;
	private int totalA;
	private StringBuffer pagingHTML;
	
	public void makePagingHTML(){
		
		int totalP = (totalA+pageSize-1)/pageSize;
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		int endPage = startPage+pageBlock-1;

			pagingHTML = new StringBuffer();
			
		if(endPage > totalP) endPage = totalP;		
			
		if(startPage>pageBlock)			
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='salesInfoPaging("+(startPage-1)+")'>이전</a></li>");     
		
		for(int i=startPage; i<=endPage; i++){
			if(i==currentPage)
				pagingHTML.append("<li class='page-item'><a class='page-link' id='currentPaging' href='#' onclick='salesInfoPaging("+i+")'>"+i+"</a></li>");
			else
				pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='salesInfoPaging("+i+")'>"+i+"</a></li>");
		
		}
		
		if(endPage < totalP)
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='salesInfoPaging("+(endPage+1)+")'>다음</a></li>");
	}
	
	public void makeSearchPagingHTML(){

		int totalP = (totalA+pageSize-1)/pageSize;
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		
			pagingHTML = new StringBuffer();
		
		if(endPage > totalP) endPage = totalP;
			
		if(startPage>pageBlock)			
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='salesInfoSearchPaging("+(startPage-1)+")'>이전</a></li>");     
		
		for(int i=startPage; i<=endPage; i++){
			if(i==currentPage)
				pagingHTML.append("<li class='page-item'><a class='page-link' id='currentPaging' onclick='salesInfoSearchPaging("+i+")'>"+i+"</a></li>");
			else
				pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='salesInfoSearchPaging("+i+")'>"+i+"</a></li>");
		}
		
		if(endPage < totalP)
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='salesInfoSearchPaging("+(endPage+1)+")'>다음</a></li>");
	}
}















