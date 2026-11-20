package com.yoterra.hippo.req;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PaginationSupport {

	public static final class PaginationUrlBuilder{
		private String pageParam;
		
		public PaginationUrlBuilder(String pageParam) {
			this.pageParam = pageParam;
		}
		
		public String getUrlForPage(int pageNum){
			ServletUriComponentsBuilder cr = org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest();
			cr.replaceQueryParam(pageParam, pageNum);
			return cr.toUriString();
		}
	}
	
	public static final class CursorPaginationUrlBuilder{
		private String cursorParam;
		
		public CursorPaginationUrlBuilder(String cursorParam) {
			this.cursorParam = cursorParam;
		}
		
		public String getUrlForCursor(String cursor){
			ServletUriComponentsBuilder cr = org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest();
			cr.replaceQueryParam(cursorParam, cursor);
			return cr.toUriString();
		}
	}
	
	public static PaginationUrlBuilder newPaginationUrlBuilder(){
		return newPaginationUrlBuilder("page");
	}
	
	public static PaginationUrlBuilder newPaginationUrlBuilder(String pageParam){
		return new PaginationUrlBuilder(pageParam);
	}
	
	public static CursorPaginationUrlBuilder newCursorPaginationUrlBuilder(){
		return newCursorPaginationUrlBuilder("cursor");
	}
	
	public static CursorPaginationUrlBuilder newCursorPaginationUrlBuilder(String cursorParam){
		return new CursorPaginationUrlBuilder(cursorParam);
	}
	
	public static String currentPath() {
		HttpServletRequest req = currentRequest();
		return req.getServletPath();
	}

	public static HttpServletRequest currentRequest() {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.state(attrs instanceof ServletRequestAttributes, "No current ServletRequestAttributes");
		HttpServletRequest req = ((ServletRequestAttributes) attrs).getRequest();
		return req;
	}
	
	public static void main(String[] args) {
		 UriComponentsBuilder b = ServletUriComponentsBuilder.fromHttpUrl("https://google.com?n1=3&p1=3");
		 b.replaceQueryParam("ff", 34);
		 b.replaceQueryParam("p1", 34);
		 b.replaceQueryParam("n1", (Object)null);
		 System.out.println(b.toUriString());
	}
}
