package com.yoterra.hippo.res;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

public class CursorSliceImpl<T> implements CursorSlice<T> {

	private final Slice<T> inst;
	private final String nextCursor;
	
	public CursorSliceImpl(Slice<T> inst, String nextCursor) {
		Validate.notNull(inst, "Original slice can not be null!");
		this.inst = inst;
		//this.nextCursor = !inst.hasNext() ? null : nextCursor; to be fixed
		this.nextCursor = nextCursor;   // Mihajlo to Mahran: Why was this changed ?

	}
	
	public String getNextCursor(){
		return nextCursor;
	}

	public int getNumber() {
		return inst.getNumber();
	}

	public int getSize() {
		return inst.getSize();
	}

	public int getNumberOfElements() {
		return inst.getNumberOfElements();
	}

	public List<T> getContent() {
		return inst.getContent();
	}

	public boolean hasContent() {
		return inst.hasContent();
	}

	public Iterator<T> iterator() {
		return inst.iterator();
	}

	public Sort getSort() {
		return inst.getSort();
	}

	public boolean isFirst() {
		return inst.isFirst();
	}

	public void forEach(Consumer<? super T> action) {
		inst.forEach(action);
	}

	public boolean isLast() {
		return inst.isLast();
	}

	public boolean hasNext() {
		return inst.hasNext();
	}

	public Stream<T> stream() {
		return inst.stream();
	}

	public boolean hasPrevious() {
		return inst.hasPrevious();
	}

	public Pageable getPageable() {
		return inst.getPageable();
	}

	public Pageable nextPageable() {
		return inst.nextPageable();
	}

	public <R> Streamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return inst.flatMap(mapper);
	}

	public Spliterator<T> spliterator() {
		return inst.spliterator();
	}

	public Pageable previousPageable() {
		return inst.previousPageable();
	}

	public Streamable<T> filter(Predicate<? super T> predicate) {
		return inst.filter(predicate);
	}

	public <U> Slice<U> map(Function<? super T, ? extends U> converter) {
		return inst.map(converter);
	}

	public Pageable nextOrLastPageable() {
		return inst.nextOrLastPageable();
	}

	public boolean isEmpty() {
		return inst.isEmpty();
	}

	public Streamable<T> and(Supplier<? extends Stream<? extends T>> stream) {
		return inst.and(stream);
	}

	public Pageable previousOrFirstPageable() {
		return inst.previousOrFirstPageable();
	}

	public Streamable<T> and(@SuppressWarnings("unchecked") T... others) {
		return inst.and(others);
	}

	public Streamable<T> and(Iterable<? extends T> iterable) {
		return inst.and(iterable);
	}

	public Streamable<T> and(Streamable<? extends T> streamable) {
		return inst.and(streamable);
	}

	public List<T> toList() {
		return inst.toList();
	}

	public Set<T> toSet() {
		return inst.toSet();
	}

	public Stream<T> get() {
		return inst.get();
	}

	@Override
	public <U> CursorSlice<U> mapWithCursor(Function<? super T, ? extends U> converter) {
		Slice<U> mslice = map(converter);
		final String nextCursor = getNextCursor();
		return new CursorSliceImpl<U>(mslice, nextCursor);
	}
}
