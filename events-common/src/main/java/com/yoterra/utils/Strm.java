package com.yoterra.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Thin wrapper over {@link Stream} with the terminal operations used in this project. */
public final class Strm<T> {

	private final Stream<T> stream;

	Strm(Stream<T> stream) {
		this.stream = stream;
	}

	public <R> Strm<R> map(Function<? super T, ? extends R> f) {
		return new Strm<>(stream.map(f));
	}

	public Strm<T> filter(Predicate<? super T> p) {
		return new Strm<>(stream.filter(p));
	}

	public Strm<T> noNulls() {
		return filter(Objects::nonNull);
	}

	public boolean anyMatch(Predicate<? super T> p) {
		return stream.anyMatch(p);
	}

	public boolean allMatch(Predicate<? super T> p) {
		return stream.allMatch(p);
	}

	public List<T> toList() {
		return stream.collect(Collectors.toCollection(ArrayList::new));
	}

	public Set<T> toSet() {
		return stream.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/** Maps each element by the given key; on duplicate keys the last element wins. */
	public <K> Map<K, T> toMapByKey(Function<? super T, ? extends K> key) {
		Map<K, T> map = new LinkedHashMap<>();
		stream.forEach(e -> map.put(key.apply(e), e));
		return map;
	}

	public Stream<T> stream() {
		return stream;
	}
}
