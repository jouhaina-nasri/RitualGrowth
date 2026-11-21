package com.yoterra.hippo.res;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.yoterra.utils.Opt;
import org.springframework.data.domain.Pageable;

public class RPage<T> {
	
	private List<T> content;
	
	private long totalElements;
	
	private int totalPages;
	private int pageNumber;
	private int pageSize;


	public static <E,P>RPage<E> off(List<E> content, Pageable pageable) {
		Page< E > page = (Page<E>) GetPage.getPage(content,pageable);
		return new RPage<E>(page.getContent(), page);

	}

	public static <E,P>RPage<E> of(List<E> content, Pageable pageable) {
		return content == null ? null : off(content, pageable);
	}
	
	public static <E>RPage<E> of(Page<E> page) {
		return page == null ? null : new RPage<E>(page.getContent(), page);
	}
	
	public static <E,P>RPage<E> of(Page<P> page, Function<P,E> converter) {
		return page == null ? null : of(page.map(converter));
	}
	
	private RPage(List<T> content, Page<T> page) {
		this(content, page.getTotalElements(), page.getTotalPages(), page.getNumber() + 1, page.getSize());
	}
	
	public <O>RPage<O> convert(Function<T, O> converter) {
		List<O> cCont = Opt.strm(content).map(converter).toList();
		return new RPage<O>(cCont, totalElements, totalPages, pageNumber, pageSize);
	}
	
	@Deprecated
	public RPage() {}		// used just for deserialization
	
	private RPage(List<T> content, long totalElements, int totalPages, int pageNumber, int pageSize) {
		this.content = content;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public List<T> getContent() {
		return content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPageNumber() {
		return pageNumber;
	}
	
	public int getPageSize() {
		return pageSize;
	}
}
