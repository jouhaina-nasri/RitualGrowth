package com.yoterra.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Null-safe helpers. Every getter chain stops and returns {@code null}
 * (or the given default) as soon as an intermediate value is {@code null}.
 */
public final class Opt {

	private Opt() {}

	public static <T, R> R get(T obj, Function<? super T, ? extends R> f) {
		return obj == null ? null : f.apply(obj);
	}

	public static <T, A, R> R get(T obj, Function<? super T, ? extends A> f1, Function<? super A, ? extends R> f2) {
		return get(get(obj, f1), f2);
	}

	public static <T, A, B, R> R get(T obj, Function<? super T, ? extends A> f1, Function<? super A, ? extends B> f2,
			Function<? super B, ? extends R> f3) {
		return get(get(obj, f1, f2), f3);
	}

	public static <T, R> R getOrDef(T obj, R def, Function<? super T, ? extends R> f) {
		return defVal(get(obj, f), def);
	}

	public static <T, A, R> R getOrDef(T obj, R def, Function<? super T, ? extends A> f1, Function<? super A, ? extends R> f2) {
		return defVal(get(obj, f1, f2), def);
	}

	public static <T> T defVal(T value, T def) {
		return value != null ? value : def;
	}

	public static <T> T lazyDefVal(T value, Supplier<? extends T> def) {
		return value != null ? value : def.get();
	}

	public static <T, X extends Throwable> T req(T obj, Supplier<? extends X> exception) throws X {
		if (obj == null)
			throw exception.get();
		return obj;
	}

	public static <T> Strm<T> strm(Collection<T> c) {
		return new Strm<>(c == null ? Stream.empty() : c.stream());
	}

	@SafeVarargs
	public static <T> Strm<T> strm(T... arr) {
		return new Strm<>(arr == null ? Stream.empty() : Arrays.stream(arr));
	}

	public static <T, R> Strm<R> strm(T obj, Function<? super T, ? extends Collection<R>> f) {
		return strm(get(obj, f));
	}
}
