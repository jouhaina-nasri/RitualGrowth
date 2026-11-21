package com.yoterra.hippo.search.requests.sort;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort.Direction;

import com.yoterra.hippo.req.SortDef;

public enum CompanySort implements SortDef {
	
//	com.yoterra.indexing.solr.fields.CompanyLuceneFieldNames.NAME_UNTOKENIZED = 
	name("name", Direction.DESC);
	
	private final String field;
	private final Direction defDir;
	
	private CompanySort(String field, Direction defDir) {
		Validate.notNull(field);
		Validate.notNull(defDir);
		this.field = field;
		this.defDir = defDir;
	}

	@Override
	public String field() {
		return field;
	}
	
	@Override
	public Direction defaultDirection() {
		return defDir;
	}
}
