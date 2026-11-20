package com.yoterra.hippo.req;

import java.util.List;
import java.util.Map;

public interface QueryParamsMapProvider {
	
	public static @interface QueryParam {}

	Map<String, List<String>> getQueryParamsMap();
}
