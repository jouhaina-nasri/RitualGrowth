package com.yoterra.hippo.controllers;

public interface IEntityToMobConverter<E,M> {
	
	M toMob(E entity);
}
