package board.bean;

import org.springframework.stereotype.Component;

import lombok.Data;
/*
 * 게시판 페이징 관련 클래스
 */
@Component
@Data
public class BoardPaging {
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
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='boardPaging("+(startPage-1)+")'>이전</a></li>");     
		
		for(int i=startPage; i<=endPage; i++){
			if(i==currentPage)
				pagingHTML.append("<li class='page-item'><a class='page-link' id='currentPaging' href='#' onclick='boardPaging("+i+")'>"+i+"</a></li>");
			else
				pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='boardPaging("+i+")'>"+i+"</a></li>");
		
		}
		
		if(endPage < totalP)
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' href='#' onclick='boardPaging("+(endPage+1)+")'>다음</a></li>");
	}
	
	public void makeSearchPagingHTML(){

		int totalP = (totalA+pageSize-1)/pageSize;
		int startPage = (currentPage-1)/pageBlock*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		
			pagingHTML = new StringBuffer();
		
		if(endPage > totalP) endPage = totalP;
			
		if(startPage>pageBlock)			
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='boardSearchPaging("+(startPage-1)+")'>이전</a></li>");     
		
		for(int i=startPage; i<=endPage; i++){
			if(i==currentPage)
				pagingHTML.append("<li class='page-item'><a class='page-link' id='currentPaging' onclick='boardSearchPaging("+i+")'>"+i+"</a></li>");
			else
				pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='boardSearchPaging("+i+")'>"+i+"</a></li>");
		}
		
		if(endPage < totalP)
			pagingHTML.append("<li class='page-item'><a class='page-link' id='paging' onclick='boardSearchPaging("+(endPage+1)+")'>다음</a></li>");
	}
}















