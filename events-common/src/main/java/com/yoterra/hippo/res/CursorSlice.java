package com.yoterra.hippo.res;

import java.util.function.Function;

import org.springframework.data.domain.Slice;

public interface CursorSlice<T> extends Slice<T> {

	public String getNextCursor();	
	
	public <U> CursorSlice<U> mapWithCursor(Function<? super T, ? extends U> converter);
}
