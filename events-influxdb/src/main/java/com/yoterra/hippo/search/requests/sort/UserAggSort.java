package com.yoterra.hippo.search.requests.sort;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort.Direction;

import com.yoterra.hippo.req.SortDef;

public enum UserAggSort implements SortDef{
    FOLLOWERS_COUNT(Direction.DESC),
    COMMENTS_LIKES_COUNT(Direction.DESC),
    COLLECTIONS_LIKES_COUNT(Direction.DESC),
    COLLECTION_FOLLOWERS_COUNT(Direction.DESC),
    COLLECTION_COMMENTS_COUNT(Direction.DESC);
	
	private final Direction defDir;
	
	private UserAggSort(Direction defDir) {
		Validate.notNull(defDir);
		this.defDir = defDir;
	}

	@Override
	public Direction defaultDirection() {
		return defDir;
	}

	@Deprecated
	@Override
	public String field() { // not used in case of aggregated sort
		return name();
	}
}
