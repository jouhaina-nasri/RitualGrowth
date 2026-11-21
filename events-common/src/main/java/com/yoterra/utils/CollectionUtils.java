package com.yoterra.utils;

import java.util.List;

public final class CollectionUtils {

	private CollectionUtils() {}

	/** First element of the list, or {@code null} if the list is null or empty. */
	public static <T> T first(List<T> list) {
		return list == null || list.isEmpty() ? null : list.get(0);
	}
}
