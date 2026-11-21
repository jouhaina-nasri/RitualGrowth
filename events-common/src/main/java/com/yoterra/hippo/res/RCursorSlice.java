package com.yoterra.hippo.res;

import java.util.List;
import java.util.function.Function;

public class RCursorSlice<T> {
	
	private final List<T> content;
	private final String nextCursor;

	public static <E>RCursorSlice<E> of(CursorSlice<E> cslice) {
		return cslice == null ? null : new RCursorSlice<E>(cslice.getContent(), cslice);
	}
	
	public static <E,P>RCursorSlice<E> of(CursorSlice<P> cslice, Function<P,E> converter) {
		return cslice == null ? null : of(cslice.mapWithCursor(converter));
	}
	
	private RCursorSlice(List<T> content, CursorSlice<T> cslice) {
		this(content, cslice.getNextCursor());
	}
	
	private RCursorSlice(List<T> content, String nextCursor) {
		super();
		this.content = content;
		this.nextCursor = nextCursor;
	}

	public List<T> getContent() {
		return content;
	}

	public String getNextCursor() {
		return nextCursor;
	}
	
//
//	public <O>RPage<O> convert(Function<T, O> converter) {
//		List<O> cCont = Opt.strm(content).map(converter).toList();
//		return new RPage<O>(cCont, totalElements, totalPages, pageNumber, pageSize);
//	}
}
