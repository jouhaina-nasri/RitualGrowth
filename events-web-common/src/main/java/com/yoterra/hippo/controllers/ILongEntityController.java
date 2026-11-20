package com.yoterra.hippo.controllers;

import com.yoterra.utils.NumUtils;

public interface ILongEntityController extends IEntityController<Long>{
	
	@Override
	default Long idFromString(String id) {
		return NumUtils.toLong(id);
	}
}
